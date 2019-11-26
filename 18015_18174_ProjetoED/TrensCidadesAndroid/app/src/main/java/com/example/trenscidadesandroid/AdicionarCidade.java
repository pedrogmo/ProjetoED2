package com.example.trenscidadesandroid;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trenscidadesandroid.classes.buckethash.BucketHash;
import com.example.trenscidadesandroid.classes.cidade.Cidade;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

//Gustavo Henrique de Meira - 18015
//Pedro Gomes Moreira - 18174

public class AdicionarCidade extends AppCompatActivity
{
    private EditText etNome, etCoordenadaX, etCoordenadaY;
    private Button btnAdicionar;
    private BucketHash<Cidade> bhCidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_cidade);

        etNome = findViewById(R.id.etNome);
        etCoordenadaX = findViewById(R.id.etCoordenadaX);
        etCoordenadaY = findViewById(R.id.etCoordenadaY);

        bhCidade = (BucketHash<Cidade>) getIntent().getExtras().getSerializable("hash");

        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etNome.getText().toString().trim().equals("")  && !etCoordenadaX.getText().toString().trim().equals("")
                        && etCoordenadaY.getText().toString().trim().equals(""))
                {
                    Cidade cd = null;
                    try
                    {
                        cd = new Cidade(etNome.getText().toString().trim());
                    }
                    catch(Exception exc)
                    {
                        Toast.makeText(
                            getApplicationContext(),
                            exc.getMessage(),
                            Toast.LENGTH_SHORT
                        ).show();
                        return;
                    }
                    if (bhCidade.buscar(cd) == null)
                    {
                        try
                        {
                            FileOutputStream fileout = openFileOutput("cidades.txt", MODE_APPEND);
                            OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);

                            outputWriter.write("\n" + cd.paraArquivo());

                            outputWriter.close();
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
                    }
                    else
                    {
                        Toast.makeText(
                            getApplicationContext(),
                            "Cidade já existente",
                            Toast.LENGTH_SHORT
                        ).show();
                    }
                }
                else
                {
                    Toast.makeText(
                        getApplicationContext(),
                        "Há campos vazios",
                        Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });
    }
    }

