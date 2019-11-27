package com.example.trenscidadesandroid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

//Gustavo Henrique de Meira - 18015
//Pedro Gomes Moreira - 18174

public class CanvasView extends View
{
    private static final float TOTAL_X = 358.5f;
    private static final float TOTAL_Y = 289f;
    private static final float ESPESSURA = 5.0f;
    private static final int COR_LINHA = Color.RED;

    private Paint paint; //propriedades do formato (cor)

    public CanvasView(
        Context context)
    {
        super(context);
    }

    @Override
    protected void onDraw(
        Canvas canvas)
    {
        super.onDraw(canvas);

        Log.d("MSG", "chegou");

        this.paint = new Paint();
        this.paint.setColor(COR_LINHA);
        this.paint.setStrokeWidth(ESPESSURA);

        //pega imagem põe no bitmap
        Bitmap imagem = BitmapFactory.decodeResource(getResources(), R.drawable.mapa);
        canvas.drawBitmap(imagem, 0, 0,null);


    }
}
