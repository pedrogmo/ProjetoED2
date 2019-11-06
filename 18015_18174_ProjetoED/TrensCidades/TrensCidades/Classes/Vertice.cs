using System;

namespace TrensCidades.Classes
{
    //Gustavo Henrique de Meira - 18015
    //Pedro Gomes Moreira - 18174

    class Vertice<T>
    {
        private bool foiVisitado;
        private T info;

        public Vertice(
            T informacao)
        {
            Info = informacao;
            FoiVisitado = false;
        }

        public bool FoiVisitado { get => foiVisitado; set => foiVisitado = value; }

        public T Info
        {
            get => info;
            set
            {
                if (value == null)
                    throw new Exception("Info do vértice nulo");
                info = value;
            }
        }
    }
}