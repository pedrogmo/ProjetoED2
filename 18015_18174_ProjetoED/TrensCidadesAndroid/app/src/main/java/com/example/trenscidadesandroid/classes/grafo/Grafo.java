package com.example.trenscidadesandroid.classes.grafo;

//Gustavo Henrique de Meira - 18015
//Pedro Gomes Moreira - 18174

import com.example.trenscidadesandroid.classes.aresta.Aresta;
import com.example.trenscidadesandroid.classes.cidade.Cidade;
import com.example.trenscidadesandroid.classes.pilha.Pilha;
import com.example.trenscidadesandroid.classes.vertice.Vertice;
import com.example.trenscidadesandroid.classes.fila.Fila;

import java.io.Serializable;

public class Grafo
    implements Serializable
{
    private class DistOriginal
    {
        public int distancia;
        public int verticePai;

        public DistOriginal(int vp, int d)
        {
            distancia = d;
            verticePai = vp;
        }
    }

    private Vertice[] vertices;
    private DistOriginal[] percurso;
    private int[][] adjMatrix;
    private int numVerts;

    private final int INFINITY = 1000000;
    private int verticeAtual;           // global usada para indicar o vértice atualmente sendo visitado
    private int doInicioAteAtual;       // global usada para ajustar menor caminho com Djikstra

    public Grafo(
        int totalVertices)
    {
        numVerts = 0;
        vertices = new Vertice[totalVertices];
        adjMatrix = new int[totalVertices][totalVertices];

        //Põe um valor muito grande nas posições da matriz
        for (int j = 0; j < totalVertices; j++)
            for (int k = 0; k < totalVertices; k++)
                adjMatrix[j][k] = INFINITY;

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
        adjMatrix[a.getOrigem().getCodigo()][a.getDestino().getCodigo()] = 1;
    }

    /*public void NovaAresta(
        int origem,
        int destino,
        int peso)
    {
        adjMatrix[origem][destino] = peso;
    }*/

    public int SemSucessores()  // encontra e retorna a linha de um vértice sem sucessores
    {
        boolean temAresta;
        for (int linha = 0; linha < numVerts; linha++)
        {
            temAresta = false;
            for (int col = 0; col < numVerts; col++)
                if (adjMatrix[linha][col] != INFINITY)
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

    public String OrdenacaoTopologica()
    {
        Pilha<Cidade> gPilha = new Pilha<Cidade>(); // para guardar a sequência de vértices
        while (numVerts > 0)
        {
            int currVertex = SemSucessores();
            if (currVertex == -1)
                return "Erro: grafo possui ciclos.";
            try
            {
                gPilha.empilhar(vertices[currVertex].getInfo());   // empilha vértice
            }
            catch(Exception exc){}
            RemoverVertice(currVertex);
        }
        String resultado = "Sequência da Ordenação Topológica: ";
        while (!gPilha.isVazia())
            resultado += gPilha.desempilhar() + " ";    // desempilha para exibir
        return resultado;
    }

    private int ObterVerticeAdjacenteNaoVisitado(
            int vertice)
    {
        for (int j = 0; j <= numVerts - 1; j++)
            if ((adjMatrix[vertice][j] != INFINITY) && (!vertices[j].foiVisitado))
                    return j;
        return -1;
    }

    public void PercursoEmProfundidade()
    {
        Pilha<Integer> gPilha = new Pilha<Integer>(); // para guardar a sequência de vértices
        vertices[0].foiVisitado = true;
        try
        {
            gPilha.empilhar(0);
        }
        catch(Exception exc){}
        int v;
        while (!gPilha.isVazia())
        {
            v = ObterVerticeAdjacenteNaoVisitado(gPilha.topo());
            if (v == -1)
                gPilha.desempilhar();
            else
            {
                (vertices[v]).foiVisitado = true;
                try
                {
                    gPilha.empilhar(v);
                }
                catch(Exception exc){}
            }
        }
        for (int j = 0; j <= numVerts - 1; j++)
            (vertices[j]).foiVisitado = false;
    }

    public void PercursoEmProfundidadeRec(
        int part)
    {
        int i;
        vertices[part].foiVisitado = true;
        for (i = 0; i < numVerts; ++i)
            if (adjMatrix[part][i] != INFINITY && !vertices[i].foiVisitado)
        PercursoEmProfundidadeRec(i);
    }

    public void PercursoPorLargura()
    {
        Fila<Integer> gQueue = new Fila<Integer>();
        vertices[0].foiVisitado = true;
        try
        {
            gQueue.enfileirar(0);
        }
        catch(Exception exc){}
        int vert1, vert2;
        while (!gQueue.isVazia())
        {
            vert1 = gQueue.desenfileirar();
            vert2 = ObterVerticeAdjacenteNaoVisitado(vert1);
            while (vert2 != -1)
            {
                vertices[vert2].foiVisitado = true;

                try
                {
                    gQueue.enfileirar(vert2);
                }
                catch(Exception exc){}

                vert2 = ObterVerticeAdjacenteNaoVisitado(vert1);
            }
        }
        for (int i = 0; i < numVerts; i++)
            vertices[i].foiVisitado = false;
    }

    public void ArvoreGeradoraMinima(
            int primeiro)
    {
        Pilha<Integer> gPilha = new Pilha<Integer>(); // para guardar a sequência de vértices
        vertices[primeiro].foiVisitado = true;
        try
        {
            gPilha.empilhar(primeiro);
        }
        catch(Exception exc){}
        int currVertex, ver;
        while (!gPilha.isVazia())
        {
            currVertex = gPilha.topo();
            ver = ObterVerticeAdjacenteNaoVisitado(currVertex);
            if (ver == -1)
                gPilha.desempilhar();
            else
            {
                vertices[ver].foiVisitado = true;
                try
                {
                    gPilha.empilhar(ver);
                }
                catch(Exception exc){}
            }
        }
        for (int j = 0; j <= numVerts - 1; j++)
            vertices[j].foiVisitado = false;
    }

    public String Caminho(
            int inicioDoPercurso,
            int finalDoPercurso)
    {
        for (int j = 0; j < numVerts; j++)
            vertices[j].foiVisitado = false;

        vertices[inicioDoPercurso].foiVisitado = true;
        for (int j = 0; j < numVerts; j++)
        {
            // anotamos no vetor percurso a distância entre o inicioDoPercurso e cada vértice
            // se não há ligação direta, o valor da distância será infinity
            int tempDist = adjMatrix[inicioDoPercurso][j];
            percurso[j] = new DistOriginal(inicioDoPercurso, tempDist);
        }

        for (int nTree = 0; nTree < numVerts; nTree++)
        {
            // Procuramos a saída não visitada do vértice inicioDoPercurso com a menor distância
            int indiceDoMenor = ObterMenor();

            // e anotamos essa menor distância
            int distanciaMinima = ((DistOriginal)percurso[indiceDoMenor]).distancia;


            // o vértice com a menor distância passa a ser o vértice atual
            // para compararmos com a distância calculada em AjustarMenorCaminho()
            verticeAtual = indiceDoMenor;
            doInicioAteAtual = percurso[indiceDoMenor].distancia;

            // visitamos o vértice com a menor distância desde o inicioDoPercurso
            vertices[verticeAtual].foiVisitado = true;
            AjustarMenorCaminho();
        }

        return ExibirPercursos(inicioDoPercurso, finalDoPercurso);
    }

    public int ObterMenor()
    {
        int distanciaMinima = INFINITY;
        int indiceDaMinima = 0;
        for (int j = 0; j < numVerts; j++)
            if (!(vertices[j].foiVisitado) && (percurso[j]).distancia < distanciaMinima)
            {
                distanciaMinima =((DistOriginal)percurso[j]).distancia;
                indiceDaMinima = j;
            }
        return indiceDaMinima;
    }

    public void AjustarMenorCaminho()
    {
        for (int coluna = 0; coluna < numVerts; coluna++)
            if (!vertices[coluna].foiVisitado)       // para cada vértice ainda não visitado
            {
                // acessamos a distância desde o vértice atual (pode ser infinity)
                int atualAteMargem = adjMatrix[verticeAtual][coluna];

                // calculamos a distância desde inicioDoPercurso passando por vertice atual até
                // esta saída
                int doInicioAteMargem = doInicioAteAtual + atualAteMargem;

                // quando encontra uma distância menor, marca o vértice a partir do
                // qual chegamos no vértice de índice coluna, e a soma da distância
                // percorrida para nele chegar
                int distanciaDoCaminho = ((DistOriginal)percurso[coluna]).distancia;
                if (doInicioAteMargem < distanciaDoCaminho)
                {
                    percurso[coluna].verticePai = verticeAtual;
                    percurso[coluna].distancia = doInicioAteMargem;
                }
            }
    }

    public String ExibirPercursos(
            int inicioDoPercurso,
            int finalDoPercurso)
    {
        String linha = "",  resultado = "";
        for (int j = 0; j < numVerts; j++)
        {
            linha += vertices[j].getInfo().toString() + "=";
            if (percurso[j].distancia == INFINITY)
                linha += "inf";
            else
                linha += percurso[j].distancia;
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

        if ((cont == 1) && (percurso[finalDoPercurso].distancia == INFINITY))
            resultado = "Não há caminho";
        else
            resultado += " --> " + vertices[finalDoPercurso].getInfo().toString();
        return resultado;
    }
}