package com.example.trenscidadesandroid.classes.vertice;

//Gustavo Henrique de Meira - 18015
//Pedro Gomes Moreira - 18174

import com.example.trenscidadesandroid.classes.cidade.Cidade;

import java.io.Serializable;

public class Vertice
    implements Serializable
{
    public boolean foiVisitado;
    private Cidade info;

    public Vertice(
            Cidade informacao) throws Exception
    {
        setInfo(informacao);
        foiVisitado = false;
    }

    public Cidade getInfo()
    {
        return info;
    }

    public void setInfo(
            Cidade info) throws Exception
    {
        if (info == null)
            throw new Exception("Vertice<T> - setInfo: dado invalido");
        this.info = (Cidade) info.clone();
    }
}