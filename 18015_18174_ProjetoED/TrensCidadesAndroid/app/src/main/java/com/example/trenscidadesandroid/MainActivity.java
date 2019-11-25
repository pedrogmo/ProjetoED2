package com.example.trenscidadesandroid;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Xml;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    Spinner spDeOnde, spParaOnde;
    View canvasView;
    LinearLayout layoutCanvas;
    Button btnBuscar, btnAdicionarCidade, btnAdicionarCaminho;
    TableLayout tbCaminhos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spDeOnde = findViewById(R.id.spDeOnde);
        spParaOnde = findViewById(R.id.spParaOnde);
        btnBuscar = findViewById(R.id.btnBuscar);
        btnAdicionarCidade = findViewById(R.id.btnAdicionarCidade);
        btnAdicionarCaminho = findViewById(R.id.btnAdicionarCaminho);

        canvasView = new View(this);
        layoutCanvas.addView(canvasView);

        bhCidade = new BucketHash<Cidade>();
        ArrayAdapter<String> cidadesSpinner = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1);

        AssetManager assetManager = getResources().getAssets();
        InputStream inputStream;

        try {
            inputStream = assetManager.open("cidades.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String recebe_string;
            while((recebe_string = bufferedReader.readLine())!=null){

                Cidade cd = new Cidade(new Linha(recebe_string));
                cidadesSpinner.add(cd.toString());
                bhCidade.inserir(cd);
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        spDeOnde.setAdapter(cidadesSpinner);
        spParaOnde.setAdapter(cidadesSpinner);

        btnAdicionarCidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AdicionarCidade.class);
                startActivity(i);
            }
        });

    }
}
