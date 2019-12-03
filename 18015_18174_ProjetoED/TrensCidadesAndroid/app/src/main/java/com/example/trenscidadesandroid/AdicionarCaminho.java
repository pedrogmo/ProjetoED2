//Gustavo Henrique de Meira - 18015
//Pedro Gomes Moreira - 18174

package com.example.trenscidadesandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.trenscidadesandroid.classes.aresta.Aresta;
import com.example.trenscidadesandroid.classes.buckethash.BucketHash;
import com.example.trenscidadesandroid.classes.cidade.Cidade;
import com.example.trenscidadesandroid.classes.pesocidades.PesoCidades;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class AdicionarCaminho extends AppCompatActivity {
    private Spinner spDeOnde, spParaOnde;
    private EditText etDistancia, etTempo;
    Button btnAdicionar;
    ArrayList<String> listaNomesCidades, listaCaminhos;
    BucketHash<Cidade> bhCidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_caminho);

        btnAdicionar = findViewById(R.id.btnAdicionarCaminhoAC);
        etDistancia = findViewById(R.id.etDistancia);
        etTempo = findViewById(R.id.etTempo);

        spDeOnde = findViewById(R.id.spDeOndeAdicionarCaminho);
        spParaOnde = findViewById(R.id.spParaOndeAdicionarCaminho);

        listaNomesCidades = (ArrayList<String>) getIntent().getExtras().getSerializable("listaNomesCidades");
        bhCidade = (BucketHash<Cidade>) getIntent().getExtras().getSerializable("hash");

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getApplicationContext(),
                android.R.layout.simple_spinner_item,
                listaNomesCidades);


        spParaOnde.setAdapter(adapter);
        spDeOnde.setAdapter(adapter);

        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stringCidadeOrigem = spDeOnde.getSelectedItem().toString(),
                        stringCidadeDestino = spParaOnde.getSelectedItem().toString();


                if (!stringCidadeOrigem.equals(stringCidadeDestino))
                    try {
                        String[] vetor1 = stringCidadeOrigem.split("-");
                        String[] vetor2 = stringCidadeDestino.split("-");

                        String cidadeOrigem = vetor1[1].substring(1), cidadeDestino = vetor2[1].substring(1);

                        Toast.makeText(getApplicationContext(), cidadeOrigem, Toast.LENGTH_SHORT).show();
                        Cidade origem = bhCidade.buscar(new Cidade(cidadeOrigem));
                        Cidade destino = bhCidade.buscar(new Cidade(cidadeDestino));
                        int tempo = Integer.parseInt(etTempo.getText().toString().trim());
                        int distancia = Integer.parseInt(etDistancia.getText().toString().trim());

                        Aresta aresta = new Aresta(origem, destino, new PesoCidades(tempo, distancia));

                        FileOutputStream fileout = openFileOutput("grafo.txt", MODE_APPEND);
                        OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
                        outputWriter.write("\n" + aresta.paraArquivo());
                        outputWriter.close();

                        Toast.makeText(getApplicationContext(), "Inserido com sucesso", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(getApplicationContext(), MainActivity.class));

                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Campos errados: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                else {
                    Toast.makeText(getApplicationContext(), "Origem e destino estão iguais", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}
