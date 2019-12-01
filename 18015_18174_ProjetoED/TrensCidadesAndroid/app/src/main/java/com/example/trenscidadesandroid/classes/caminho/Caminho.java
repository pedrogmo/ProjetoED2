package com.example.trenscidadesandroid.classes.caminho;

//Gustavo Henrique de Meira - 18015
//Pedro Gomes Moreira - 18174

import com.example.trenscidadesandroid.classes.aresta.Aresta;
import com.example.trenscidadesandroid.classes.lista.Lista;

public class Caminho
{
    private Lista<Aresta> listaArestas;
    private int distanciaTotal;
    private int tempoTotal;

    public Caminho()
    {
        listaArestas = new Lista<Aresta>();
        distanciaTotal = 0;
        tempoTotal = 0;
    }

    public void adicionar(
        Aresta novaAresta) throws Exception
    {
        listaArestas.inserirFim(novaAresta);
    }

    public Lista<Aresta> getListaArestas()
    {
        return listaArestas;
    }

    public int getDistanciaTotal()
    {
        return distanciaTotal;
    }

    public int getTempoTotal()
    {
        return tempoTotal;
    }

    public boolean isVazio()
    {
        return listaArestas.isVazia();
    }
}
