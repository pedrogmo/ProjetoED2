package com.example.trenscidadesandroid.classes.pilha;

//Gustavo Henrique de Meira - 18015
//Pedro Gomes Moreira - 18174

import com.example.trenscidadesandroid.classes.lista.Lista;

import java.io.Serializable;

//Classe com conceito de Pilha modelada sobre uma Lista
public class Pilha<T>
    implements Serializable
{
    private Lista<T> lista;

    public Pilha()
    {
        lista = new Lista<T>();
    }

    //Método que retorna isVazia() da lista interna
    public boolean isVazia()
    {
        return lista.isVazia();
    }

    //Método que chama o inserirInicio da lista interna, inserindo a informação passada por parâmetro
    public void empilhar(
        T informacao) throws Exception
    {
        lista.inserirInicio(informacao);
    }

    //Método que retorna e remove o primeiro elemento
    public T desempilhar()
    {
        T dado = lista.getDadoInicio();
        lista.excluirInicio();
        return dado;
    }

    //retorna o elemento no topo lógico da pilha, ou seja, o início da lista
    public T topo()
    {
        return lista.getDadoInicio();
    }
}
