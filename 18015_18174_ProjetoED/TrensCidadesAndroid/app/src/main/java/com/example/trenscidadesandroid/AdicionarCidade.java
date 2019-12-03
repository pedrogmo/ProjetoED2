//Gustavo Henrique de Meira - 18015
//Pedro Gomes Moreira - 18174

package com.example.trenscidadesandroid;

import android.content.Intent;
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

//Activity para adicionar nova cidade
public class AdicionarCidade extends AppCompatActivity
{
    private EditText etNome, etCoordenadaX, etCoordenadaY;
    private Button btnAdicionar;
    private BucketHash<Cidade> bhCidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_cidade);

        //Definição dos campos a partir do ID
        etNome = findViewById(R.id.etNome);
        etCoordenadaX = findViewById(R.id.etCoordenadaX);
        etCoordenadaY = findViewById(R.id.etCoordenadaY);
        btnAdicionar = findViewById(R.id.btnConcluirCidade);

        //Recebe o BucketHash serializado
        bhCidade = (BucketHash<Cidade>) getIntent().getExtras().getSerializable("hash");

        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Se os campos não estão vazios
                if (!etNome.getText().toString().trim().equals("") &&
                    !etCoordenadaX.getText().toString().trim().equals("") &&
                    !etCoordenadaY.getText().toString().trim().equals(""))
                {
                    Cidade cd = null;

                    final String nome;
                    final float x;
                    final float y;

                    try
                    {
                        //Valores atribuídos às variáveis
                        nome = etNome.getText().toString().trim();
                        x = Float.parseFloat(etCoordenadaX.getText().toString().trim());
                        y = Float.parseFloat(etCoordenadaY.getText().toString().trim());
                    }
                    catch(Exception exc)
                    {
                        //Se houve erro, mensagem ao usuário
                        Toast.makeText(
                            getApplicationContext(),
                            "Dados inválidos",
                            Toast.LENGTH_SHORT
                        ).show();

                        //Sai do evento click
                        return;
                    }

                    try
                    {
                        //Instanciação de cidade com os dados
                        cd = new Cidade(
                            bhCidade.getQuantidade(),
                            nome,
                            x,
                            y
                        );
                    }
                    catch(Exception exc)
                    {
                        //Se houve erro, mostra a exeção vinda da Cidade
                        Toast.makeText(
                            getApplicationContext(),
                            exc.getMessage(),
                            Toast.LENGTH_SHORT
                        ).show();
                        return;
                    }

                    //Se cidade não existe no BucketHash
                    if (bhCidade.buscar(cd) == null)
                    {
                        try
                        {
                            //Abre arquivo de saída no modo append, para adicionar cidade ao final
                            FileOutputStream fileout = openFileOutput("cidades.txt", MODE_APPEND);
                            OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
                            outputWriter.write("\n" + cd.paraArquivo());
                            outputWriter.close();

                            Toast.makeText(getApplicationContext(), "Inserido com sucesso", Toast.LENGTH_SHORT).show();

                            //Volta para a MainActivity
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }

                        catch (Exception exc)
                        {
                            //Se lançou exceção, erro de leitura
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
                        //Caso cidade já exista no BucketHash
                        Toast.makeText(
                            getApplicationContext(),
                            "Cidade já existente",
                            Toast.LENGTH_SHORT
                        ).show();
                    }
                }
                else
                {
                    //Se há campos vazios
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