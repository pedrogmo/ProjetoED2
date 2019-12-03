package com.example.trenscidadesandroid.classes.desenhadora;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

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

    private static float TOTAL_X;
    private static float TOTAL_Y;
    private static final float PROPORCAO_IMAGEM = 717.0f / 578.0f;
    private static final float ESPESSURA = 5.0f;
    private static final int DIST_LINHA = 200;
    private static final int COR_LINHA_LONGA = 0xFF800080;
    private static final int COR_LINHA_CURTA = Color.RED;
    private Paint paint; //propriedades do formato (cor)

    public Desenhadora(
        ImageView imvMapa,
        Resources resources,
        int largura)
    {
        this.TOTAL_X = largura;
        this.TOTAL_Y = this.TOTAL_X / PROPORCAO_IMAGEM;

        this.imvMapa = imvMapa;
        this.resources = resources;

        this.paint = new Paint();
        this.paint.setStrokeWidth(ESPESSURA);

        Bitmap imagem = BitmapFactory.decodeResource(this.resources, R.drawable.mapa);
        Bitmap tempBitmap = Bitmap.createBitmap(imagem.getWidth(), imagem.getHeight(), Bitmap.Config.RGB_565);

        canvas = new Canvas(tempBitmap);
        canvas.drawBitmap(imagem, 0, 0, null);

        this.imvMapa.setImageDrawable(new BitmapDrawable(this.resources, tempBitmap));
    }

    public void desenhaCaminho(
        Caminho caminho)
    {
        for (Aresta aresta : caminho.getListaArestas())
        {
            if (aresta.getDistancia() < DIST_LINHA)
                this.paint.setColor(COR_LINHA_CURTA);
            else
                this.paint.setColor(COR_LINHA_LONGA);

            canvas.drawLine(
                aresta.getOrigem().getX() * TOTAL_X,
                aresta.getOrigem().getY() * TOTAL_Y,
                aresta.getDestino().getX() * TOTAL_X,
                aresta.getDestino().getY() * TOTAL_Y,
                paint
            );
        }
    }
}
