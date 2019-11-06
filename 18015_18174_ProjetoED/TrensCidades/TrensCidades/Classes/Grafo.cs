using System;
using System.Collections.Generic;

namespace TrensCidades.Classes
{
    //Gustavo Henrique de Meira - 18015
    //Pedro Gomes Moreira - 18174

    class Grafo<T>
    {
        private Vertice<T>[] vertices;
        private int[,] adjMatrix;
        private int numVerts;

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

        DistOriginal[] percurso;
        int INFINITY = 1000000;
        int verticeAtual;           // global usada para indicar o vértice atualmente sendo visitado 
        int doInicioAteAtual;       // global usada para ajustar menor caminho com Djikstra

        public Grafo(
            int totalVertices)
        {
            numVerts = 0;
            vertices = new Vertice<T>[totalVertices];
            adjMatrix = new int[totalVertices, totalVertices];

            //Põe um valor muito grande nas posições da matriz
            for (int j = 0; j < totalVertices; j++)
                for (int k = 0; k < totalVertices; k++)
                    adjMatrix[j, k] = INFINITY;

            percurso = new DistOriginal[totalVertices];
        }

        public void NovoVertice(
            T informacao)
        {
            vertices[numVerts] = new Vertice<T>(informacao);
            numVerts++;
        }

        public void NovaAresta(
            int origem,
            int destino)
        {
            adjMatrix[origem, destino] = 1;
        }

        public void NovaAresta(
            int origem,
            int destino,
            int peso)
        {
            adjMatrix[origem, destino] = peso;
        }

