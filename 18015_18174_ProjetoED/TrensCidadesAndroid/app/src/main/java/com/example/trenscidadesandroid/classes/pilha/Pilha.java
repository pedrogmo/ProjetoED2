package com.example.trenscidadesandroid.classes.pilha;

//Gustavo Henrique de Meira - 18015
//Pedro Gomes Moreira - 18174

import com.example.trenscidadesandroid.classes.lista.Lista;

import java.io.Serializable;

public class Pilha<T>
    implements Serializable
{
    private Lista<T> lista;

    public Pilha()
    {
        lista = new Lista<T>();
    }

    public boolean isVazia()
    {
        return lista.isVazia();
    }

    public void empilhar(
        T informacao) throws Exception
    {
        lista.inserirInicio(informacao);
    }

    public T desempilhar()
    {
        T dado = lista.getDadoInicio();
        lista.excluirInicio();
        return dado;
    }

    public T topo()
    {
        return lista.getDadoInicio();
    }
}
