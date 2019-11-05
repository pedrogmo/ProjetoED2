using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace TrensCidades.Classes
{
    //Gustavo Henrique de Meira - 18015
    //Pedro Gomes Moreira - 18174

    class No<T>
    {
        protected T info;
        protected No<T> prox;

        public No(T inf, No<T> prx)
        {
            Info = inf;
            Prox = prx;
        }

        public No(T inf) : this(inf, null)
        { }

        public T Info
        {
            get => info;
            set
            {
                if (value == null)
                    throw new Exception("Informação ausente");
                info = value;
            }
        }

        public No<T> Prox
        {
            get => prox;
            set => prox = value;
        }
    }
}
