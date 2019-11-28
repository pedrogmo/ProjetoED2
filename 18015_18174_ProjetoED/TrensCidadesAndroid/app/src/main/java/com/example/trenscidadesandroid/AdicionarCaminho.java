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
    Button btnAdicionar;
    ArrayList<String> listaNomesCidades;
    BucketHash<Cidade> bhCidade;


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
        bhCidade = (BucketHash<Cidade>) getIntent().getExtras().getSerializable("hash");

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
                    outputWriter.write("Madrid         Salamanca       220  150\n" +
                            "Salamanca      Guarda          160   90\n" +
                            "Guarda         Viseu            76   80\n" +
                            "Madrid         Albacete        257  280\n" +
                            "Madrid         Toledo           72  205\n" +
                            "Madrid         Valladolid      189  140\n" +
                            "Toledo         Cordoba         344  210\n" +
                            "Cordoba        Sevilha         140  220\n" +
                            "Sevilha        Huelva           94  201\n" +
                            "Sevilha        Cadiz           121   80\n" +
                            "Sevilha        Caceres         267  120\n" +
                            "Caceres        Toledo          260  110\n" +
                            "Caceres        Castelo Branco  141   90\n" +
                            "Pombal         Castelo Branco  137  150\n" +
                            "Zaragoza       Lleida          150  210\n" +
                            "Lleida         Tarragona       100  205\n" +
                            "Tarragona      Barcelona        99  208\n" +
                            "Tarragona      Valencia        258  250\n" +
                            "Valencia       Albacete        188  270\n" +
                            "Albacete       Murcia          144  230\n" +
                            "Murcia         Cartagena        50  201\n" +
                            "Valencia       Alicante        179  105\n" +
                            "Alicante       Murcia           82   80\n" +
                            "Murcia         Lorca            70   60\n" +
                            "Zaragoza       Valencia        308   93\n" +
                            "Zaragoza       Pau             235   60\n" +
                            "Pau            Toulouse        195   90\n" +
                            "Toulouse       Montpellier     246   76\n" +
                            "Zaragoza       Logrono         170  100\n" +
                            "Logrono        San Sebastian   167   75\n" +
                            "Logrono        Bilbao          136   77\n" +
                            "Logrono        Burgos          104   50\n" +
                            "Bilbao         Burgos          158   70\n" +
                            "Burgos         Valladolid      136   50\n" +
                            "Valladolid     Santander       244   66\n" +
                            "Valladolid     Leon            137   95\n" +
                            "Leon           Oviedo          125   83\n" +
                            "Leon           Ponferrada      113   75\n" +
                            "Oviedo         Gijon            30   73\n" +
                            "Loule          Lisboa          265   75\n" +
                            "Lisboa         Pombal          140   70\n" +
                            "Pombal         Coimbra          36   96\n" +
                            "Coimbra        Viseu            92   78\n" +
                            "Coimbra        Porto           107  107\n" +
                            "Porto          Braga            46   42\n" +
                            "Braga          Vigo             81   18\n" +
                            "Vigo           Ourense          71   42\n" +
                            "Ourense        Ponferrada      100   48\n" +
                            "Vigo           Santiago de C.   72   48\n" +
                            "Santiago de C. A Coruna         64   67\n" +
                            "Santiago de C. Ourense          82  136\n" +
                            "A Coruna       Lugo             80   47\n" +
                            "Lugo           Ponferrada       93   71\n" +
                            "Cordoba        Jaen            120   73\n" +
                            "Cordoba        Malaga          168  203\n" +
                            "Malaga         Granada          88   50\n" +
                            "Jaen           Granada          67   53\n" +
                            "Granada        El Ejido        113   37\n" +
                            "Madrid         Guadalajara      68   45\n" +
                            "Guadalajara    Zaragoza        257   72\n" +
                            "Barcelona      Mataro           31   31\n" +
                            "Mataro         Girona           80   44\n" +
                            "Girona         Perpignan        93   38"
                    );
                    outputWriter.close();
                    String cidadeOrigem = spDeOnde.getSelectedItem().toString(), cidadeDestino = spParaOnde.getSelectedItem().toString();


                    Cidade origem = bhCidade.buscar(new Cidade(cidadeOrigem));
                    Cidade destino = bhCidade.buscar(new Cidade(cidadeDestino));
                    int distancia = Integer.parseInt(etDistancia.getText().toString().trim());
                    int tempo = Integer.parseInt(etTempo.getText().toString().trim());
                    PesoCidades p = new PesoCidades(tempo, distancia);

                    Aresta aresta = new Aresta(origem, destino, p);

                    fileout = openFileOutput("cidades.txt", MODE_APPEND);
                    outputWriter = new OutputStreamWriter(fileout);
                    outputWriter.write("\n" + aresta.paraArquivo());
                    outputWriter.close();

                    Toast.makeText(getApplicationContext(), "Inserido com sucesso", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(getApplicationContext(), MainActivity.class));

                }
                catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Campos errados", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
