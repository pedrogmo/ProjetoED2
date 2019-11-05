using System;
using System.Collections;
using System.IO;

namespace TrensCidades.Classes
{
    //Gustavo Henrique de Meira - 18015
    //Pedro Gomes Moreira - 18174

    class Lista<T> where T : IComparable<T>
    {
        protected No<T> atual, primeiro, anterior, ultimo;
        protected int qtosNos;

        public Lista()
        {
            primeiro = atual = anterior = ultimo = null;
            qtosNos = 0;
        }

        public bool EstaVazia { get => primeiro == null; }

        public No<T> Primeiro { get => primeiro; }

        public No<T> Ultimo { get => ultimo; }

        protected No<T> Atual { get => atual; }

        public int Quantidade { get => qtosNos; }

        public T this[int indice]
        {
            get
            {
                int cont = 0;
                for(atual = primeiro; atual != null; atual = atual.Prox)
                {
                    if (cont == indice)
                        return atual.Info;
                    ++cont;
                }
                return default(T);
            }
            set
            {
                int cont = 0;
                for (atual = primeiro; atual != null; atual = atual.Prox)
                {
                    if (cont == indice)
                        atual.Info = value;
                    ++cont;
                }
            }
        }

        public void InserirAntesDoInicio(T d)
        {
            if (d == null)
                throw new Exception("Dado nulo");
            InserirAntesDoInicio(new No<T>(d, null));
        }

        public void InserirAposFim(T d)
        {
            if (d == null)
                throw new Exception("Dado nulo");
            InserirAposFim(new No<T>(d, null));
        }

        protected void InserirAntesDoInicio(No<T> novoNo)
        {
            novoNo.Prox = primeiro;
            primeiro = novoNo;
            if (EstaVazia)
                ultimo = novoNo;
            qtosNos++;
        }

        protected void InserirAposFim(No<T> novoNo)
        {
            novoNo.Prox = null;
            if (EstaVazia)
                primeiro = novoNo;
            else
                ultimo.Prox = novoNo;
            ultimo = novoNo;
            qtosNos++;
        }

        public void Inverter()
        {
            if (!EstaVazia)
            {
                No<T> um, dois = null, tres;
                um = primeiro;
                dois = um.Prox;
                while (dois != null)
                {
                    tres = dois.Prox;
                    dois.Prox = um;
                    um = dois;
                    dois = tres;
                }
                ultimo = primeiro;
                ultimo.Prox = null;
                primeiro = um;
            }
        }

        public Lista<T> Casamento(Lista<T> l)
        {
            if (l == null) throw new Exception("Lista nula");
            Lista<T> ret = new Lista<T>();
            atual = primeiro;
            l.atual = l.primeiro;
            while (atual != null && l.atual != null)
            {
                if (atual.Info.CompareTo(l.atual.Info) < 0)
                {
                    ret.InserirAposFim(atual);
                    atual = atual.Prox;
                }
                else if (atual.Info.CompareTo(l.atual.Info) > 0)
                {
                    ret.InserirAposFim(l.atual);
                    l.atual = l.atual.Prox;
                }
                else if (atual.Info.CompareTo(l.atual.Info) == 0)
                {
                    ret.InserirAposFim(atual);
                    atual = atual.Prox;
                    l.atual = l.atual.Prox;
                }
            }
            while (atual != null)
            {
                ret.InserirAposFim(atual);
                atual = atual.Prox;
            }
            while (l.atual != null)
            {
                ret.InserirAposFim(l.atual);
                l.atual = l.atual.Prox;
            }
            return ret;
        }

        public bool ExisteDado(T d)
        {
            if (d == null)
                throw new Exception("Dado nulo");

            atual = primeiro;
            anterior = null;

            if (EstaVazia)
                return false;
            if (d.CompareTo(primeiro.Info) < 0)
                return false;
            if (d.CompareTo(ultimo.Info) > 0)
            {
                anterior = ultimo;
                atual = null;
                return false;
            }
            while (atual != null)
            {
                if (d.CompareTo(atual.Info) == 0)
                    return true;
                if (d.CompareTo(atual.Info) < 0)
                    return false;
                anterior = atual;
                atual = atual.Prox;
            }
            return false;
        }

        /*public void InserirOrdem(T d)
        {
            if (d == null) throw new Exception("Dado nulo");
            if (ExisteDado(d)) throw new Exception("Dado já existente");
            No<T> n = new No<T>(d, null);
            if (EstaVazia || anterior == null && atual != null)
                InserirAntesDoInicio(n);
            else InserirMeio(n);
        }*/

        protected void InserirMeio(No<T> novoNo)
        {
            novoNo.Prox = atual;
            anterior.Prox = novoNo;
            if (anterior == ultimo)
                ultimo = novoNo;
            qtosNos++;
        }

        public void Excluir(T d)
        {
            if (d == null) throw new Exception("Dado nulo");
            if (!ExisteDado(d)) throw new Exception("Dado não existente");
            RemoverNo(anterior, atual);
        }

        protected void RemoverNo(No<T> ant, No<T> atu)
        {
            if (ant == null && atu != null)
            {
                primeiro = atu.Prox;
                if (primeiro == null) //caso o primeiro fique nulo, a lista ficou vazia
                    ultimo = null; //ultimo também fica nulo
            }
            else
            {
                ant.Prox = atu.Prox;
                if (atu == null)
                    ultimo = ant;
            }
            qtosNos--;
        }

        public void Ordenar()
        {
            if (!EstaVazia)
            {
                Lista<T> ord = new Lista<T>();
                No<T> atuM = null, antM = null, novoNo;
                while (!EstaVazia)
                {
                    ProcurarMenor(ref antM, ref atuM);
                    novoNo = atuM;
                    RemoverNo(antM, atuM);
                    ord.InserirAposFim(novoNo);
                }
                primeiro = ord.primeiro;
                ultimo = ord.ultimo;
                qtosNos = ord.qtosNos;
                atual = anterior = null;
            }
        }

        protected void ProcurarMenor(ref No<T> antM, ref No<T> atuM)
        {
            antM = anterior = null;
            atuM = atual = primeiro;

            for (atual = primeiro, anterior = null;
                atual != null;
                anterior = atual, atual = atual.Prox)
            {
                if (atual.Info.CompareTo(atuM.Info) < 0)
                {
                    atuM = atual;
                    antM = anterior;
                }
            }
        }

        public override string ToString()
        {
            string ret = "";
            for (atual = primeiro; atual != null; atual = atual.Prox)
                ret += atual.ToString() + "\n";
            return ret;
        }

        //Métodos necessários para fazer foreach
        public IEnumerator GetEnumerator()
        {
            return (IEnumerator)this;
        }

        public bool MoveNext()
        {
            atual = atual.Prox;
            return atual != null;
        }

        public void Reset()
        {
            atual = primeiro;
        }

        public object Current
        {
            get => atual.Info;
        }
    }
}