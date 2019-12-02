package com.example.trenscidadesandroid.classes.no;

import com.example.trenscidadesandroid.classes.utilildades.Utilidades;

import java.io.Serializable;

//Classe Nó de informação genérica
public class No<T>
    implements Serializable
{
    //tipo genérico de dado
    private T info;
    //próximo nó de informação genérica
    public No<T> prox;

    //Construtor com a informação e o próximo nó, permitindo percurso na lista
    public No(
            T info,
            No<T> prox) throws Exception
    {
        setInfo(info);
        this.prox = prox;
    }

    public T getInfo()
    {
        return info;
    }

    //altera a informação do nó com a informação passada
    public void setInfo(
            T info) throws Exception
    {
        if (info == null)
            throw new Exception("No<T> - setInfo: info nula");
        if (info instanceof Cloneable)
            this.info = (T) Utilidades.cloneDe(info);
        this.info = info;
    }
}
