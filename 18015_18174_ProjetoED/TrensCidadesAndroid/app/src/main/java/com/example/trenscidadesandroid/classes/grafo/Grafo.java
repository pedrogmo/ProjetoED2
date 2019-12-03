//Gustavo Henrique de Meira - 18015
//Pedro Gomes Moreira - 18174

package com.example.trenscidadesandroid.classes.grafo;

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

    //Adiciona peso à matriz, na posição certa conforme as cidades da Aresta recebida
    public void novaAresta(
        Aresta aresta)
    {
        adjMatrix[aresta.getOrigem().getCodigo()][aresta.getDestino().getCodigo()] = aresta.getPesoCidades();
    }

    public void removerVertice(
        int indiceVertice)
    {
        //Se o índice está dentro do intervalo válido
        if (indiceVertice >= 0 && indiceVertice < numVerts)
        {
            if (indiceVertice != numVerts - 1)
            {
                //Mover o vetor uma posição para trás a partir do índice removido
                for (int j = indiceVertice; j < numVerts - 1; j++)
                    vertices[j] = vertices[j + 1];

                //Remover vértice da matriz, removendo sua linha e sua coluna
                for (int row = indiceVertice; row < numVerts; row++)
                    moverLinhas(row, numVerts - 1);
                for (int col = indiceVertice; col < numVerts; col++)
                    moverColunas(col, numVerts - 1);
            }
            numVerts--;
        }
    }

    private void moverLinhas(
        int linha,
        int tamanho)
    {
        if (linha != numVerts - 1)
            for (int col = 0; col < tamanho; col++)
                //Desloca para excluir
                adjMatrix[linha][col] = adjMatrix[linha + 1][col];
    }

    private void moverColunas(
        int coluna,
        int tamanho)
    {
        if (coluna != numVerts - 1)
            for (int linha = 0; linha < tamanho; linha++)
                //Desloca para excluir
                adjMatrix[linha][coluna] = adjMatrix[linha][coluna + 1];
    }

    //Método privativo que retorna o peso de uma posição da matriz passada
    private int getPeso(
        PesoCidades pesoCidades)
    {
        //Começa inicialmente com INFINITY, que representa uma aresta inexistente
        int peso = INFINITY;

        if (pesoCidades != null)
        {
            //A partir do modoBusca atual, retornar distância ou tempo do PesoCidades
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
        //Definição do modoBusca atual
        this.modoBusca = mb;

        //Índices das duas cidades
        int inicioDoPercurso = origem.getCodigo();
        int finalDoPercurso = destino.getCodigo();

        //Resetar os vértices para não visitados
        for (int j = 0; j < numVerts; j++)
            vertices[j].foiVisitado = false;

        //Primeiro vértice do percurso marcado como visitado
        vertices[inicioDoPercurso].foiVisitado = true;

        for (int j = 0; j < numVerts; j++)
        {
            //Anotar no vetor percurso a distância entre o inicioDoPercurso e cada vértice
            //Se não há ligação direta, o valor da distância será infinity
            int peso = getPeso(adjMatrix[inicioDoPercurso][j]);
            percurso[j] = new DistOriginal(inicioDoPercurso, peso);
        }

        for (int vert = 0; vert < numVerts; vert++)
        {
            //Procuramos a saída não visitada do vértice inicioDoPercurso com o menor peso
            int indiceDoMenor = obterMenor();

            //O vértice com o menor peso passa a ser o vértice atual
            //para compararmos com o peso calculada em ajustarMenorCaminho()
            verticeAtual = indiceDoMenor;
            doInicioAteAtual = percurso[indiceDoMenor].peso;

            //Visitar o vértice com a menor distância desde o inicioDoPercurso
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

    //Percorre do final do dijkstra até o topo e retorna o Caminho
    private Caminho getCaminho(
        int inicioDoPercurso,
        int finalDoPercurso) throws Exception
    {
        //Objeto de retorno
        Caminho caminhoFinal = new Caminho();

        //Pilha para armazenar as cidades para desempilhá-las posteriormente
        Pilha<Cidade> pilha = new Pilha<Cidade>();

        //A partir do final do percurso, empilha-se as cidades que levam a ela,
        //incrementando um contador

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


        //Se não foi possível achar caminho, retorna-se o caminho vazio
        if ((cont == 1) && (percurso[finalDoPercurso].peso == INFINITY))
            return caminhoFinal;

        //Desempilham-se as cidades em duas cidades auxiliares para fazer as ligações

        Cidade anterior = vertices[inicioDoPercurso].getInfo();
        Cidade atual = null;

        while (!pilha.isVazia())
        {
            //Atual recebe o topo da pilha
            atual = pilha.desempilhar();

            //Faz-se a ligação entre a cidade anterior e atual, adiconando a aresta ao caminho final
            Aresta a = new Aresta(anterior, atual, adjMatrix[anterior.getCodigo()][atual.getCodigo()]);
            caminhoFinal.adicionar(a);

            //Anterior passa a ser a cidade atual
            anterior = atual;
        }

        //Retorna-se o caminho final montado
        return caminhoFinal;
    }
}