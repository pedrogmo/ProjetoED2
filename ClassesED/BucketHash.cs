using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace hashString1
{
    class BucketHash
    {
        private const int SIZE = 101;
        private ArrayList[] data;

        public BucketHash()
        {
            data = new ArrayList[SIZE];
            for (int i = 0; i <= SIZE - 1; i++)
                data[i] = new ArrayList();
        }

        public int Hash(
            string s)
        {
            long tot = 0;
            foreach (char c in s)
                tot += 37 * tot + (int)c;
            tot = tot % data.GetUpperBound(0);
            if (tot < 0)
                tot += data.GetUpperBound(0);
            return (int)tot;
        }

        public void Insert(
            string item)
        {
            int hash_value;
            hash_value = Hash(item);
            if (!data[hash_value].Contains(item))
                data[hash_value].Add(item);
        }

        public bool Remove(
            string item)
        {
            int hash_value;
            hash_value = Hash(item);
            if (data[hash_value].Contains(item))
            {
                data[hash_value].Remove(item);
                return true;
            }
            return false;
        }

        public void Exibir()
        {
            for (int i = 0; i < data.GetUpperBound(0); i++)
                if (data[i].Count > 0)
                    foreach (string chave in data[i])
                        Console.WriteLine(i + " " + chave);
            Console.ReadKey();
        }
    }
}
