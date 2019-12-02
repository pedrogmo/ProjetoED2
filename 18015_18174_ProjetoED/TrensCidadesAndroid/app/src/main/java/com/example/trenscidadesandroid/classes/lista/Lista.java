package com.example.trenscidadesandroid.classes.lista;

//Gustavo Henrique de Meira - 18015
//Pedro Gomes Moreira - 18174

import com.example.trenscidadesandroid.classes.no.No;

import java.io.Serializable;
import java.util.Iterator;

//Lista simples genérica
public class Lista<T>
    implements Serializable, Iterable<T>
{
    //Atributos ponteiros de nós
    protected No<T> atual, primeiro, anterior, ultimo;

    //Quantidade de elementos armazenados
    protected int qtosNos;

    //Construtor padrão inicializa ponteiros para null e quantidade para 0
    public Lista()
    {
        primeiro = atual = anterior = ultimo = null;
        qtosNos = 0;
    }

    //Retorna-se se a lista está vazia, ou seja, se o primeiro elemento é nulo
    public boolean isVazia()
    {
        return primeiro == null;
    }

    //Getter da quantidade
    public int getQuantidade()
    {
        return qtosNos;
    }

    //Retorna o dado armazenado na posição [indice], null caso não exista
    public T get(
        int indice)
    {
        int cont = 0;

        //Percorre-se cada nó de lista
        for (atual = primeiro;
             atual != null;
             atual = atual.prox)
        {
            //Se chegou na posição desejada, retorna info do nó
            if (cont == indice)
                return atual.getInfo();
            ++cont;
        }

        //Retorna-se null caso índice seja inválido
        return null;
    }

    //Getter do dado guardado no início da lista, null se está vazia

    public T getDadoInicio()
    {
        if (isVazia())
            return null;
        return primeiro.getInfo();
    }

    //Getter do dado guardado no fim da lista, null se está vazia

    public T getDadoFim()
    {
        if (isVazia())
            return null;
        return ultimo.getInfo();
    }

    //Setter da posição [indice] da lista

    public void inserir(
        int indice,
        T info) throws Exception
    {
        int cont = 0;

        //Construção de novo nó com a informação
        No<T> novoNo = new No<T>(info, null);

        //Percorre-se a lista dois a dois
        for (anterior = null, atual = primeiro;
             atual != null;
             anterior = atual, atual = atual.prox)
        {
            //Se chegou na posição, insere o nó entre anterior e atual
            if (cont == indice)
            {
                novoNo.prox = atual;
                anterior.prox = novoNo;
                ++qtosNos;
            }
            ++cont;
        }
    }

    public void inserirInicio(T d)
        throws Exception
    {
        //Construção de um nó cuja informação é 'd' e próximo é primeiro
        No<T> novoNo = new No<T>(d, primeiro);

        //Primeiro nó vira novo
        primeiro = novoNo;

        //Se estava vazia, ultimo vira primeiro
        if (ultimo == null)
            ultimo = novoNo;

        qtosNos++;
    }

    public void inserirFim(T d)
        throws Exception
    {
        //Novo nó com info 'd' e prox null
        No<T> novoNo = new No<T>(d, null);

        //Se vazia, primeiro vira novo
        if (isVazia())
            primeiro = novoNo;
        else
            //Se não, ultimo aponta para novo
            ultimo.prox = novoNo;

        ultimo = novoNo;
        qtosNos++;
    }

    //Método que inverte os nós da lista

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

    //Procura um dado, retorna se existe e atribui nós atual a anterior a ele

    public boolean existeDado(T d)
    {
        //Se dado é nulo ou lista está vaiza, false

        if (d == null)
            return false;

        if (isVazia())
            return false;

        //Percorre-se a lista de dois a dois
        for (atual = primeiro, anterior = null;
             atual != null;
             anterior = atual, atual = atual.prox)
        {
            //Se o dado é igual ao conteúdo do nó atual, true
            if (d.equals(atual.getInfo()))
                return true;
        }

        //False se não achar
        return false;
    }

    //Método que excluir um dado e retorna se excluiu ou não

    public boolean excluir(T d)
    {
        //Se dado é nulo, false
        if (d == null)
            return false;

        //Se existe o dado, remover-se-o-á e se retornará false
        if (existeDado(d))
        {
            removerNo(anterior, atual);
            return true;
        }

        //Retorna-se false caso não existe
        return false;
    }

    public boolean excluirInicio()
    {
        //Se está vazia, não se pode excluir
        if (isVazia())
            return false;

        //Primeiro passa ser seu próximo
        primeiro = primeiro.prox;
        if (primeiro == null)
            ultimo = null;
        --qtosNos;
        return true;
    }

    public boolean excluirFim()
    {
        //Se está vazia, não se pode excluir
        if (isVazia())
            return false;

        No<T> atual = primeiro;

        //Se não é um único nó da lista
        if (atual.prox != null)
        {
            //Percorre-se a lista com atual até que seja o penúltimo nó
            for(; atual.prox.prox != null;
                atual = atual.prox);
            ultimo = atual;
        }
        else
        {
            //Se só existe um nó, primeiro e ultimo viram null
            primeiro = null;
            ultimo = null;
        }
        --qtosNos;

        return true;
    }

    //Retorna um dado a partir de uma chave

    public T buscar(
        T dado)
    {
        //Se existe, retorna a informação do nó atual alterado
        if (existeDado(dado))
            return atual.getInfo();

        //Se não existe, retorna null
        return null;
    }

    protected void removerNo(
        No<T> ant,
        No<T> atu)
    {
        //Se anterior é null e atual não, é o primeiro nó
        if (ant == null && atu != null)
        {
            //Logo, primeiro vira seu próximo
            primeiro = atu.prox;
            if (primeiro == null)
                ultimo = null;
        }
        else
        {
            //Anterior passa a ter como próximo o próximo do atual
            //Dessa forma, pula-se o nó a ser removido
            ant.prox = atu.prox;
            if (atu == null)
                ultimo = ant;
        }
        qtosNos--;
    }

    public String toString()
    {
        String ret = "";

        //Percorre-se a lista adicionando o toString() de cada elemento à String
        for (atual = primeiro;
             atual != null;
             atual = atual.prox)
        {
            ret += atual.toString() + "\n";
        }
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

        //Construtor a partir de um nó
        public MeuIterador(No<T> no)
        {
            this.no = no;
        }

        //Retorna se o nó é nulo ou não
        public boolean hasNext()
        {
            return no != null;
        }

        //Vai para o próximo e retorna o nó anterior
        public T next()
        {
            T info = no.getInfo();
            no = no.prox;
            return info;
        }
    }
}