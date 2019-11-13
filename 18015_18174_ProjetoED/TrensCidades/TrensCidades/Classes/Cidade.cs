﻿using System;
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

        //Posição em porcentagem da imagem
        private double x, y;

        private string nome;

        public const int COMECO_CODIGO = 0;
        public const int TAMANHO_CODIGO = 2;
        public const int COMECO_NOME = COMECO_CODIGO + TAMANHO_CODIGO;
        public const int TAMANHO_NOME = 16;
        public const int COMECO_X = COMECO_NOME + TAMANHO_NOME;
        public const int TAMANHO_X = 6;
        public const int COMECO_Y = COMECO_X + TAMANHO_X;
        public const int TAMANHO_Y = 5;

        public Cidade(
            int codigo,
            string nome, 
            int posicaoX,
            int posicaoY)
        {
            Codigo = codigo;
            Nome = nome;
            X = posicaoX;
            Y = posicaoY;
        }

        public Cidade(
            Linha linha)
        {
            try
            {
                string str = linha.Conteudo;
                Codigo = int.Parse(str.Substring(COMECO_CODIGO, TAMANHO_CODIGO).Trim());
                Nome = str.Substring(COMECO_NOME, TAMANHO_NOME).Trim();
                X = double.Parse(str.Substring(COMECO_X, TAMANHO_X).Trim());
                Y = double.Parse(str.Substring(COMECO_Y, TAMANHO_Y).Trim());
            }
            catch
            {
                throw new Exception("String da cidade inválida");
            }
        }

        public Cidade(
            string nome)
        {
            codigo = 0;
            Nome = nome;
            x = 0.0;
            y = 0.0;
        }

        public int Codigo
        {
            get => codigo;
            set
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

        public double X
        {
            get => x;
            set
            {
                if (value < 0.0 || value > 1.0)
                    throw new Exception("Coordenada x inválida");
                x = value;
            }
        }

        public double Y
        {
            get => y;
            set
            {
                if (value < 0.0 || value > 1.0)
                    throw new Exception("Coordenada y inválida");
                y = value;
            }
        }   
        
        public int CompareTo(
            Cidade c)
        {
            return codigo - c.codigo;
        }

        public override string ToString()
        {
            return $"{codigo} - {nome}";
        }

        public string ParaArquivo()
        {
            string ret = (codigo + "").PadRight(TAMANHO_CODIGO);
            ret += nome.PadRight(TAMANHO_NOME);
            ret += (x + "").PadRight(TAMANHO_X);
            ret += (Y + "").PadRight(TAMANHO_Y);
            return ret;
        }

        public override bool Equals(
            object obj)
        {
            if (this == obj)
                return true;
            if (obj == null)
                return false;

            Cidade c = (Cidade)obj;

            return nome.Equals(c.nome);
        }

        public override int GetHashCode()
        {
            int hash = 1;
            foreach (char c in nome)
                hash = 37 * hash + c;
            return hash;
        }
    }
}
