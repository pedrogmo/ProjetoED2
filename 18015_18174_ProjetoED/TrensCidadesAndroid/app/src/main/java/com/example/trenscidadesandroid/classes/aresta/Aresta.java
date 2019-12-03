package com.example.trenscidadesandroid.classes.aresta;

//Gustavo Henrique de Meira - 18015
//Pedro Gomes Moreira - 18174

import com.example.trenscidadesandroid.classes.cidade.Cidade;
import com.example.trenscidadesandroid.classes.linha.Linha;
import com.example.trenscidadesandroid.classes.utilildades.Utilidades;

import java.io.Serializable;

public class Aresta
    implements Serializable
{
    //Aributos da classe

    private Cidade origem;
    private Cidade destino;
    private int tempo;
    private int distancia;

    //Constantes para leitura do arquivo texto "grafo.txt"

    public static final int COMECO_NOME_ORIGEM = 0;
    public static final int TAMANHO_NOME_ORIGEM = 15;
    public static final int FIM_NOME_ORIGEM = COMECO_NOME_ORIGEM + TAMANHO_NOME_ORIGEM;

    public static final int COMECO_NOME_DESTINO = FIM_NOME_ORIGEM;
    public static final int TAMANHO_NOME_DESTINO = 16;
    public static final int FIM_NOME_DESTINO = COMECO_NOME_DESTINO + TAMANHO_NOME_DESTINO;

    public static final int COMECO_DISTANCIA = FIM_NOME_DESTINO;
    public static final int TAMANHO_DISTANCIA = 5;
    public static final int FIM_DISTANCIA = COMECO_DISTANCIA + TAMANHO_DISTANCIA;

    public static final int COMECO_TEMPO = FIM_DISTANCIA;

    //Consturtor de Aresta com os atributos correspondentes

    public Aresta(
        Cidade origem,
        Cidade destino,
        int tempo,
        int distancia) throws Exception
    {
        this.setOrigem(origem);
        this.setDestino(destino);
        this.setTempo(tempo);
        this.setDistancia(distancia);
    }

    //Consturtor de Aresta a partir de uma Linha lida do arquivo

    public Aresta(
        Linha linha) throws Exception
    {
        try
        {
            //Pega-se o conteúdo da string e se separam os dados presentes para cada atributo

            String str = linha.getConteudo();
            setOrigem(new Cidade(str.substring(COMECO_NOME_ORIGEM, FIM_NOME_ORIGEM).trim()));
            setDestino(new Cidade(str.substring(COMECO_NOME_DESTINO, FIM_NOME_DESTINO).trim()));
            setDistancia(Integer.parseInt(str.substring(COMECO_DISTANCIA, FIM_DISTANCIA).trim()));
            setTempo(Integer.parseInt(str.substring(COMECO_TEMPO).trim()));
        }
        catch (Exception exc)
        {
            //Caso alguma exceção seja lançada, joga-se outra indicando que houve erro na Linha

            throw new Exception("Aresta - contrutor de linha: linha inválida (" + exc.getMessage() + ")");
        }
    }

    //Getters e setters para cada atributo, sendo os setters private

    public Cidade getOrigem()
    {
        return origem;
    }

    public void setOrigem(
        Cidade origem) throws Exception
    {
        if (origem == null)
            throw new Exception("Aresta - setOrigem: cidade vazia");
        this.origem = origem;
    }

    public Cidade getDestino()
    {
        return destino;
    }

    public void setDestino(
        Cidade destino) throws Exception
    {
        if (destino == null)
            throw new Exception("Aresta - setDestino: cidade vazia");
        this.destino = destino;
    }

    public int getTempo()
    {
        return tempo;
    }

    private void setTempo(
            int tempo) throws Exception
    {
        if (tempo < 0)
            throw new Exception("Aresta - setTempo: valor negativo");
        this.tempo = tempo;
    }

    public int getDistancia()
    {
        return distancia;
    }

    private void setDistancia(
            int distancia) throws Exception
    {
        if (distancia < 0)
            throw new Exception("Aresta - setDistancia: valor negativo");
        this.distancia = distancia;
    }

    //método que retorna o formato de arquivo da Aresta

    public String paraArquivo()
    {
        String ret = Utilidades.padRight(origem.getNome(), TAMANHO_NOME_ORIGEM);
        ret += Utilidades.padRight(destino.getNome(), TAMANHO_NOME_DESTINO);
        ret += Utilidades.padRight(distancia + "", TAMANHO_DISTANCIA);
        ret += tempo + "";
        return ret;
    }

    //toString() indicando os valores da aresta

    public String toString()
    {
        String ret =
            Utilidades.padRight(origem.getNome(), Cidade.TAMANHO_NOME) + "->" +
            Utilidades.padRight(destino.getNome(), Cidade.TAMANHO_NOME) +
            "|" + tempo + " | " + distancia;
        return ret;
    }
}
