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

        /*public int Hash(
            string s)
        {
            long tot = 0;
            foreach (char c in s)
                tot += 37 * tot + (int)c;
            tot = tot % data.GetUpperBound(0);
            if (tot < 0)
                tot += data.GetUpperBound(0);
            return (int)tot;
        }*/
        private int Hash(T item)
        {
            return Math.Abs(item.GetHashCode() % SIZE);
        }

        public void Insert(
            T item)
        {
            int hash_value;
            hash_value = Hash(item);
            if (!data[hash_value].ExisteDado(item))
                data[hash_value].InserirAposFim(item);
        }

        public bool Remove(
            T item)
        {
            int hash_value;
            hash_value = Hash(item);
            if (data[hash_value].ExisteDado(item))
            {
                data[hash_value].Excluir(item);
                return true;
            }
            return false;
        }

        public void Exibir()
        {
            for (int i = 0; i < data.GetUpperBound(0); i++)
                if (!data[i].EstaVazia)
                    foreach (string chave in data[i])
                        Console.WriteLine(i + " " + chave);
            Console.ReadKey();
        }

        public string Conteudo()
        {
            string ret = "";
            for (int i = 0; i < data.GetUpperBound(0); i++)
                if (!data[i].EstaVazia)
                    foreach (T chave in data[i])
                        ret += i + ": {" + chave.ToString() + "}";
            return ret;
        }
    }
}
