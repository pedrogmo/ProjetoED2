package com.example.trenscidadesandroid.classes.pesocidades;

public class PesoCidades
{
    private int tempo;
    private int distancia;

    public PesoCidades(
        int tempo,
        int distancia) throws Exception
    {
        setTempo(tempo);
        setDistancia(distancia);
    }

    public int getTempo()
    {
        return tempo;
    }

    private void setTempo(
        int tempo) throws Exception
    {
        if (tempo < 0)
            throw new Exception("PesoCidades - setTempo: valor negativo");
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
            throw new Exception("PesoCidades - setDistancia: valor negativo");
        this.distancia = distancia;
    }
}
