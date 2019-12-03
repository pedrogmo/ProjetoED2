package com.example.trenscidadesandroid.classes.grafo;

//Gustavo Henrique de Meira - 18015
//Pedro Gomes Moreira - 18174

import com.example.trenscidadesandroid.classes.aresta.Aresta;
import com.example.trenscidadesandroid.classes.caminho.Caminho;
import com.example.trenscidadesandroid.classes.cidade.Cidade;
import com.example.trenscidadesandroid.classes.pesocidades.PesoCidades;
import com.example.trenscidadesandroid.classes.pilha.Pilha;
import com.example.trenscidadesandroid.classes.vertice.Vertice;

import java.io.Serializable;

public class Grafo
    implements Serializable
{
    //Classe para armazenar dados para o Dijkstra
    private class DistOriginal
    {
        public int verticePai;
        public int peso;

        public DistOriginal(int vp, int p)
        {
            peso = p;
            verticePai = vp;
        }
    }

    //Os modos de busca das cidades no grafo
    public enum ModoBusca
    {
        PorMenorDistancia, PorMenorTempo
    }

    //Vetor de vértices do grafo
    private Vertice[] vertices;

    //Vetor de [pai + peso], cujos índices são correspondetes aos do vetor de vértices
    private DistOriginal[] percurso;

    //Matriz de pesocidades do grafo
    private PesoCidades[][] adjMatrix;

    //Quantidade de vértices efetivamente armazenados
    private int numVerts;

    private final int INFINITY = 1000000;

    //Atributo usado para indicar o vértice atualmente sendo visitado
    private int verticeAtual;

    //Atributo usado para ajustar menor caminho com Djikstra
    private int doInicioAteAtual;

    //O modo de busca atual do grafo
    private ModoBusca modoBusca;

    //Construtor que inicializa os vetores e a matriz a partir do total de vértices
    public Grafo(
        int totalVertices)
    {
        numVerts = 0;
        vertices = new Vertice[totalVertices];
        adjMatrix = new PesoCidades[totalVertices][totalVertices];
        percurso = new DistOriginal[totalVertices];
    }

    //Adiciona um vértice ao vetor e incrementa a quantidade
    public void novoVertice(
        Cidade informacao) throws Exception
    {
        vertices[numVerts] = new Vertice(informacao);
        numVerts++;
    }

    //Adiciona uma aresta à matriz
    public void novaAresta(
        Aresta aresta)
    {
        adjMatrix[aresta.getOrigem().getCodigo()][aresta.getDestino().getCodigo()] = aresta.getPesoCidades();
    }

    public void removerVertice(
        int indiceVertice)
    {
        if (indiceVertice != numVerts - 1)
        {
            for (int j = indiceVertice; j < numVerts - 1; j++)   // remove vértice do vetor
                vertices[j] = vertices[j + 1];

            // remove vértice da matriz
            for (int row = indiceVertice; row < numVerts; row++)
                moverLinhas(row, numVerts - 1);
            for (int col = indiceVertice; col < numVerts; col++)
                moverColunas(col, numVerts - 1);
        }
        numVerts--;
    }

    private void moverLinhas(
        int linha,
        int tamanho)
    {
        if (linha != numVerts - 1)
            for (int col = 0; col < tamanho; col++)
                adjMatrix[linha][col] = adjMatrix[linha + 1][col];  // desloca para excluir
    }

    private void moverColunas(
        int coluna,
        int tamanho)
    {
        if (coluna != numVerts - 1)
            for (int linha = 0; linha < tamanho; linha++)
                adjMatrix[linha][coluna] = adjMatrix[linha][coluna + 1]; // desloca para excluir
    }

    private int getPeso(
        PesoCidades pesoCidades)
    {
        int peso = INFINITY;
        if (pesoCidades != null)
        {
            if (modoBusca == ModoBusca.PorMenorDistancia)
                peso = pesoCidades.getDistancia();
            else if (modoBusca == ModoBusca.PorMenorTempo)
                peso = pesoCidades.getTempo();
        }
        return peso;
    }

    //Método que busca o melhor caminho da origem ao destino, priorizado  por distância ou tempo (ModoBusca)
    public Caminho getCaminho(
        Cidade origem,
        Cidade destino,
        ModoBusca mb) throws Exception
    {
        this.modoBusca = mb;

        int inicioDoPercurso = origem.getCodigo();
        int finalDoPercurso = destino.getCodigo();

        for (int j = 0; j < numVerts; j++)
            vertices[j].foiVisitado = false;

        vertices[inicioDoPercurso].foiVisitado = true;
        for (int j = 0; j < numVerts; j++)
        {
            // anotamos no vetor percurso a distância entre o inicioDoPercurso e cada vértice
            // se não há ligação direta, o valor da distância será infinity
            int peso = getPeso(adjMatrix[inicioDoPercurso][j]);
            percurso[j] = new DistOriginal(inicioDoPercurso, peso);
        }

        for (int vert = 0; vert < numVerts; vert++)
        {
            // Procuramos a saída não visitada do vértice inicioDoPercurso com o menor peso
            int indiceDoMenor = obterMenor();

            // o vértice com o menor peso passa a ser o vértice atual
            // para compararmos com o peso calculada em AjustarMenorCaminho()
            verticeAtual = indiceDoMenor;
            doInicioAteAtual = percurso[indiceDoMenor].peso;

            // visitamos o vértice com a menor distância desde o inicioDoPercurso
            vertices[verticeAtual].foiVisitado = true;
            ajustarMenorCaminho();
        }

        return getCaminho(inicioDoPercurso, finalDoPercurso);
    }

    private int obterMenor()
    {
        int pesoMinimo = INFINITY;
        int indiceDaMinima = 0;
        for (int j = 0; j < numVerts; j++)
            if (!(vertices[j].foiVisitado) && (percurso[j]).peso < pesoMinimo)
            {
                pesoMinimo = percurso[j].peso;
                indiceDaMinima = j;
            }
        return indiceDaMinima;
    }

    private void ajustarMenorCaminho()
    {
        for (int coluna = 0; coluna < numVerts; coluna++)

            // para cada vértice ainda não visitado
            if (!vertices[coluna].foiVisitado)
            {
                // acessamos o peso desde o vértice atual (pode ser infinity)
                int atualAteMargem = getPeso(adjMatrix[verticeAtual][coluna]);

                // calculamos o peso desde inicioDoPercurso passando por vertice atual até
                // esta saída
                int doInicioAteMargem = doInicioAteAtual + atualAteMargem;

                // quando encontra um peso menor, marca o vértice a partir do
                // qual chegamos no vértice de índice coluna, e a soma do peso
                // para nele chegar
                int pesoDoCaminho = percurso[coluna].peso;

                if (doInicioAteMargem < pesoDoCaminho)
                {
                    percurso[coluna].verticePai = verticeAtual;
                    percurso[coluna].peso = doInicioAteMargem;
                }
            }
    }

    //percorre do final do dijkstra até o topo
    private Caminho getCaminho(
        int inicioDoPercurso,
        int finalDoPercurso) throws Exception
    {
        Caminho ret = new Caminho();
        Pilha<Cidade> pilha = new Pilha<Cidade>();

        int onde = finalDoPercurso;

        int cont = 0;
        while (onde != inicioDoPercurso)
        {
            try
            {
                pilha.empilhar(vertices[onde].getInfo());
            }
            catch(Exception exc){}

            onde = percurso[onde].verticePai;
            ++cont;
        }


        //Se não foi possível achar caminho, retornamos o caminho vazio
        if ((cont == 1) && (percurso[finalDoPercurso].peso == INFINITY))
            return ret;

        Cidade anterior = vertices[inicioDoPercurso].getInfo();
        Cidade atual = null;

        while (!pilha.isVazia())
        {
            atual = pilha.desempilhar();
            if (anterior != null)
            {
                Aresta a = new Aresta(anterior, atual, adjMatrix[anterior.getCodigo()][atual.getCodigo()]);
                ret.adicionar(a);
            }
            anterior = atual;
        }

        return ret;
    }
}