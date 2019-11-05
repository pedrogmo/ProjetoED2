﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace TrensCidades.Classes
{
    //Gustavo Henrique de Meira - 18015
    //Pedro Gomes Moreira - 18174

    interface IStack<T>
    {
        void Empilhar(T info);
        T Desempilhar();
        T Topo();
        int Tamanho { get; }
        bool EstaVazia { get; }
    }
}
