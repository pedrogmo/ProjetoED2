//Gustavo Henrique de Meira - 18015
//Pedro Gomes Moreira - 18174

package com.example.trenscidadesandroid.classes.vertice;

import com.example.trenscidadesandroid.classes.cidade.Cidade;

import java.io.Serializable;
// Classe que representa cada vértice do grafo
public class Vertice
    implements Serializable
{
    //boolean de controle para o acesso do vértice
    public boolean foiVisitado;
    //Cidade armazenada no vértice
    private Cidade info;
    //Construtor de vertice que recebe a cidade como parâmetro
    public Vertice(
            Cidade informacao) throws Exception
    {
        setInfo(informacao);
        foiVisitado = false;
    }
    //getter
    public Cidade getInfo()
    {
        return info;
    }
    //setter
    private void setInfo(
            Cidade info) throws Exception
    {
        if (info == null)
            throw new Exception("Vertice<T> - setInfo: dado invalido");
        this.info = (Cidade) info.clone();
    }
}