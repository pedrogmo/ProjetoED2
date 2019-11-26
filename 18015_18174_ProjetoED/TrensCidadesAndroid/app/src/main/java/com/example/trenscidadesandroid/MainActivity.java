package com.example.trenscidadesandroid;

import android.content.Intent;
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
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;

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
        layoutCanvas = findViewById(R.id.llCanvas);
        tbCaminhos = findViewById(R.id.tbCaminhos);

        canvasView = new CanvasView(this);
        layoutCanvas.addView(canvasView);

        bhCidade = new BucketHash<Cidade>();
        ArrayList<String> listaNomesCidades = new ArrayList<String>();

        try
        {
            FileInputStream fileIn = openFileInput("cidades.txt");
            InputStreamReader inputRead = new InputStreamReader(fileIn);
            BufferedReader leitor = new BufferedReader(inputRead);

            String recebeString;
            while((recebeString = leitor.readLine()) != null)
            {
                Cidade cd = new Cidade(new Linha(recebeString));
                listaNomesCidades.add(cd.toString());
                bhCidade.inserir(cd);
            }
            leitor.close();
        }

        catch (Exception exc)
        {
            Toast.makeText(
                getApplicationContext(),
                "Erro na leitura do arquivo",
                Toast.LENGTH_SHORT
            ).show();

            Log.d("MSG_ERR", exc.toString());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
            getApplicationContext(),
            android.R.layout.simple_spinner_item,
            listaNomesCidades);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spDeOnde.setAdapter(adapter);
        spParaOnde.setAdapter(adapter);

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
