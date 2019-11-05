using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace TrensCidades.Classes
{
    //Gustavo Henrique de Meira - 18015
    //Pedro Gomes Moreira - 18174

    class PilhaVaziaException : Exception
    {
        public PilhaVaziaException(string msg) : base(msg)
        { }
    }
}
