package com.example.trenscidadesandroid.classes.desenhadora;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.widget.ImageView;

import com.example.trenscidadesandroid.R;
import com.example.trenscidadesandroid.classes.caminho.Caminho;
import com.example.trenscidadesandroid.classes.cidade.Cidade;
import com.example.trenscidadesandroid.classes.lista.Lista;

//Gustavo Henrique de Meira - 18015
//Pedro Gomes Moreira - 18174

public class Desenhadora
{
    private ImageView imvMapa;
    private Resources resources;
    private Canvas canvas;

    private static final float TOTAL_X = 358.5f;
    private static final float TOTAL_Y = 289f;
    private static final float ESPESSURA = 5.0f;
    private static final int COR_LINHA = Color.RED;
    private Paint paint; //propriedades do formato (cor)

    public Desenhadora(
        ImageView imvMapa,
        Resources resources)
    {
        this.imvMapa = imvMapa;
        this.resources = resources;

        this.paint = new Paint();
        this.paint.setColor(COR_LINHA);
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
        
    }
}
