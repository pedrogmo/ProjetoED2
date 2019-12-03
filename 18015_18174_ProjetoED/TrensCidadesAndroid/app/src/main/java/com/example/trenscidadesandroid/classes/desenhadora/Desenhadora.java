//Gustavo Henrique de Meira - 18015
//Pedro Gomes Moreira - 18174

package com.example.trenscidadesandroid.classes.desenhadora;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.trenscidadesandroid.R;
import com.example.trenscidadesandroid.classes.aresta.Aresta;
import com.example.trenscidadesandroid.classes.caminho.Caminho;

public class Desenhadora
{
    //Os diferentes tipos de célula presentes na tabela de resultado
    private enum TipoCelula
    {
        Cabecalho, Valor, Total
    }

    //Canvas usado para desenhar no componente
    private Canvas canvas;

    //Constantes

    //Tamanho da imagem
    private final static float TOTAL_X = 717.0f;
    private final static float TOTAL_Y = 578.0f;

    //Espessura da linha desenhada
    private static final float ESPESSURA = 7.5f;

    //Distância para uma ligação ser longa ou curta
    private static final int DIST_LINHA = 200;

    //Cores da linha
    private static final int COR_LINHA_LONGA = 0xFF800080;
    private static final int COR_LINHA_CURTA = Color.RED;
    private static final int COR_CABECALHO_TABELA = Color.DKGRAY;
    private static final int COR_TEXTO_CABECALHO = Color.WHITE;
    private static final int COR_TOTAL_TABELA = Color.LTGRAY;

    //Propriedades do formato (cor)
    private Paint paint;
    private Bitmap imagem;

    //Construtor da classe com o componente ImageView para botar a imagem e um objeto Resources
    public Desenhadora(
        ImageView imvMapa,
        Resources resources)
    {
        //definição do objeto de pincel para desenho
        this.paint = new Paint();
        this.paint.setStrokeWidth(ESPESSURA);

        //Pegar imagem do mapa da pasta drawable
        imagem = BitmapFactory.decodeResource(resources, R.drawable.mapa);

        //Bitmap de desenho por cima da imagem
        Bitmap bmpDesenho = Bitmap.createBitmap(imagem.getWidth(), imagem.getHeight(), Bitmap.Config.RGB_565);

        //Instanciação do canvas com o bitmap
        canvas = new Canvas(bmpDesenho);

        //Desenha a imagem com o canvas
        canvas.drawBitmap(imagem, 0, 0, null);

        //Imagem sendo posta no componente
        imvMapa.setImageDrawable(new BitmapDrawable(resources, bmpDesenho));
    }

    private void adicionarTexto(
        TableRow tr,
        String texto,
        TipoCelula tipoCelula)
    {
        TextView tv = new TextView(tr.getContext());
        if (tipoCelula == TipoCelula.Cabecalho)
        {
            tv.setBackgroundColor(COR_CABECALHO_TABELA);
            tv.setTextSize(20.0f);
            tv.setTextColor(COR_TEXTO_CABECALHO);
        }
        else if (tipoCelula == TipoCelula.Total)
        {
            tv.setBackgroundColor(COR_TOTAL_TABELA);
            tv.setTextSize(17.0f);
        }

        tv.setText(texto);
        tv.setPadding(15, 15, 15, 15);
        tr.addView(tv);
    }

    //Desenha no mapa o caminho e exibe os dados no TableLayout passado
    public void desenhaCaminho(
        Caminho caminho,
        TableLayout tabela)
    {
        Context contexto = tabela.getContext();

        //Adiciona-se uma linha de tabela para ser o cabeçalho
        TableRow tr = new TableRow(contexto);
        adicionarTexto(tr, "Origem", TipoCelula.Cabecalho);
        adicionarTexto(tr, "Destino", TipoCelula.Cabecalho);
        adicionarTexto(tr, "Distância", TipoCelula.Cabecalho);
        adicionarTexto(tr, "Tempo", TipoCelula.Cabecalho);
        tabela.addView(tr);

        //Para cada aresta presente no caminho
        for (Aresta aresta : caminho.getListaArestas())
        {
            //Determinação da cor da linha conforme distância
            if (aresta.getPesoCidades().getDistancia() < DIST_LINHA)
                this.paint.setColor(COR_LINHA_CURTA);
            else
                this.paint.setColor(COR_LINHA_LONGA);

            //Desenha linha entre as duas cidades adaptando a proporção
            canvas.drawLine(
                aresta.getOrigem().getX() * TOTAL_X * 2,
                aresta.getOrigem().getY() * TOTAL_Y * 2,
                aresta.getDestino().getX() * TOTAL_X * 2,
                aresta.getDestino().getY() * TOTAL_Y * 2,
                paint
            );

            //Adiciona-se uma linha de tabela com os dados [origem, destino, distância, tempo]
            tr = new TableRow(contexto);
            adicionarTexto(tr, aresta.getOrigem().getNome(), TipoCelula.Valor);
            adicionarTexto(tr, aresta.getDestino().getNome(), TipoCelula.Valor);
            adicionarTexto(tr, aresta.getPesoCidades().getDistancia() + "", TipoCelula.Valor);
            adicionarTexto(tr, aresta.getPesoCidades().getTempo() + "", TipoCelula.Valor);
            tabela.addView(tr);
        }

        //Linha de tabela com distância total do caminho
        tr = new TableRow(contexto);
        adicionarTexto(tr, "Distância", TipoCelula.Total);
        adicionarTexto(tr, "total: ", TipoCelula.Total);
        adicionarTexto(tr, caminho.getDistanciaTotal() + "", TipoCelula.Total);
        tabela.addView(tr);

        //Linha de tabela com tempo total do caminho
        tr = new TableRow(contexto);
        adicionarTexto(tr, "Tempo", TipoCelula.Total);
        adicionarTexto(tr, "total: ", TipoCelula.Total);
        adicionarTexto(tr, caminho.getTempoTotal() + "", TipoCelula.Total);
        tabela.addView(tr);
    }
    public void limpar()
    {
        //Redesenha bitmap para limpar linhas feitas
        canvas.drawBitmap(imagem, 0, 0, null);
    }
}
