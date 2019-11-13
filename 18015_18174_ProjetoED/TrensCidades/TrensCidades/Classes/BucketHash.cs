using System;

namespace TrensCidades.Classes
{
    //Gustavo Henrique de Meira - 18015
    //Pedro Gomes Moreira - 18174

    class BucketHash<T>
    {
        private const int TAMANHO = 500;
        private Lista<T>[] conteudo;
        private int qtd;

        public BucketHash()
        {
            conteudo = new Lista<T>[TAMANHO];
        }

        private int Hash(
            T item)
        {
            return Math.Abs(item.GetHashCode() % TAMANHO);
        }

        public void Inserir(
            T item)
        {
            int valorHash = Hash(item);

            if (conteudo[valorHash] == null)
                conteudo[valorHash] = new Lista<T>();

            if (!conteudo[valorHash].ExisteDado(item))
            {
                conteudo[valorHash].InserirFim(item);
                ++qtd;
            }
        }

        public void Excluir(
            T item)
        {
            int valorHash = Hash(item);

            if (conteudo[valorHash] != null)
                if (conteudo[valorHash].Excluir(item))
                    --qtd;
        }

        public T Buscar(
            T chave)
        {
            int valorHash = Hash(chave);
            if (conteudo[valorHash] == null)
                return default(T);
            return conteudo[valorHash].Buscar(chave);
        }

        public void Exibir()
        {
            for (int i = 0; i < conteudo.GetUpperBound(0); i++)
                if (conteudo[i] != null)
                    foreach (T chave in conteudo[i])
                        Console.WriteLine(i + " " + chave.ToString());
            Console.ReadKey();
        }

        public string Conteudo()
        {
            string ret = "";
            for (int i = 0; i < conteudo.GetUpperBound(0); i++)
                if (conteudo[i] != null)
                    foreach (T chave in conteudo[i])
                        ret += i + ": {" + chave.ToString() + "}" + "\n";
            return ret;
        }

        public int Quantidade { get => qtd; }
    }
}
