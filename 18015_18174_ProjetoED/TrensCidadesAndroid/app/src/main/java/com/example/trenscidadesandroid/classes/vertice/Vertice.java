package com.example.trenscidadesandroid.classes.vertice;

//Gustavo Henrique de Meira - 18015
//Pedro Gomes Moreira - 18174

public class Vertice<T>
{
    public boolean foiVisitado;
    private T info;

    public Vertice(
        T informacao) throws Exception
    {
        setInfo(informacao);
        foiVisitado = false;
    }

    public T getInfo()
    {
        return info;
    }

    public void setInfo(
        T info) throws Exception
    {
        if (info == null)
            throw new Exception("Vertice<T> - setInfo: dado invalido");
        this.info = info;
    }
}