package com.example.trenscidadesandroid.classes.aresta;

import com.example.trenscidadesandroid.classes.cidade.Cidade;
import com.example.trenscidadesandroid.classes.linha.Linha;
import com.example.trenscidadesandroid.classes.utilildades.Utilidades;

public class Aresta
{
    public static final int COMECO_NOME_ORIGEM = 0;
    public static final int TAMANHO_NOME_ORIGEM = 15;
    public static final int FIM_NOME_ORIGEM = COMECO_NOME_ORIGEM + TAMANHO_NOME_ORIGEM;

    public static final int COMECO_NOME_DESTINO = FIM_NOME_ORIGEM;
    public static final int TAMANHO_NOME_DESTINO = 15;
    public static final int FIM_NOME_DESTINO = COMECO_NOME_DESTINO + TAMANHO_NOME_DESTINO;

    public static final int COMECO_TEMPO = FIM_NOME_DESTINO;
    public static final int TAMANHO_TEMPO = 6;
    public static final int FIM_TEMPO = COMECO_TEMPO + TAMANHO_TEMPO;

    public static final int COMECO_DISTANCIA = FIM_TEMPO;


    private Cidade origem;
    private Cidade destino;
    private int tempo;
    private int distancia;

    public Aresta(
        Cidade origem,
        Cidade destino,
        int tempo,
        int distancia) throws Exception
    {
        this.setOrigem(origem);
        this.setDestino(destino);
        this.setTempo(tempo);
        this.setDistancia(distancia);
    }

    public Aresta(
        Linha linha) throws Exception
    {
        try
        {
            String str = linha.getConteudo();
            setOrigem(new Cidade(str.substring(COMECO_NOME_ORIGEM, FIM_NOME_ORIGEM).trim()));
            setDestino(new Cidade(str.substring(COMECO_NOME_DESTINO, FIM_NOME_DESTINO).trim()));
            setTempo(Integer.parseInt(str.substring(COMECO_TEMPO, FIM_TEMPO).trim()));
            setDistancia(Integer.parseInt(str.substring(COMECO_DISTANCIA).trim()));
        }
        catch (Exception exc)
        {
            throw new Exception("Aresta - contrutor de linha: linha inválida");
        }
    }

    public Cidade getOrigem()
    {
        return origem;
    }

    private void setOrigem(
        Cidade origem)
    {
        this.origem = origem;
    }

    public Cidade getDestino()
    {
        return destino;
    }

    private void setDestino(
            Cidade destino)
    {
        this.destino = destino;
    }

    public int getTempo()
    {
        return tempo;
    }

    private void setTempo(
            int tempo) throws Exception
    {
        if (tempo < 0)
            throw new Exception("Aresta - setTempo: valor negativo");
        this.tempo = tempo;
    }

    public int getDistancia()
    {
        return distancia;
    }

    private void setDistancia(
            int distancia) throws Exception
    {
        if (distancia < 0)
            throw new Exception("Aresta - setDistancia: valor negativo");
        this.distancia = distancia;
    }

    public String paraArquivo()
    {
        String ret = Utilidades.padRight(origem.getNome(), TAMANHO_NOME_ORIGEM);
        ret += Utilidades.padRight(destino.getNome(), TAMANHO_NOME_DESTINO);
        ret += Utilidades.padRight(tempo + "", TAMANHO_TEMPO);
        ret += distancia + "";
        return ret;
    }
}
