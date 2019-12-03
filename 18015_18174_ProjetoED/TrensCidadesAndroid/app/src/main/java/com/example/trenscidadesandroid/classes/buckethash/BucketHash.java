//Gustavo Henrique de Meira - 18015
//Pedro Gomes Moreira - 18174

package com.example.trenscidadesandroid.classes.buckethash;

import com.example.trenscidadesandroid.classes.lista.Lista;

import java.io.Serializable;

public class BucketHash<T>
    implements Serializable
{
    //Constante indicando o tamanho do vetor para hash
    private static final int TAMANHO = 500;

    //Vetor de hash. É exigido pelo Java que seu tipo seja Object
    private Object[] conteudo;

    //Quantidade de elementos efetivamente armazenados
    private int qtd;

    //No construtor, instancia-se o vetor com ponteiros nulos a princípio
    public BucketHash()
    {
        conteudo = new Object[TAMANHO];
    }

    //método privado que chama calcula o valor hash de um objeto a partir de seu hashCode()
    private int hash(
        T item)
    {
        return Math.abs(item.hashCode() % TAMANHO);
    }

    public void inserir(
        T item) throws Exception
    {
        //Mapeamento do objeto a partir de seu hash
        int valorHash = hash(item);

        //Se a posição do vetor é nula, instancia-se uma lista
        if (conteudo[valorHash] == null)
            conteudo[valorHash] = new Lista<T>();

        Lista<T> lista = (Lista<T>) conteudo[valorHash];

        //Se o dado não existe na lista, ele é inserido ao fim
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

        //Se a lista daquela posição não for nula
        if (conteudo[valorHash] != null)
            //Se a lista conseguiu excluir aquele valor
            if (((Lista<T>) conteudo[valorHash]).excluir(item))
                //Decremento da quantidade
                --qtd;
    }

    public T buscar(
        T chave)
    {
        int valorHash = hash(chave);

        //Se a posição do hash estiver com lista vazia, retorna-se null
        if (conteudo[valorHash] == null)
            return null;

        //Retorna-se o resultado do método buscar(T) da lista, que pode ser null
        return ((Lista<T>) conteudo[valorHash]).buscar(chave);
    }

    //Método que retorna uma String com o vetor de hash interno
    public String conteudo()
    {
        String ret = "";

        //Percorre-se o vetor
        for (int i = 0; i < TAMANHO; i++)
            //Se o Bucket for diferente de null
            if (conteudo[i] != null)
                //Para cada chave armazenada na lista, adiciona-se seu toString()
                for (T chave : ((Lista<T>) conteudo[i]))
                    ret += i + ": {" + chave.toString() + "}" + "\n";

        return ret;
    }

    //Getter da quantidade de elementos
    public int getQuantidade()
    {
        return qtd;
    }
}
