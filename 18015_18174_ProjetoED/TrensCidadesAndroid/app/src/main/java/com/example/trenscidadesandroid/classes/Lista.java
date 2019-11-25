package com.example.trenscidadesandroid.classes;

//Gustavo Henrique de Meira - 18015
//Pedro Gomes Moreira - 18174

import java.io.Serializable;
import java.util.Iterator;

public class Lista<T>
    implements Serializable, Iterable<T>
{
    private class No<T>
    {
        private T info;
        public No<T> prox;

        public No(
            T info,
            No<T> prox) throws Exception
        {
            setInfo(info);
            this.prox = prox;
        }

        public T getInfo()
        {
            return info;
        }

        public void setInfo(
            T info) throws Exception
        {
            if (info == null)
                throw new Exception("No<T> - setInfo: info nula");
            this.info = info;
        }
    }

    protected No<T> atual, primeiro, anterior, ultimo;
    protected int qtosNos;

    public Lista()
    {
        primeiro = atual = anterior = ultimo = null;
        qtosNos = 0;
    }

    public boolean isVazia()
    {
        return primeiro == null;
    }

    public int getQuantidade()
    {
        return qtosNos;
    }

    public T get(
        int indice)
    {
        int cont = 0;
        for (atual = primeiro; atual != null; atual = atual.prox) {
            if (cont == indice)
                return atual.getInfo();
            ++cont;
        }
        return null;
    }

    public T getDadoInicio()
    {
        return primeiro.getInfo();
    }

    public T getDadoFim()
    {
        return ultimo.getInfo();
    }

    public void alterar(
        int indice,
        T info) throws Exception
    {
        int cont = 0;
        for (atual = primeiro; atual != null; atual = atual.prox) {
            if (cont == indice)
                atual.setInfo(info);
            ++cont;
        }
    }

    public void inserirInicio(T d)
            throws Exception
    {
        inserirInicio(new No<T>(d, null));
    }

    public void inserirFim(T d)
            throws Exception
    {
        inserirFim(new No<T>(d, null));
    }

    protected void inserirInicio(
        No<T> novoNo)
    {
        novoNo.prox = primeiro;
        primeiro = novoNo;
        if (isVazia())
            ultimo = novoNo;
        qtosNos++;
    }

    protected void inserirFim(
        No<T> novoNo) 
    {
        novoNo.prox = null;
        if (isVazia())
            primeiro = novoNo;
        else
            ultimo.prox = novoNo;
        ultimo = novoNo;
        qtosNos++;
    }

    public void inverter() 
    {
        if (!isVazia())
        {
            No<T> um, dois = null, tres;
            um = primeiro;
            dois = um.prox;
            while (dois != null) 
            {
                tres = dois.prox;
                dois.prox = um;
                um = dois;
                dois = tres;
            }
            ultimo = primeiro;
            ultimo.prox = null;
            primeiro = um;
        }
    }

    public boolean existeDado(T d)
            throws Exception
    {
        if (d == null)
            throw new Exception("Dado nulo");

        if (isVazia())
            return false;

        for (atual = primeiro, anterior = null;
             atual != null;
             anterior = atual, atual = atual.prox)
        {
            if (d.equals(atual.getInfo()))
                return true;
        }

        return false;
    }

    protected void inserirMeio(
        No<T> novoNo)
    {
        novoNo.prox = atual;
        anterior.prox = novoNo;
        if (anterior == ultimo)
            ultimo = novoNo;
        qtosNos++;
    }

    public boolean excluir(T d)
        throws Exception
    {
        if (d == null)
            throw new Exception("Dado nulo");
        if (existeDado(d)) {
            removerNo(anterior, atual);
            return true;
        }
        return false;
    }

    public boolean excluirInicio()
    {
        if (isVazia())
            return false;
        primeiro = primeiro.prox;
        if (primeiro == null)
            ultimo = null;
        --qtosNos;
        return true;
    }

    public boolean excluirFim()
    {
        if (isVazia())
            return false;

        No<T> atual = primeiro;

        if (atual.prox != null)
        {
            for(; atual.prox.prox != null;
                atual = atual.prox);
            ultimo = atual;
        }
        else
        {
            primeiro = null;
            ultimo = null;
        }
        --qtosNos;

        return true;
    }

    public T buscar(
            T dado)
    {
        for (atual = primeiro; atual != null; atual = atual.prox)
            if (atual.getInfo().equals(dado))
                return atual.getInfo();
        return null;
    }

    protected void removerNo(
        No<T> ant,
        No<T> atu)
    {
        if (ant == null && atu != null)
        {
            primeiro = atu.prox;
            if (primeiro == null)
                ultimo = null;
        }
        else
        {
            ant.prox = atu.prox;
            if (atu == null)
                ultimo = ant;
        }
        qtosNos--;
    }

    public String toString()
    {
        String ret = "";
        for (atual = primeiro; atual != null; atual = atual.prox)
            ret += atual.toString() + "\n";
        return ret;
    }

    //Iterador para fazer foreach do Java

    public Iterator<T> iterator()
    {
        return new MeuIterador(primeiro);
    }

    class MeuIterador implements Iterator<T>
    {
        private No<T> no;

        public MeuIterador(No<T> no)
        {
            this.no = no;
        }

        public boolean hasNext()
        {
            return no.prox != null;
        }

        public T next()
        {
            no = no.prox;
            return no.getInfo();
        }

        public void remove()
        {
            throw new UnsupportedOperationException("NAO");
        }
    }
}