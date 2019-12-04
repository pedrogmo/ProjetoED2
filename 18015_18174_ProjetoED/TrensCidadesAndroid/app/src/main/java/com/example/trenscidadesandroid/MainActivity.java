//Gustavo Henrique de Meira - 18015
//Pedro Gomes Moreira - 18174

package com.example.trenscidadesandroid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trenscidadesandroid.classes.aresta.Aresta;
import com.example.trenscidadesandroid.classes.buckethash.BucketHash;
import com.example.trenscidadesandroid.classes.caminho.Caminho;
import com.example.trenscidadesandroid.classes.cidade.Cidade;
import com.example.trenscidadesandroid.classes.desenhadora.Desenhadora;
import com.example.trenscidadesandroid.classes.grafo.Grafo;
import com.example.trenscidadesandroid.classes.linha.Linha;
import com.example.trenscidadesandroid.classes.lista.Lista;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{

    private Spinner spDeOnde, spParaOnde;
    private Desenhadora desenhadora;
    private ImageView ivCanvas;
    private Button btnBuscar, btnAdicionarCidade, btnAdicionarCaminho;
    private TableLayout tlResultados;
    private Grafo grafo;
    private Lista<Cidade> cidadesLidas;
    private RadioButton rbTempo, rbDistancia;

    private static BucketHash<Cidade> bhCidade;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spDeOnde = findViewById(R.id.spDeOndeMain);
        spParaOnde = findViewById(R.id.spParaOndeMain);
        btnBuscar = findViewById(R.id.btnBuscar);
        btnAdicionarCidade = findViewById(R.id.btnAdicionarCidade);
        btnAdicionarCaminho = findViewById(R.id.btnAdicionarCaminho);
        ivCanvas = findViewById(R.id.ivCanvas);
        tlResultados = findViewById(R.id.tlResultados);
        rbTempo = findViewById(R.id.rbTempo);
        rbDistancia = findViewById(R.id.rbDistancia);

        //Radio button é marcado como default
        rbTempo.setChecked(true);

        cidadesLidas = new Lista<Cidade>();


        //Instanciação da desenhadora de grafo
        desenhadora = new Desenhadora(this.ivCanvas, getResources());

        bhCidade = new BucketHash<Cidade>();

        //ArrayList<String> é exigido pelo Spinner
        final ArrayList<String> listaNomesCidades = new ArrayList<String>();

        try
        {
            //try que tentará abrir um arquivo. Caso não exista, cairá no catch
            try
            {
                FileInputStream f = openFileInput("cidades.txt");
            }
            //Como o arquivo não existe, cria-o com o método escreverCidades()
            catch(FileNotFoundException exc){escreverCidades();}

            FileInputStream fileIn = openFileInput("cidades.txt");
            InputStreamReader inputRead = new InputStreamReader(fileIn);
            BufferedReader leitor = new BufferedReader(inputRead);

            String recebeString;
            //Lê-se as cidades e as adiciona na lista de cidades lidas,
            //para instanciarmos o grafo com o número de cidades na lista futuramente
            while((recebeString = leitor.readLine()) != null)
            {
                Cidade cd = new Cidade(new Linha(recebeString));
                cidadesLidas.inserirFim(cd);
            }

            leitor.close();
            //Instância do grafo com a qtd de elementos na lista
            grafo = new Grafo(cidadesLidas.getQuantidade());

            //foreach que adiciona as cidades na lista de nomes das cidades, BucketHash e inclui um novo vértice da cidade no grafo
            for(Cidade cd: cidadesLidas)
            {
                listaNomesCidades.add(cd.toString());
                bhCidade.inserir(cd);
                grafo.novoVertice(cd);
            }

            //try que tentará abrir um arquivo. Caso não exista, cairá no catch
            try
            {
                FileInputStream f = openFileInput("grafo.txt");
            }
            //Como o arquivo não existe, cria-o com o método escreverCidades()
            catch(FileNotFoundException exc){escreverGrafo();}

            fileIn = openFileInput("grafo.txt");
            inputRead = new InputStreamReader(fileIn);
            leitor = new BufferedReader(inputRead);

            while((recebeString = leitor.readLine()) != null)
            {
                Aresta a = new Aresta(new Linha(recebeString));

                //Redefinindo as cidades da aresta buscando na tabela hash
                a.setOrigem(bhCidade.buscar(a.getOrigem()));
                a.setDestino(bhCidade.buscar(a.getDestino()));

                grafo.novaAresta(a);
            }
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

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
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
                //Serializa a tabela de hash para a tela de adição de cidades
                Intent i = new Intent(getApplicationContext(), AdicionarCidade.class);
                i.putExtra("hash", (Serializable) bhCidade);
                startActivity(i);
            }
        });

        btnAdicionarCaminho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Serializa a tabela de hash para a tela de adição de caminhos e a lista dos nomes para os spinners na tela contidos
                Intent i = new Intent(getApplicationContext(), AdicionarCaminho.class);
                i.putExtra("listaNomesCidades", (Serializable) listaNomesCidades);
                i.putExtra("hash", (Serializable) bhCidade);
                startActivity(i);
            }
        });

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    //Obtem-se as opções escolhidas
                    String stringCidadeOrigem = spDeOnde.getSelectedItem().toString(),
                            stringCidadeDestino = spParaOnde.getSelectedItem().toString();

                    //Separa-se o código do nome por um split
                    String[] vetor1 = stringCidadeOrigem.split("-");
                    String[] vetor2 = stringCidadeDestino.split("-");

                    // Obtem-se os nomes das cidades
                    String  cidadeOrigem = vetor1[1].substring(1),
                            cidadeDestino = vetor2[1].substring(1);

                    //Busca, pelos seus nomes, os respectivos códigos das cidades no BucketHash
                     Cidade origem = bhCidade.buscar(new Cidade(cidadeOrigem)),
                            destino = bhCidade.buscar(new Cidade(cidadeDestino));

                     if (origem.equals(destino))
                     {
                         Toast.makeText(getApplicationContext(), "Cidades iguais", Toast.LENGTH_SHORT).show();
                         return;
                     }

                    Grafo.ModoBusca modoBusca;

                    //Verifica qual o modo de busca selecionado
                    if (rbTempo.isChecked())
                        modoBusca = Grafo.ModoBusca.PorMenorTempo;
                    else
                        modoBusca = Grafo.ModoBusca.PorMenorDistancia;

                    //Busca-se o caminho passando a origem, o destino e o modo de busca
                    Caminho c = grafo.getCaminho(origem, destino, modoBusca);
                    tlResultados.removeAllViews();
                    desenhadora.limpar();

                    if (c.isVazio())
                        //Se não se achou um caminho
                        Toast.makeText(getApplicationContext(), "Caminho impossível", Toast.LENGTH_SHORT).show();
                    else
                    {
                        //Desenha o caminho e escreve as rotas no TextView
                        desenhadora.desenhaCaminho(c, tlResultados);
                        Toast.makeText(getApplicationContext(), "Caminho encontrado", Toast.LENGTH_SHORT).show();
                    }

                }
                catch (Exception e){Toast.makeText(getApplicationContext(), "Busca não foi possível", Toast.LENGTH_SHORT).show();}

            }
        });

    }

    private void escreverCidades()
    {
        try
        {
            //Abre arquivo modo criação para escrever
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
        }
        catch (Exception exc)
        {
            Toast.makeText(
                    getApplicationContext(),
                    "Erro na escrita do arquivo de cidades",
                    Toast.LENGTH_SHORT
            ).show();

            Log.d("MSG_ERR", exc.toString());
        }
    }

    private void escreverGrafo()
    {
        try
        {
            //Abre arquivo modo criação para escrever
            FileOutputStream fileout = openFileOutput("grafo.txt", MODE_PRIVATE);
            OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
            outputWriter.write("" +
                    "Madrid         Salamanca       220  150\n" +
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
        }
        catch (Exception exc)
        {
            Toast.makeText(
                    getApplicationContext(),
                    "Erro na escrita do arquivo de grafos",
                    Toast.LENGTH_SHORT
            ).show();

            Log.d("MSG_ERR", exc.toString());
        }
    }
}
