package com.example.trenscidadesandroid.classes.aresta;

public class Aresta<T>
{
    private int origem;
    private int destino;
    private T peso;

    public Aresta(
        int origem,
        int destino,
        T peso) throws Exception
    {
        this.setOrigem(origem);
        this.setDestino(destino);
        this.setPeso(peso);
    }

    public int getOrigem()
    {
        return origem;
    }

    private void setOrigem(
        int origem) throws Exception
    {
        if (origem < 0)
            throw new Exception("Aresta - setOrigem: valor negativo");
        this.origem = origem;
    }

    public int getDestino()
    {
        return destino;
    }

    private void setDestino(
            int destino) throws Exception
    {
        if (destino < 0)
            throw new Exception("Aresta - setDestino: valor negativo");
        this.destino = destino;
    }

    public T getPeso()
    {
        return peso;
    }

    private void setPeso(
        T peso) throws Exception
    {
        if (peso == null)
            throw new Exception("Aresta - setPeso: valor nulo");
        this.peso = peso;
    }
}
