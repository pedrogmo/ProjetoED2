using System;

namespace TrensCidades.Classes
{
    //Gustavo Henrique de Meira - 18015
    //Pedro Gomes Moreira - 18174

    class Vertice<T>
    {
        public bool foiVisitado;
        public T info;

        public Vertice(
            T informacao)
        {
            if (informacao == null)
                throw new Exception("Info do vértice nula");

            info = informacao;
            foiVisitado = false;
        }
    }
}