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
    public static final int TAMANHO_NOME_DESTINO = 15;
    public static final int FIM_NOME_DESTINO = COMECO_NOME_DESTINO + TAMANHO_NOME_DESTINO;

    public static final int COMECO_TEMPO = FIM_NOME_DESTINO;
    public static final int TAMANHO_TEMPO = 6;
    public static final int FIM_TEMPO = COMECO_TEMPO + TAMANHO_TEMPO;

    public static final int COMECO_DISTANCIA = FIM_TEMPO;

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
            setTempo(Integer.parseInt(str.substring(COMECO_TEMPO, FIM_TEMPO).trim()));
            setDistancia(Integer.parseInt(str.substring(COMECO_DISTANCIA).trim()));
        }
        catch (Exception exc)
        {
            //Caso alguma exceção seja lançada, joga-se outra indicando que houve erro na Linha

            throw new Exception("Aresta - contrutor de linha: linha inválida");
        }
    }

    //Getters e setters para cada atributo, sendo os setters private

    public Cidade getOrigem()
    {
        return origem;
    }

    private void setOrigem(
        Cidade origem)
    {
        this.origem = origem;
    }

    public Cidade getDestino()
    {
        return destino;
    }

    private void setDestino(
            Cidade destino)
    {
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
        ret += Utilidades.padRight(tempo + "", TAMANHO_TEMPO);
        ret += distancia + "";
        return ret;
    }

    //toString() indicando os valores da aresta

    public String toString()
    {
        String ret = origem.getNome() + " | " + destino.getNome() + " | " + tempo + " | " + distancia;
        return ret;
    }
}
