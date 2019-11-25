package com.example.trenscidadesandroid.classes.no;

public class No<T>
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
        this.info = info;
    }
}
