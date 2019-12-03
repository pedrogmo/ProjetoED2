//Gustavo Henrique de Meira - 18015
//Pedro Gomes Moreira - 18174

package com.example.trenscidadesandroid.classes.linha;

import java.io.Serializable;

//Classe para diferenciar o construtor de nome do construtor em que é passado uma linha inteira
public class Linha
    implements Serializable
{
    //Conteúdo string interno da Linha
    private String conteudo;

    //Construtor com string
    public Linha(
        String conteudo) throws Exception
    {
        setConteudo(conteudo);
    }

    //Getter e setter

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
