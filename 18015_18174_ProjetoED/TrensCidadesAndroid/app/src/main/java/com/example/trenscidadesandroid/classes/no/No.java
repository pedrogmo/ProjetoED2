package com.example.trenscidadesandroid.classes.no;

import com.example.trenscidadesandroid.classes.utilildades.Utilidades;

import java.io.Serializable;

public class No<T>
    implements Serializable
{
    private T info;
    public No<T> prox;

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
