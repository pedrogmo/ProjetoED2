package com.example.trenscidadesandroid.classes.buckethash;

//Gustavo Henrique de Meira - 18015
//Pedro Gomes Moreira - 18174

import com.example.trenscidadesandroid.classes.lista.Lista;

import java.io.Serializable;

public class BucketHash<T>
    implements Serializable
{
    private final int TAMANHO = 500;
    private Object[] conteudo;
    private int qtd;

    public BucketHash()
    {
        conteudo = new Object[TAMANHO];
    }

    private int hash(
        T item)
    {
        return Math.abs(item.hashCode() % TAMANHO);
    }

    public void inserir(
        T item) throws Exception
    {
        int valorHash = hash(item);

        if (conteudo[valorHash] == null)
            conteudo[valorHash] = new Lista<T>();

        Lista<T> lista = (Lista<T>) conteudo[valorHash];

        if (!lista.existeDado(item))
        {
            lista.inserirFim(item);
            ++qtd;
        }
    }

    public void excluir(
        T item)
    {
        int valorHash = hash(item);

        if (conteudo[valorHash] != null)
            if (((Lista<T>) conteudo[valorHash]).excluir(item))
                --qtd;
    }

    public T buscar(
        T chave)
    {
        int valorHash = hash(chave);

        if (conteudo[valorHash] == null)
            return null;
        return ((Lista<T>) conteudo[valorHash]).buscar(chave);
    }

    public String conteudo()
    {
        String ret = "";
        for (int i = 0; i < TAMANHO; i++)
            if (conteudo[i] != null)
                for (T chave : ((Lista<T>) conteudo[i]))
                    ret += i + ": {" + chave.toString() + "}" + "\n";
        return ret;
    }

    public int getQuantidade()
    {
        return qtd;
    }
}
