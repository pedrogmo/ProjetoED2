package com.example.trenscidadesandroid.classes.desenhadora;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trenscidadesandroid.R;
import com.example.trenscidadesandroid.classes.aresta.Aresta;
import com.example.trenscidadesandroid.classes.caminho.Caminho;

//Gustavo Henrique de Meira - 18015
//Pedro Gomes Moreira - 18174

public class Desenhadora
{
    private ImageView imvMapa;
    private Resources resources;
    private Canvas canvas;

    private final static float TOTAL_X = 717.0f;
    private final static float TOTAL_Y = 578.0f;
    //private static final float PROPORCAO_IMAGEM = 717.0f / 578.0f;
    private static final float ESPESSURA = 5.0f;
    private static final int DIST_LINHA = 200;
    private static final int COR_LINHA_LONGA = 0xFF800080;
    private static final int COR_LINHA_CURTA = Color.RED;
    private Paint paint; //propriedades do formato (cor)
    private Bitmap imagem;

    public Desenhadora(
        ImageView imvMapa,
        Resources resources,
        int largura)
    {
        //this.TOTAL_X = largura;
        //this.TOTAL_Y = this.TOTAL_X / PROPORCAO_IMAGEM;

        this.imvMapa = imvMapa;
        this.resources = resources;

        this.paint = new Paint();
        this.paint.setStrokeWidth(ESPESSURA);

        imagem = BitmapFactory.decodeResource(this.resources, R.drawable.mapa);
        Bitmap tempBitmap = Bitmap.createBitmap(imagem.getWidth(), imagem.getHeight(), Bitmap.Config.RGB_565);

        canvas = new Canvas(tempBitmap);
        canvas.drawBitmap(imagem, 0, 0, null);

        this.imvMapa.setImageDrawable(new BitmapDrawable(this.resources, tempBitmap));
    }

    public void desenhaCaminho(
            Caminho caminho,
            TextView textView)
    {
        for (Aresta aresta : caminho.getListaArestas())
        {
            if (aresta.getPesoCidades().getDistancia() < DIST_LINHA)
                this.paint.setColor(COR_LINHA_CURTA);
            else
                this.paint.setColor(COR_LINHA_LONGA);

            canvas.drawLine(
                aresta.getOrigem().getX() * TOTAL_X * 2,
                aresta.getOrigem().getY() * TOTAL_Y * 2,
                aresta.getDestino().getX() * TOTAL_X * 2,
                aresta.getDestino().getY() * TOTAL_Y * 2,
                paint
            );

            textView.append(aresta.toString() + "\n");
        }

        textView.append("\nDistância total: " + caminho.getDistanciaTotal() + "\n");
        textView.append("Tempo total: " + caminho.getTempoTotal() + "\n");
    }
    public void limpar()
    {
        canvas.drawBitmap(imagem, 0, 0, null);
    }
}
