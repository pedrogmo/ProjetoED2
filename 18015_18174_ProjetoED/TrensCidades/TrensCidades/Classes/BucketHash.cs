using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace TrensCidades.Classes
{
    //Gustavo Henrique de Meira - 18015
    //Pedro Gomes Moreira - 18174

    class BucketHash<T> where T : IComparable<T>
    {
        private const int SIZE = 101;
        private Lista<T>[] data;

        public BucketHash()
        {
            data = new Lista<T>[SIZE];
            for (int i = 0; i <= SIZE - 1; i++)
                data[i] = new Lista<T>();
        }

        private int Hash(T item)
        {
            return Math.Abs(item.GetHashCode() % SIZE);
        }

        public void Inserir(
            T item)
        {
            int hashValue;
            hashValue = Hash(item);
            if (!data[hashValue].ExisteDado(item))
                data[hashValue].InserirFim(item);
        }

        public bool Excluir(
            T item)
        {
            int hashValue;
            hashValue = Hash(item);
            if (data[hashValue].ExisteDado(item))
            {
                data[hashValue].Excluir(item);
                return true;
            }
            return false;
        }

        public void Exibir()
        {
            for (int i = 0; i < data.GetUpperBound(0); i++)
                if (!data[i].EstaVazia)
                    foreach (T chave in data[i])
                        Console.WriteLine(i + " " + chave.ToString());
            Console.ReadKey();
        }

        public string Conteudo()
        {
            string ret = "";
            for (int i = 0; i < data.GetUpperBound(0); i++)
                if (!data[i].EstaVazia)
                    foreach (T chave in data[i])
                        ret += i + ": {" + chave.ToString() + "}" + "\n";
            return ret;
        }
    }
}
