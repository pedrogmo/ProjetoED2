package com.example.trenscidadesandroid.classes.linha;

//Gustavo Henrique de Meira - 18015
//Pedro Gomes Moreira - 18174

import java.io.Serializable;

public class Linha
        implements Serializable
{
    private String conteudo;

    public Linha(
        String conteudo) throws Exception
    {
        setConteudo(conteudo);
    }

    public String getConteudo()
    {
        return conteudo;
    }

    public void setConteudo(
        String conteudo) throws Exception
    {
        if (conteudo == null || conteudo.equals(""))
            throw new Exception("Linha - setConteudo: string inválida");
        this.conteudo = conteudo;
    }
}
