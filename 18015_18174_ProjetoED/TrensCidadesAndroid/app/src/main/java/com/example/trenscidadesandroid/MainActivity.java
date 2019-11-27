package com.example.trenscidadesandroid;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
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
    private ImageView ivCanvas;
    private Button btnBuscar, btnAdicionarCidade, btnAdicionarCaminho;
    private TableLayout tbCaminhos;
    Paint paint;
    private static final float TOTAL_X = 358.5f;
    private static final float TOTAL_Y = 289f;
    private static final float ESPESSURA = 5.0f;
    private static final int COR_LINHA = Color.RED;

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
        ivCanvas = findViewById(R.id.ivCanvas);
        tbCaminhos = findViewById(R.id.tbCaminhos);

        this.paint = new Paint();
        this.paint.setColor(COR_LINHA);
        this.paint.setStrokeWidth(ESPESSURA);

        canvasView = new CanvasView(this);

        Bitmap imagem = BitmapFactory.decodeResource(getResources(), R.drawable.mapa);
        Canvas canvas = new Canvas();
        canvas.drawBitmap(imagem, 0, 0, null);

        ivCanvas.setImageDrawable(new BitmapDrawable(getResources(), imagem));


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
