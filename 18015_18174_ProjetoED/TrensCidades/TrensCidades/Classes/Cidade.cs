using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace TrensCidades.Classes
{
    //Gustavo Henrique de Meira - 18015
    //Pedro Gomes Moreira - 18174

    class Cidade : IComparable<Cidade>
    {
        private int codigo;
        private float x, y;
        private string nome;

        public const int COMECO_CODIGO = 0;
        public const int TAMANHO_CODIGO = 2;
        public const int COMECO_NOME = COMECO_CODIGO + TAMANHO_CODIGO;
        public const int TAMANHO_NOME = 16;
        public const int COMECO_X = COMECO_NOME + TAMANHO_NOME;
        public const int TAMANHO_X = 6;
        public const int COMECO_Y = COMECO_X + TAMANHO_X;
        public const int TAMANHO_Y = 5;

        public Cidade(int c, string n, int x, int y)
        {
            Codigo = c;
            Nome = n;
            X = x;
            Y = y;
        }

        public Cidade(string linha)
        {
            try
            {
                Codigo = int.Parse(linha.Substring(COMECO_CODIGO, TAMANHO_CODIGO).Trim());
                Nome = linha.Substring(COMECO_NOME, TAMANHO_NOME).Trim();
                X = int.Parse(linha.Substring(COMECO_X, TAMANHO_X).Trim());
                Y = int.Parse(linha.Substring(COMECO_Y, TAMANHO_Y).Trim());
            }
            catch
            {
                throw new Exception("String da cidade inválida");
            }
        }

        public Cidade(int codC)
        {
            Codigo = codC;
        }

        public int Codigo
        {
            get => codigo;
            private set
            {
                if (value < 0)
                    throw new Exception("Código inválido");
                codigo = value;
            }
        }

        public string Nome
        {
            get => nome;
            private set
            {
                if (value == null || value == "")
                    throw new Exception("Nome inválido");
                nome = value;
            }
        }

        public int X
        {
            get => x;
            set
            {
                if (value < 0)
                    throw new Exception("Coordenada x inválida");
                x = value;
            }
        }

        public int Y
        {
            get => y;
            set
            {
                if (value < 0)
                    throw new Exception("Coordenada y inválida");
                y = value;
            }
        }   
        
        public int CompareTo(Cidade c)
        {
            return codigo - c.codigo;
        }

        public override string ToString()
        {
            return $"{codigo} - {nome}";
        }
    }
}
