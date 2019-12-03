//Gustavo Henrique de Meira - 18015
//Pedro Gomes Moreira - 18174

package com.example.trenscidadesandroid.classes.cidade;

import com.example.trenscidadesandroid.classes.linha.Linha;
import com.example.trenscidadesandroid.classes.utilildades.Utilidades;

import java.io.Serializable;

public class Cidade
    implements Comparable<Cidade>, Serializable, Cloneable
{
    //Atributos da Cidade

    private int codigo;
    //Posição da cidade no mapa em porcentagem
    private float x, y;
    private String nome;

    //Constantes para leitura do arquivo "cidades.txt"

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

    //Construtor de Cidade com cada atributo dela

    public Cidade(
        int codigo,
        String nome,
        float posicaoX,
        float posicaoY) throws Exception
    {
        setCodigo(codigo);
        setNome(nome);
        setX(posicaoX);
        setY(posicaoY);
    }

    //Construtor de Cidade a partir de uma linha lida do arquivo

    public Cidade(
        Linha linha) throws Exception
    {
        try
        {
            //Separam-se os dados da String para cada atributo correspondente

            String str = linha.getConteudo();
            setCodigo(Integer.parseInt(str.substring(COMECO_CODIGO, FIM_CODIGO).trim()));
            setNome(str.substring(COMECO_NOME, FIM_NOME).trim());
            setX(Float.parseFloat(str.substring(COMECO_X, FIM_X).trim()));
            setY(Float.parseFloat(str.substring(COMECO_Y).trim()));
        }
        catch (Exception exc)
        {
            //Se houve erro, joga-se uma exceção
            throw new Exception("Cidade - contrutor de linha: linha inválida");
        }
    }

    //Construtor de Cidade com o seu nome, para ser usado como chave

    public Cidade(
        String nome) throws Exception
    {
        //Nome é definido, enquanto o restante dos atributos inicializados para 0

        codigo = 0;
        setNome(nome);
        x = 0.0f;
        y = 0.0f;
    }

    //Construtor de cópia de Cidade a partir de um modelo

    public Cidade(
        Cidade modelo) throws Exception
    {
        //Se o modelo estiver vazio, joga-se exceção
        if (modelo == null)
            throw new Exception("Cidade - construtor de cópia: modelo ausente");

        //Copia-se cada atributo
        codigo = modelo.codigo;
        nome = modelo.nome;
        x = modelo.x;
        y = modelo.y;
    }

    //Getters e setters para cada atributo, sendo os setters private

    public int getCodigo()
    {
        return codigo;
    }

    private void setCodigo(
        int codigo) throws Exception
    {
        if (codigo < 0)
            throw new Exception("Cidade - setCodigo: codigo negativo");
        this.codigo = codigo;
    }

    public float getX()
    {
        return x;
    }

    private void setX(
        float x) throws Exception
    {
        if (x < 0.0f || x > 1.0f)
            throw new Exception("Cidade - setX: valor inválido");
        this.x = x;
    }

    public float getY()
    {
        return y;
    }

    private void setY(
        float y) throws Exception
    {
        if (y < 0.0f || y > 1.0f)
            throw new Exception("Cidade - setY: valor inválido");
        this.y = y;
    }

    public String getNome()
    {
        return nome;
    }

    private void setNome(
        String nome) throws Exception
    {
        if (nome == null || nome.equals(""))
            throw new Exception("Cidade - setNome: string inválida");
        this.nome = nome;
    }

    //Compara duas cidades a partir do nome

    public int compareTo(
        Cidade c)
    {
        return nome.compareTo(c.nome);
    }

    //Cidade formatada para String

    public String toString()
    {
        return codigo + " - " + nome;
    }

    //String que representa uma cidade no arquivo texto

    public String paraArquivo()
    {
        String ret = Utilidades.padRight(codigo + "", TAMANHO_CODIGO);
        ret += Utilidades.padRight(nome, TAMANHO_NOME);
        ret += Utilidades.padRight(x + "", TAMANHO_X);
        ret += Utilidades.padRight(y + "", TAMANHO_Y);
        return ret;
    }

    //Verifica-se se duas cidades são iguais a partir dos nomes

    public boolean equals(
        Object obj)
    {
        //Se ponteiros são iguais, true
        if (this == obj)
            return true;

        //Se obj é vazio, false
        if (obj == null)
            return false;

        Cidade c = (Cidade)obj;

        //Retorna-se o equals do nome
        return nome.equals(c.nome);
    }

    //Retorna-se o código hash a partir da String nome

    public int hashCode()
    {
        return nome.hashCode();
    }

    //Método que retorna um clone da instância atual a partir do consturtor de cópia

    public Object clone()
    {
        Cidade c = null;
        try{ c = new Cidade(this); }
        catch(Exception e){}
        return c;
    }
}
