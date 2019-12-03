//Gustavo Henrique de Meira - 18015
//Pedro Gomes Moreira - 18174

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

public class Desenhadora
{
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

    //Desenha no mapa o caminho e exibe os dados no TextView passado
    public void desenhaCaminho(
        Caminho caminho,
        TextView textView)
    {
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

            //Adiciona o conteúdo da aresta no textView
            textView.append(aresta.toString() + "\n");
        }

        textView.append("\nDistância total: " + caminho.getDistanciaTotal() + "\n");
        textView.append("Tempo total: " + caminho.getTempoTotal() + "\n");
    }
    public void limpar()
    {
        //Redesenha bitmap para limpar linhas feitas
        canvas.drawBitmap(imagem, 0, 0, null);
    }
}
