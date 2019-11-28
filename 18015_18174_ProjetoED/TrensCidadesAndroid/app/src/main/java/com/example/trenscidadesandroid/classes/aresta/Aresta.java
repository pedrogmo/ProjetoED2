package com.example.trenscidadesandroid.classes.aresta;

import com.example.trenscidadesandroid.classes.cidade.Cidade;
import com.example.trenscidadesandroid.classes.pesocidades.PesoCidades;

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
    private PesoCidades peso;

    public Aresta(
        Cidade origem,
        Cidade destino,
        PesoCidades peso) throws Exception
    {
        this.setOrigem(origem);
        this.setDestino(destino);
        this.setPeso(peso);
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

    public PesoCidades getPeso()
    {
        return peso;
    }

    private void setPeso(
        PesoCidades peso) throws Exception
    {
        if (peso == null)
            throw new Exception("Aresta - setPeso: valor nulo");
        this.peso = peso;
    }

    public String paraArquivo()
    {
        String ret = padRight(origem.getNome(), TAMANHO_NOME_ORIGEM);
        ret += padRight(destino.getNome(), TAMANHO_NOME_DESTINO);
        ret += padRight(peso.getTempo() + "", TAMANHO_TEMPO);
        ret += peso.getDistancia() + "";
        return ret;
    }

    private static String padRight(
            String s,
            int n)
    {
        return String.format("%-" + n + "s", s);
    }
}
