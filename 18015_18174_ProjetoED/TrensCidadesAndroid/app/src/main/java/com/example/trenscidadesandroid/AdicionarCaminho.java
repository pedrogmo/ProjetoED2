package com.example.trenscidadesandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.trenscidadesandroid.classes.buckethash.BucketHash;
import com.example.trenscidadesandroid.classes.cidade.Cidade;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class AdicionarCaminho extends AppCompatActivity {
    private Spinner spDeOnde, spParaOnde;
    private EditText etDistancia, etTempo;
    Button btnAdicionar;
    ArrayList<String> listaNomesCidades;


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

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getApplicationContext(),
                android.R.layout.simple_spinner_item,
                listaNomesCidades);

        spParaOnde.setAdapter(adapter);
        spDeOnde.setAdapter(adapter);

        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    FileOutputStream fileout = openFileOutput("cidades.txt", MODE_PRIVATE);
                    OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
                    outputWriter.write("" // moreira cole o texto aqui
                    );
                    outputWriter.close();


                    int distancia = Integer.parseInt(etDistancia.getText().toString().trim());
                    int tempo = Integer.parseInt(etTempo.getText().toString().trim());


                }
                catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Campos errados", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
