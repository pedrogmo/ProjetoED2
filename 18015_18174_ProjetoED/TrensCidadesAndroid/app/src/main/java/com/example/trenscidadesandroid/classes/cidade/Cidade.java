package com.example.trenscidadesandroid.classes.cidade;

//Gustavo Henrique de Meira - 18015
//Pedro Gomes Moreira - 18174

import com.example.trenscidadesandroid.classes.linha.Linha;

import java.io.Serializable;

public class Cidade
    implements Comparable<Cidade>, Serializable
{
    private int codigo;
    //Posição em porcentagem da imagem
    private double x, y;
    private String nome;

    public static final int COMECO_CODIGO = 0;
    public static final int TAMANHO_CODIGO = 2;
    public static final int FIM_CODIGO = COMECO_CODIGO + TAMANHO_CODIGO;

    public static final int COMECO_NOME = FIM_CODIGO;
    public static final int TAMANHO_NOME = 16;
    public static final int FIM_NOME = COMECO_NOME + TAMANHO_NOME;

    public static final int COMECO_X = FIM_NOME;
    public static final int TAMANHO_X = 6;
    public static final int FIM_X = COMECO_X + TAMANHO_X;

    public static final int COMECO_Y = FIM_X;
    public static final int TAMANHO_Y = 5;

    public Cidade(
        int codigo,
        String nome,
        double posicaoX,
        double posicaoY) throws Exception
    {
        setCodigo(codigo);
        setNome(nome);
        setX(posicaoX);
        setY(posicaoY);
    }

    public Cidade(
        Linha linha) throws Exception
    {
        try
        {
            String str = linha.getConteudo();
            setCodigo(Integer.parseInt(str.substring(COMECO_CODIGO, FIM_CODIGO).trim()));
            setNome(str.substring(COMECO_NOME, FIM_NOME).trim());
            setX(Double.parseDouble(str.substring(COMECO_X, FIM_X).trim()));
            setY(Double.parseDouble(str.substring(COMECO_Y).trim()));
        }
        catch (Exception exc)
        {
            throw new Exception(exc.getMessage());
        }
    }

    public Cidade(
        String nome) throws Exception
    {
        codigo = 0;
        setNome(nome);
        x = 0.0;
        y = 0.0;
    }

    public int getCodigo()
    {
        return codigo;
    }

    public void setCodigo(
        int codigo) throws Exception
    {
        if (codigo < 0)
            throw new Exception("Cidade - setCodigo: codigo negativo");
        this.codigo = codigo;
    }

    public double getX()
    {
        return x;
    }

    public void setX(
        double x) throws Exception
    {
        if (x < 0.0 || x > 1.0)
            throw new Exception("Cidade - setX: valor inválido");
        this.x = x;
    }

    public double getY()
    {
        return y;
    }

    public void setY(
        double y) throws Exception
    {
        if (y < 0.0 || y > 1.0)
            throw new Exception("Cidade - setY: valor inválido");
        this.y = y;
    }

    public String getNome()
    {
        return nome;
    }

    public void setNome(
        String nome) throws Exception
    {
        if (nome == null || nome.equals(""))
            throw new Exception("Cidade - setNome: string inválida");
        this.nome = nome;
    }

    public int compareTo(
        Cidade c)
    {
        return codigo - c.codigo;
    }

    public String toString()
    {
        return codigo + " - " + nome;
    }

    public String paraArquivo()
    {
        String ret = padRight(codigo + "", TAMANHO_CODIGO);
        ret += padRight(nome, TAMANHO_NOME);
        ret += padRight(x + "", TAMANHO_X);
        ret += padRight(y + "", TAMANHO_Y);
        return ret;
    }

    public boolean equals(
        Object obj)
    {
        if (this == obj)
        return true;

        if (obj == null)
        return false;

        Cidade c = (Cidade)obj;

        return nome.equals(c.nome);
    }

    public int hashCode()
    {
        int hash = 1;
        for(char c : nome.toCharArray())
            hash = 37 * hash + c;
        return hash;
    }

    private static String padRight(
        String s,
        int n)
    {
        return String.format("%-" + n + "s", s);
    }
}
