package com.example.trenscidadesandroid.classes.pesocidades;

//Gustavo Henrique de Meira - 18015
//Pedro Gomes Moreira - 18174

//Classe que guarda os diferentes pesos entre cidades
public class PesoCidades
{
    private int tempo;
    private int distancia;

    //Construtor com os pesos
    public PesoCidades(
        int tempo,
        int distancia) throws Exception
    {
        setDistancia(distancia);
        setTempo(tempo);
    }

    //Getters e setters (privativos) dos atributos

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
