package com.example.trenscidadesandroid.classes.fila;

//Gustavo Henrique de Meira - 18015
//Pedro Gomes Moreira - 18174

import com.example.trenscidadesandroid.classes.lista.Lista;

import java.io.Serializable;

public class Fila<T>
    implements Serializable
{
    private Lista<T> lista;

    public Fila()
    {
        lista = new Lista<T>();
    }

    public boolean isVazia()
    {
        return lista.isVazia();
    }

    public void enfileirar(
        T informacao) throws Exception
    {
        lista.inserirFim(informacao);
    }

    public T desenfileirar()
    {
        T dado = lista.getDadoInicio();
        lista.excluirInicio();
        return dado;
    }

    public T inicio()
    {
        return lista.getDadoInicio();
    }
}
