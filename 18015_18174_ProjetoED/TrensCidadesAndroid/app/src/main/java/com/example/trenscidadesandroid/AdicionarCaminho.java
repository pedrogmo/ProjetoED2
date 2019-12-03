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
    private Button btnAdicionar;
    private ArrayList<String> listaNomesCidades, listaCaminhos;
    private BucketHash<Cidade> bhCidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_caminho);

        //Definição dos campos
        btnAdicionar = findViewById(R.id.btnAdicionarCaminhoAC);
        etDistancia = findViewById(R.id.etDistancia);
        etTempo = findViewById(R.id.etTempo);
        spDeOnde = findViewById(R.id.spDeOndeAdicionarCaminho);
        spParaOnde = findViewById(R.id.spParaOndeAdicionarCaminho);

        //Recebe ArrayList com nomes das cidades serializado
        listaNomesCidades = (ArrayList<String>) getIntent().getExtras().getSerializable("listaNomesCidades");

        //Recebe BucketHash de cidades
        bhCidade = (BucketHash<Cidade>) getIntent().getExtras().getSerializable("hash");

        //Instancia-se um apapter de Spinner
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getApplicationContext(),
                android.R.layout.simple_spinner_item,
                listaNomesCidades);

        //Adapter para os dois spinners presentes
        spParaOnde.setAdapter(adapter);
        spDeOnde.setAdapter(adapter);

        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //Strings dos itens selecionados
                String  stringCidadeOrigem = spDeOnde.getSelectedItem().toString(),
                        stringCidadeDestino = spParaOnde.getSelectedItem().toString();

                //Se a ligação não for entre duas cidades iguais
                if (!stringCidadeOrigem.equals(stringCidadeDestino))
                    try
                    {
                        //Separa-se a string selecionada
                        String[] vetor1 = stringCidadeOrigem.split("-");
                        String[] vetor2 = stringCidadeDestino.split("-");

                        //Pegam-se os nomes das duas cidades
                        String  cidadeOrigem = vetor1[1].substring(1),
                                cidadeDestino = vetor2[1].substring(1);

                        //Buscam-se as duas cidades no BucketHash
                        Cidade origem = bhCidade.buscar(new Cidade(cidadeOrigem));
                        Cidade destino = bhCidade.buscar(new Cidade(cidadeDestino));

                        //Pegam-se os valores de tempo e distância digitados
                        int tempo = Integer.parseInt(etTempo.getText().toString().trim());
                        int distancia = Integer.parseInt(etDistancia.getText().toString().trim());

                        //Instancia-se uma aresta com origem, destino, tempo e distância
                        Aresta aresta = new Aresta(origem, destino, new PesoCidades(tempo, distancia));

                        //Arquivo sa saída aberto no modo append, para adicionar ao final
                        FileOutputStream fileout = openFileOutput("grafo.txt", MODE_APPEND);
                        OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
                        outputWriter.write("\n" + aresta.paraArquivo());
                        outputWriter.close();

                        Toast.makeText(getApplicationContext(), "Inserido com sucesso", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(getApplicationContext(), MainActivity.class));

                    }
                    catch (Exception e)
                    {
                        //Houve campos digitados errado
                        Toast.makeText(getApplicationContext(), "Campos errados: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                else
                {
                    //Cidades selecionadas são iguais
                    Toast.makeText(getApplicationContext(), "Origem e destino estão iguais", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}
