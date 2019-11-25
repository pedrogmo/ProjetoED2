package com.example.trenscidadesandroid;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class AdicionarCidade extends AppCompatActivity {
    EditText etNome, etCoordenadaX, etCoordenadaY;
    Button btnAdicionar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_cidade);

        etNome = findViewById(R.id.etNome);
        etCoordenadaX = findViewById(R.id.etCoordenadaX);
        etCoordenadaY = findViewById(R.id.etCoordenadaY);

        AssetManager assetManager = getApplicationContext().getAssets();
        InputStream inputStream;

        bhCidade = new BucketHash<Cidade>();

        try {
            inputStream = assetManager.open("cidades.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String recebe_string;
            while((recebe_string = bufferedReader.readLine())!=null){

                Cidade cd = new Cidade(new Linha(recebe_string));
                bhCidade.inserir(cd);
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }



        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etNome.getText().toString().trim().equals("")  && !etCoordenadaX.getText().toString().trim().equals("")
                        && etCoordenadaY.getText().toString().trim().equals(""))
                {
                    Cidade cd = new Cidade(etNome.getText().toString().trim());
                    if (bhCidade.Buscar(cd) == null)
                    {
                        StreamWriter sw = new StreamWriter(assets.Open("cidades.txt"));
                        cd.X = Double.Parse(etCoordenadaX.Text.Trim());
                        cd.Y = Double.Parse(etCoordenadaY.Text.Trim());
                        cd.Codigo = bhCidade.Quantidade;
                        sw.Write(cd.ParaArquivo());
                        sw.Close();
                    }
                    else
                        Toast.MakeText(Application.Context, "Essa cidade já existe", ToastLength.Short);
                }
                else
                    Toast.MakeText(Application.Context, "Há campos vazios", ToastLength.Short);
            }
        });
    }
    }

