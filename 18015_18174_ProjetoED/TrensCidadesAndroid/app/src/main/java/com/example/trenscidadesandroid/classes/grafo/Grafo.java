package com.example.trenscidadesandroid.classes.grafo;

//Gustavo Henrique de Meira - 18015
//Pedro Gomes Moreira - 18174

import com.example.trenscidadesandroid.classes.aresta.Aresta;
import com.example.trenscidadesandroid.classes.cidade.Cidade;
import com.example.trenscidadesandroid.classes.pilha.Pilha;
import com.example.trenscidadesandroid.classes.vertice.Vertice;

import java.io.Serializable;

public class Grafo
    implements Serializable
{
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

    public enum ModoBusca
    {
        PorMenorDistancia, PorMenorTempo
    }

    private Vertice[] vertices;
    private DistOriginal[] percurso;
    private Aresta[][] adjMatrix;
    private int numVerts;

    private final int INFINITY = 1000000;
    private int verticeAtual;           // global usada para indicar o vértice atualmente sendo visitado
    private int doInicioAteAtual;       // global usada para ajustar menor caminho com Djikstra
    private ModoBusca modoBusca;

    public Grafo(
        int totalVertices)
    {
        numVerts = 0;
        vertices = new Vertice[totalVertices];
        adjMatrix = new Aresta[totalVertices][totalVertices];

        //Põe null em cada posição da matriz
        for (int j = 0; j < totalVertices; j++)
            for (int k = 0; k < totalVertices; k++)
                adjMatrix[j][k] = null;

        percurso = new DistOriginal[totalVertices];
    }

    public void NovoVertice(
        Cidade informacao) throws Exception
    {
        vertices[numVerts] = new Vertice(informacao);
        numVerts++;
    }

    public void NovaAresta(
        Aresta a)
    {
        adjMatrix[a.getOrigem().getCodigo()][a.getDestino().getCodigo()] = a;
    }

    public int SemSucessores()  // encontra e retorna a linha de um vértice sem sucessores
    {
        boolean temAresta;
        for (int linha = 0; linha < numVerts; linha++)
        {
            temAresta = false;
            for (int col = 0; col < numVerts; col++)
                if (adjMatrix[linha][col] != null)
            {
                temAresta = true;
                break;
            }
            if (!temAresta)
                return linha;
        }
        return -1;
    }

    public void RemoverVertice(int vert)
    {
        if (vert != numVerts - 1)
        {
            for (int j = vert; j < numVerts - 1; j++)   // remove vértice do vetor
                vertices[j] = vertices[j + 1];

            // remove vértice da matriz
            for (int row = vert; row < numVerts; row++)
                MoverLinhas(row, numVerts - 1);
            for (int col = vert; col < numVerts; col++)
                MoverColunas(col, numVerts - 1);
        }
        numVerts--;
    }
    private void MoverLinhas(
        int linha,
        int tamanho)
    {
        if (linha != numVerts - 1)
            for (int col = 0; col < tamanho; col++)
                adjMatrix[linha][col] = adjMatrix[linha + 1][col];  // desloca para excluir
    }

    private void MoverColunas(
        int coluna,
        int tamanho)
    {
        if (coluna != numVerts - 1)
            for (int linha = 0; linha < tamanho; linha++)
                adjMatrix[linha][coluna] = adjMatrix[linha][coluna + 1]; // desloca para excluir
    }

    private int getPeso(Aresta a)
    {
        int peso = INFINITY;
        if (a != null)
        {
            if (modoBusca == ModoBusca.PorMenorDistancia)
                peso = a.getDistancia();
            else if (modoBusca == ModoBusca.PorMenorTempo)
                peso = a.getTempo();
        }
        return peso;
    }

    public String Caminho(
            Cidade origem,
            Cidade destino,
            ModoBusca mb)
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

            int tempDist = getPeso(adjMatrix[inicioDoPercurso][j]);
            percurso[j] = new DistOriginal(inicioDoPercurso, tempDist);
        }

        for (int nTree = 0; nTree < numVerts; nTree++)
        {
            // Procuramos a saída não visitada do vértice inicioDoPercurso com o menor peso
            int indiceDoMenor = ObterMenor();

            // e anotamos esse menor peso
            int pesoMinimo = percurso[indiceDoMenor].peso;

            // o vértice com o menor peso passa a ser o vértice atual
            // para compararmos com o peso calculada em AjustarMenorCaminho()

            verticeAtual = indiceDoMenor;
            doInicioAteAtual = percurso[indiceDoMenor].peso;

            // visitamos o vértice com a menor distância desde o inicioDoPercurso
            vertices[verticeAtual].foiVisitado = true;
            AjustarMenorCaminho();
        }

        return ExibirPercursos(origem, destino);
    }

    public int ObterMenor()
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

    public void AjustarMenorCaminho()
    {
        for (int coluna = 0; coluna < numVerts; coluna++)

            // para cada vértice ainda não visitado
            if (!vertices[coluna].foiVisitado)
            {
                // acessamos o peso desde o vértice atual (pode ser infinity)
                int atualAteMargem = getPeso(adjMatrix[verticeAtual][coluna]);

                // calculamos a distância desde inicioDoPercurso passando por vertice atual até
                // esta saída
                int doInicioAteMargem = doInicioAteAtual + atualAteMargem;

                // quando encontra uma distância menor, marca o vértice a partir do
                // qual chegamos no vértice de índice coluna, e a soma da distância
                // percorrida para nele chegar
                int distanciaDoCaminho = ((DistOriginal)percurso[coluna]).peso;
                if (doInicioAteMargem < distanciaDoCaminho)
                {
                    percurso[coluna].verticePai = verticeAtual;
                    percurso[coluna].peso = doInicioAteMargem;
                }
            }
    }

    public String ExibirPercursos(
            Cidade origem,
            Cidade destino)
    {
        int inicioDoPercurso = origem.getCodigo();
        int finalDoPercurso = destino.getCodigo();
        String linha = "",  resultado = "";
        for (int j = 0; j < numVerts; j++)
        {
            linha += vertices[j].getInfo().toString() + "=";
            if (percurso[j].peso == INFINITY)
                linha += "inf";
            else
                linha += percurso[j].peso;
            String pai = (vertices[percurso[j].verticePai]).getInfo().toString();
            linha += "(" + pai + ") ";
        }

        int onde = finalDoPercurso;
        Pilha<Cidade> pilha = new Pilha<Cidade>();

        int cont = 0;
        while (onde != inicioDoPercurso)
        {
            onde = percurso[onde].verticePai;

            try
            {
                pilha.empilhar(vertices[onde].getInfo());
            }
            catch(Exception exc){}

            cont++;
        }

        while (!pilha.isVazia())
        {
            resultado += pilha.desempilhar();
            if (!pilha.isVazia())
                resultado += " --> ";
        }

        if ((cont == 1) && (percurso[finalDoPercurso].peso == INFINITY))
            resultado = "Não há caminho";
        else
            resultado += " --> " + vertices[finalDoPercurso].getInfo().toString();
        return resultado;
    }
}