        public int SemSucessores()  // encontra e retorna a linha de um vértice sem sucessores
        {
            bool temAresta;
            for (int linha = 0; linha < numVerts; linha++)
            {
                temAresta = false;
                for (int col = 0; col < numVerts; col++)
                    if (adjMatrix[linha, col] != INFINITY)
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
                    adjMatrix[linha, col] = adjMatrix[linha + 1, col];  // desloca para excluir
        }

        private void MoverColunas(
            int coluna,
            int tamanho)
        {
            if (coluna != numVerts - 1)
                for (int linha = 0; linha < tamanho; linha++)
                    adjMatrix[linha, coluna] = adjMatrix[linha, coluna + 1]; // desloca para excluir
        }

        public string OrdenacaoTopologica()
        {
            Pilha<T> gPilha = new Pilha<T>(); // para guardar a sequência de vértices
            int origVerts = numVerts;
            while (numVerts > 0)
            {
                int currVertex = SemSucessores();
                if (currVertex == -1)
                    return "Erro: grafo possui ciclos.";
                gPilha.Empilhar(vertices[currVertex].Info);   // empilha vértice
                RemoverVertice(currVertex);
            }
            string resultado = "Sequência da Ordenação Topológica: ";
            while (!gPilha.EstaVazia)
                resultado += gPilha.Desempilhar() + " ";    // desempilha para exibir
            return resultado;
        }

        private int ObterVerticeAdjacenteNaoVisitado(
            int vertice)
        {
            for (int j = 0; j <= numVerts - 1; j++)
                if ((adjMatrix[vertice, j] != INFINITY) && (!vertices[j].FoiVisitado))
                    return j;
            return -1;
        }

        public void PercursoEmProfundidade()
        {
            Pilha<int> gPilha = new Pilha<int>(); // para guardar a sequência de vértices
            vertices[0].FoiVisitado = true;
            gPilha.Empilhar(0);
            int v;
            while (!gPilha.EstaVazia)
            {
                v = ObterVerticeAdjacenteNaoVisitado(gPilha.Topo());
                if (v == -1)
                    gPilha.Desempilhar();
                else
                {
                    vertices[v].FoiVisitado = true;
                    gPilha.Empilhar(v);
                }
            }
            for (int j = 0; j <= numVerts - 1; j++)
                vertices[j].FoiVisitado = false;
        }

        public void PercursoEmProfundidadeRec(
            int part)
        {
            int i;
            vertices[part].FoiVisitado = true;
            for (i = 0; i < numVerts; ++i)
                if (adjMatrix[part, i] != INFINITY && !vertices[i].FoiVisitado)
                    PercursoEmProfundidadeRec(i);
        }

        public void PercursoPorLargura()
        {
            Queue<int> gQueue = new Queue<int>();
            vertices[0].FoiVisitado = true;
            gQueue.Enqueue(0);
            int vert1, vert2;
            while (gQueue.Count > 0)
            {
                vert1 = gQueue.Dequeue();
                vert2 = ObterVerticeAdjacenteNaoVisitado(vert1);
                while (vert2 != -1)
                {
                    vertices[vert2].FoiVisitado = true;
                    gQueue.Enqueue(vert2);
                    vert2 = ObterVerticeAdjacenteNaoVisitado(vert1);
                }
            }
            for (int i = 0; i < numVerts; i++)
                vertices[i].FoiVisitado = false;
        }

        public void ArvoreGeradoraMinima(
            int primeiro)
        {
            Pilha<int> gPilha = new Pilha<int>(); // para guardar a sequência de vértices
            vertices[primeiro].FoiVisitado = true;
            gPilha.Empilhar(primeiro);
            int currVertex, ver;
            while (!gPilha.EstaVazia)
            {
                currVertex = gPilha.Topo();
                ver = ObterVerticeAdjacenteNaoVisitado(currVertex);
                if (ver == -1)
                    gPilha.Desempilhar();
                else
                {
                    vertices[ver].FoiVisitado = true;
                    gPilha.Empilhar(ver);
                }
            }
            for (int j = 0; j <= numVerts - 1; j++)
                vertices[j].FoiVisitado = false;
        }

        public string Caminho(
            int inicioDoPercurso,
            int finalDoPercurso)
        {
            for (int j = 0; j < numVerts; j++)
                vertices[j].FoiVisitado = false;

            vertices[inicioDoPercurso].FoiVisitado = true;
            for (int j = 0; j < numVerts; j++)
            {
                // anotamos no vetor percurso a distância entre o inicioDoPercurso e cada vértice
                // se não há ligação direta, o valor da distância será infinity
                int tempDist = adjMatrix[inicioDoPercurso, j];
                percurso[j] = new DistOriginal(inicioDoPercurso, tempDist);
            }

            for (int nTree = 0; nTree < numVerts; nTree++)
            {
                // Procuramos a saída não visitada do vértice inicioDoPercurso com a menor distância
                int indiceDoMenor = ObterMenor();

                // e anotamos essa menor distância
                int distanciaMinima = percurso[indiceDoMenor].distancia;


                // o vértice com a menor distância passa a ser o vértice atual
                // para compararmos com a distância calculada em AjustarMenorCaminho()
                verticeAtual = indiceDoMenor;
                doInicioAteAtual = percurso[indiceDoMenor].distancia;

                // visitamos o vértice com a menor distância desde o inicioDoPercurso
                vertices[verticeAtual].FoiVisitado = true;
                AjustarMenorCaminho();
            }

            return ExibirPercursos(inicioDoPercurso, finalDoPercurso);
        }

        public int ObterMenor()
        {
            int distanciaMinima = INFINITY;
            int indiceDaMinima = 0;
            for (int j = 0; j < numVerts; j++)
                if (!(vertices[j].FoiVisitado) && (percurso[j].distancia < distanciaMinima))
                {
                    distanciaMinima = percurso[j].distancia;
                    indiceDaMinima = j;
                }
            return indiceDaMinima;
        }

        public void AjustarMenorCaminho()
        {
            for (int coluna = 0; coluna < numVerts; coluna++)
                if (!vertices[coluna].FoiVisitado)       // para cada vértice ainda não visitado
                {
                    // acessamos a distância desde o vértice atual (pode ser infinity)
                    int atualAteMargem = adjMatrix[verticeAtual, coluna];

                    // calculamos a distância desde inicioDoPercurso passando por vertice atual até
                    // esta saída
                    int doInicioAteMargem = doInicioAteAtual + atualAteMargem;

                    // quando encontra uma distância menor, marca o vértice a partir do
                    // qual chegamos no vértice de índice coluna, e a soma da distância
                    // percorrida para nele chegar
                    int distanciaDoCaminho = percurso[coluna].distancia;
                    if (doInicioAteMargem < distanciaDoCaminho)
                    {
                        percurso[coluna].verticePai = verticeAtual;
                        percurso[coluna].distancia = doInicioAteMargem;
                    }
                }
        }

        public string ExibirPercursos(
            int inicioDoPercurso,
            int finalDoPercurso)
        {
            string linha = "", resultado = "";
            for (int j = 0; j < numVerts; j++)
            {
                linha += vertices[j].Info.ToString() + "=";
                if (percurso[j].distancia == INFINITY)
                    linha += "inf";
                else
                    linha += percurso[j].distancia;
                string pai = vertices[percurso[j].verticePai].Info.ToString();
                linha += "(" + pai + ") ";
            }

            int onde = finalDoPercurso;
            Pilha<T> pilha = new Pilha<T>();

            int cont = 0;
            while (onde != inicioDoPercurso)
            {
                onde = percurso[onde].verticePai;
                pilha.Empilhar(vertices[onde].Info);
                cont++;
            }

            while (!pilha.EstaVazia)
            {
                resultado += pilha.Desempilhar();
                if (!pilha.EstaVazia)
                    resultado += " --> ";
            }

            if ((cont == 1) && (percurso[finalDoPercurso].distancia == INFINITY))
                resultado = "Não há caminho";
            else
                resultado += " --> " + vertices[finalDoPercurso].Info.ToString();
            return resultado;
        }
    }

}