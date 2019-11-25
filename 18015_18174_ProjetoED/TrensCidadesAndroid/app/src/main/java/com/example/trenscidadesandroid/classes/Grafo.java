package com.example.trenscidadesandroid.classes;

//Gustavo Henrique de Meira - 18015
//Pedro Gomes Moreira - 18174

public class Grafo<T>
{
    private Object[] vertices;
    private int[][] adjMatrix;
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

    Object[] percurso;

    int INFINITY = 1000000;
    int verticeAtual;           // global usada para indicar o vértice atualmente sendo visitado
    int doInicioAteAtual;       // global usada para ajustar menor caminho com Djikstra

    public Grafo(
        int totalVertices)
    {
        numVerts = 0;
        vertices = new Object[totalVertices];
        adjMatrix = new int[totalVertices][totalVertices];

        //Põe um valor muito grande nas posições da matriz
        for (int j = 0; j < totalVertices; j++)
            for (int k = 0; k < totalVertices; k++)
                adjMatrix[j][k] = INFINITY;

        percurso = new Object[totalVertices];
    }

    public void NovoVertice(
        T informacao) throws Exception
    {
        vertices[numVerts] = new Vertice<T>(informacao);
        numVerts++;
    }

    public void NovaAresta(
        int origem,
        int destino)
    {
        adjMatrix[origem][destino] = 1;
    }

    public void NovaAresta(
        int origem,
        int destino,
        int peso)
    {
        adjMatrix[origem][destino] = peso;
    }

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
        Pilha<T> gPilha = new Pilha<T>(); // para guardar a sequência de vértices
        while (numVerts > 0)
        {
            int currVertex = SemSucessores();
            if (currVertex == -1)
                return "Erro: grafo possui ciclos.";
            try
            {
                gPilha.empilhar(((Vertice<T>)vertices[currVertex]).getInfo());   // empilha vértice
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
            if ((adjMatrix[vertice][j] != INFINITY) && (!((Vertice<T>)vertices[j]).foiVisitado))
                    return j;
        return -1;
    }

    public void PercursoEmProfundidade()
    {
        Pilha<Integer> gPilha = new Pilha<Integer>(); // para guardar a sequência de vértices
        ((Vertice<T>)vertices[0]).foiVisitado = true;
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
                ((Vertice<T>)vertices[v]).foiVisitado = true;
                try
                {
                    gPilha.empilhar(v);
                }
                catch(Exception exc){}
            }
        }
        for (int j = 0; j <= numVerts - 1; j++)
            ((Vertice<T>)vertices[j]).foiVisitado = false;
    }

    public void PercursoEmProfundidadeRec(
        int part)
    {
        int i;
        ((Vertice<T>)vertices[part]).foiVisitado = true;
        for (i = 0; i < numVerts; ++i)
            if (adjMatrix[part][i] != INFINITY && !((Vertice<T>)vertices[i]).foiVisitado)
        PercursoEmProfundidadeRec(i);
    }

    /*public void PercursoPorLargura()
    {
        Queue<Integer> gQueue = new Queue<Integer>();
        vertices[0].foiVisitado = true;
        gQueue.Enqueue(0);
        int vert1, vert2;
        while (gQueue.Count > 0)
        {
            vert1 = gQueue.Dequeue();
            vert2 = ObterVerticeAdjacenteNaoVisitado(vert1);
            while (vert2 != -1)
            {
                vertices[vert2].foiVisitado = true;
                gQueue.Enqueue(vert2);
                vert2 = ObterVerticeAdjacenteNaoVisitado(vert1);
            }
        }
        for (int i = 0; i < numVerts; i++)
            vertices[i].foiVisitado = false;
    }*/

    public void ArvoreGeradoraMinima(
            int primeiro)
    {
        Pilha<Integer> gPilha = new Pilha<Integer>(); // para guardar a sequência de vértices
        ((Vertice<T>)vertices[primeiro]).foiVisitado = true;
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
                ((Vertice<T>)vertices[ver]).foiVisitado = true;
                try
                {
                    gPilha.empilhar(ver);
                }
                catch(Exception exc){}
            }
        }
        for (int j = 0; j <= numVerts - 1; j++)
            ((Vertice<T>)vertices[j]).foiVisitado = false;
    }

    public String Caminho(
            int inicioDoPercurso,
            int finalDoPercurso)
    {
        for (int j = 0; j < numVerts; j++)
            ((Vertice<T>)vertices[j]).foiVisitado = false;

        ((Vertice<T>)vertices[inicioDoPercurso]).foiVisitado = true;
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
            doInicioAteAtual = ((DistOriginal)percurso[indiceDoMenor]).distancia;

            // visitamos o vértice com a menor distância desde o inicioDoPercurso
            ((Vertice<T>)vertices[verticeAtual]).foiVisitado = true;
            AjustarMenorCaminho();
        }

        return ExibirPercursos(inicioDoPercurso, finalDoPercurso);
    }

    public int ObterMenor()
    {
        int distanciaMinima = INFINITY;
        int indiceDaMinima = 0;
        for (int j = 0; j < numVerts; j++)
            if (!(((Vertice<T>)vertices[j]).foiVisitado) && (((DistOriginal)percurso[j]).distancia < distanciaMinima))
            {
                distanciaMinima =((DistOriginal)percurso[j]).distancia;
                indiceDaMinima = j;
            }
        return indiceDaMinima;
    }

    public void AjustarMenorCaminho()
    {
        for (int coluna = 0; coluna < numVerts; coluna++)
            if (!((Vertice<T>)vertices[coluna]).foiVisitado)       // para cada vértice ainda não visitado
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
                    ((DistOriginal)percurso[coluna]).verticePai = verticeAtual;
                    ((DistOriginal)percurso[coluna]).distancia = doInicioAteMargem;
                }
            }
    }

    public String ExibirPercursos(
            int inicioDoPercurso,
            int finalDoPercurso)
    {
        String linha = "", resultado = "";
        for (int j = 0; j < numVerts; j++)
        {
            linha += ((Vertice<T>)vertices[j]).getInfo().toString() + "=";
            if (((DistOriginal)percurso[j]).distancia == INFINITY)
                linha += "inf";
            else
                linha += ((DistOriginal)percurso[j]).distancia;
            String pai = ((Vertice<T>)vertices[((DistOriginal)percurso[j]).verticePai]).getInfo().toString();
            linha += "(" + pai + ") ";
        }

        int onde = finalDoPercurso;
        Pilha<T> pilha = new Pilha<T>();

        int cont = 0;
        while (onde != inicioDoPercurso)
        {
            onde = ((DistOriginal)percurso[onde]).verticePai;

            try
            {
                pilha.empilhar(((Vertice<T>)vertices[onde]).getInfo());
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

        if ((cont == 1) && (((DistOriginal)percurso[finalDoPercurso]).distancia == INFINITY))
            resultado = "Não há caminho";
        else
            resultado += " --> " + ((Vertice<T>)vertices[finalDoPercurso]).getInfo().toString();
        return resultado;
    }
}