//Gustavo Henrique de Meira - 18015
//Pedro Gomes Moreira - 18174

package com.example.trenscidadesandroid.classes.caminho;

import com.example.trenscidadesandroid.classes.aresta.Aresta;
import com.example.trenscidadesandroid.classes.lista.Lista;

public class Caminho
{
    //Lista de Arestas entre cidades presentes no caminho
    private Lista<Aresta> listaArestas;

    //Somatória da distância das arestas
    private int distanciaTotal;

    //Somatória do tempo das arestas
    private int tempoTotal;

    //Construtor vazio do Caminho
    public Caminho()
    {
        listaArestas = new Lista<Aresta>();
        distanciaTotal = 0;
        tempoTotal = 0;
    }

    public void adicionar(
        Aresta novaAresta) throws Exception
    {
        //Adiciona-se a aresta na lista
        listaArestas.inserirFim(novaAresta);

        //Adicionam-se os valores de distância e tempo
        distanciaTotal += novaAresta.getPesoCidades().getDistancia();
        tempoTotal += novaAresta.getPesoCidades().getTempo();
    }

    //Getters dos atributos

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

    //Para saber se o caminho é válido, retorna-se se a lista está vazia

    public boolean isVazio()
    {
        return listaArestas.isVazia();
    }
}
