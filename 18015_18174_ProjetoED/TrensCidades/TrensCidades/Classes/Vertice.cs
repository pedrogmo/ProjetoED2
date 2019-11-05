using System;

namespace TrensCidades.Classes
{
    //Gustavo Henrique de Meira - 18015
    //Pedro Gomes Moreira - 18174

    class Vertice
    {
        public bool foiVisitado;
        public string rotulo;

        public Vertice(string label)
        {
            rotulo = label;
            foiVisitado = false;
        }
    }
}