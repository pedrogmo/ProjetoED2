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
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trenscidadesandroid.classes.buckethash.BucketHash;
import com.example.trenscidadesandroid.classes.cidade.Cidade;
import com.example.trenscidadesandroid.classes.linha.Linha;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.ArrayList;

//Gustavo Henrique de Meira - 18015
//Pedro Gomes Moreira - 18174

public class MainActivity extends AppCompatActivity
{

    private Spinner spDeOnde, spParaOnde;
    private Desenhadora desenhadora;
    private ImageView ivCanvas;
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
        ivCanvas = findViewById(R.id.ivCanvas);
        tbCaminhos = findViewById(R.id.tbCaminhos);

        desenhadora = new Desenhadora(this.ivCanvas, getResources());

        bhCidade = new BucketHash<Cidade>();
        ArrayList<String> listaNomesCidades = new ArrayList<String>();

        try
        {
            /*
            Escrevendo arquivo:

            FileOutputStream fileout = openFileOutput("cidades.txt", MODE_PRIVATE);
            OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
            outputWriter.write("" +
                " 0Covilhã         0.252 0.479\n" +
                " 1A Coruna        0.195 0.151\n" +
                " 2Albacete        0.609 0.609\n" +
                " 3Alicante        0.697 0.675\n" +
                " 4Barcelona       0.864 0.363\n" +
                " 5Bilbao          0.542 0.164\n" +
                " 6Braga           0.194 0.344\n" +
                " 7Burgos          0.494 0.261\n" +
                " 8Caceres         0.324 0.561\n" +
                " 9Cadiz           0.327 0.854\n" +
                "10Cartagena       0.666 0.751\n" +
                "11Castelo Branco  0.252 0.524\n" +
                "12Coimbra         0.193 0.485\n" +
                "13Cordoba         0.425 0.721\n" +
                "14El Ejido        0.550 0.832\n" +
                "15Gijon           0.369 0.131\n" +
                "16Girona          0.907 0.299\n" +
                "17Granada         0.498 0.791\n" +
                "18Guadalajara     0.527 0.443\n" +
                "19Guarda          0.266 0.452\n" +
                "20Huelva          0.287 0.784\n" +
                "21Jaen            0.488 0.734\n" +
                "22Leon            0.375 0.235\n" +
                "23Lisboa          0.148 0.638\n" +
                "24Lleida          0.768 0.339\n" +
                "25Logrono         0.572 0.249\n" +
                "26Lorca           0.619 0.742\n" +
                "27Loule           0.218 0.796\n" +
                "28Lugo            0.248 0.192\n" +
                "29Madrid          0.492 0.462\n" +
                "30Malaga          0.446 0.837\n" +
                "31Mataro          0.882 0.350\n" +
                "32Montpellier     0.976 0.126\n" +
                "33Murcia          0.655 0.710\n" +
                "34Ourense         0.229 0.261\n" +
                "35Oviedo          0.356 0.152\n" +
                "36Pau             0.703 0.159\n" +
                "37Perpignan       0.911 0.223\n" +
                "38Pombal          0.179 0.517\n" +
                "39Ponferrada      0.310 0.240\n" +
                "40Porto           0.180 0.388\n" +
                "41Salamanca       0.368 0.407\n" +
                "42San Sebastian   0.601 0.156\n" +
                "43Santander       0.487 0.141\n" +
                "44Santiago de C.  0.185 0.206\n" +
                "45Sevilha         0.347 0.770\n" +
                "46Tarragona       0.806 0.391\n" +
                "47Toledo          0.471 0.521\n" +
                "48Toulouse        0.817 0.126\n" +
                "49Valencia        0.702 0.561\n" +
                "50Valladolid      0.425 0.334\n" +
                "51Vigo            0.174 0.272\n" +
                "52Viseu           0.225 0.439\n" +
                "53Zaragoza        0.672 0.334"
            );
            outputWriter.close();
            */

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
