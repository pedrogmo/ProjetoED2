package com.example.trenscidadesandroid;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trenscidadesandroid.classes.buckethash.BucketHash;
import com.example.trenscidadesandroid.classes.cidade.Cidade;
import com.example.trenscidadesandroid.classes.linha.Linha;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;

//Gustavo Henrique de Meira - 18015
//Pedro Gomes Moreira - 18174

public class MainActivity extends AppCompatActivity
{

    private Spinner spDeOnde, spParaOnde;
    private CanvasView canvasView;
    private LinearLayout layoutCanvas;
    private Button btnBuscar, btnAdicionarCidade, btnAdicionarCaminho;
    private TableLayout tbCaminhos;

    private BucketHash<Cidade> bhCidade;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spDeOnde = findViewById(R.id.spDeOnde);
        spParaOnde = findViewById(R.id.spParaOnde);
        btnBuscar = findViewById(R.id.btnBuscar);
        btnAdicionarCidade = findViewById(R.id.btnAdicionarCidade);
        btnAdicionarCaminho = findViewById(R.id.btnAdicionarCaminho);

        canvasView = new CanvasView(this);
        layoutCanvas.addView(canvasView);

        bhCidade = new BucketHash<Cidade>();
        ArrayAdapter<String> cidadesSpinner = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1);

        try
        {
            AssetManager assetManager = getResources().getAssets();

            InputStream inputStream = assetManager.open("cidades.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String recebeString;
            while((recebeString = bufferedReader.readLine())!=null)
            {
                Cidade cd = null;
                cd = new Cidade(new Linha(recebeString));
                cidadesSpinner.add(cd.toString());
                bhCidade.inserir(cd);
            }
            inputStream.close();
        }

        catch (Exception exc)
        {
            Toast.makeText(
                getApplicationContext(),
                "Erro na leitura do arquivo",
                Toast.LENGTH_SHORT
            ).show();

            Log.d("ERRO", exc.toString());
        }

        spDeOnde.setAdapter(cidadesSpinner);
        spParaOnde.setAdapter(cidadesSpinner);

        btnAdicionarCidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(getApplicationContext(), AdicionarCidade.class);
                i.putExtra("hash", (Serializable) bhCidade);
                startActivity(i);
            }
        });

    }
}
