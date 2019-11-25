package com.example.trenscidadesandroid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

//Gustavo Henrique de Meira - 18015
//Pedro Gomes Moreira - 18174

public class CanvasView extends View
{
    private final float mapaTotalX = 358.5f;
    private final float mapaTotalY = 289f;

    private Paint paint; //propriedades do formato (cor)

    public CanvasView(
        Context context)
    {
        super(context);
    }

    @Override
    protected void onDraw(
            Canvas canvas) //canvas para formas geométricas
    {
        super.onDraw(canvas);
        this.paint = new Paint();
        this.paint.setColor(Color.RED);

        //pega imagem põe no bitmap
        Bitmap imagem = BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_name);

        canvas.drawBitmap(imagem, 0, 0, null);

        final float espessura = 5.0f;

        paint.setStrokeWidth(espessura);
    }
}
