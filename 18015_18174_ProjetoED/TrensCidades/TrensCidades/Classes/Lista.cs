using System;
using System.Collections;
using System.Collections.Generic;
using System.IO;

namespace TrensCidades.Classes
{
    //Gustavo Henrique de Meira - 18015
    //Pedro Gomes Moreira - 18174

    class Lista<T> : IEnumerable<T>
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

        public void InserirInicio(T d)
        {
            if (d == null)
                throw new Exception("Dado nulo");
            InserirInicio(new No<T>(d, null));
        }

        public void InserirFim(T d)
        {
            if (d == null)
                throw new Exception("Dado nulo");
            InserirFim(new No<T>(d, null));
        }

        protected void InserirInicio(No<T> novoNo)
        {
            novoNo.Prox = primeiro;
            primeiro = novoNo;
            if (EstaVazia)
                ultimo = novoNo;
            qtosNos++;
        }

        protected void InserirFim(No<T> novoNo)
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

        public bool ExisteDado(T d)
        {
            if (d == null)
                throw new Exception("Dado nulo");

            if (EstaVazia)
                return false;

            for (atual = primeiro, anterior = null;
                atual != null;
                anterior = atual, atual = atual.Prox)
            {
                if (d.Equals(atual.Info))
                    return true;
            }

            return false;
        }

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
            if (ExisteDado(d))
                RemoverNo(anterior, atual);
        }

        public T Buscar(
            T dado)
        {
            for(atual = primeiro; atual != null; atual = atual.Prox)
                if (atual.Info.Equals(dado))
                    return atual.Info;
            return default(T);
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

        public override string ToString()
        {
            string ret = "";
            for (atual = primeiro; atual != null; atual = atual.Prox)
                ret += atual.ToString() + "\n";
            return ret;
        }

        //Métodos necessários para fazer foreach
        public IEnumerator<T> GetEnumerator()
        {
            for (atual = primeiro; atual != null; atual = atual.Prox)
                yield return atual.Info;
        }

        IEnumerator IEnumerable.GetEnumerator()
        {
            return GetEnumerator();
        }
    }
}