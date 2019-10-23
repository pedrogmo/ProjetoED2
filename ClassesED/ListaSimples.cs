using System;
using System.IO;

    public class ListaSimples<Dado> where Dado : IComparable<Dado>
    {
        protected NoLista<Dado> atual, primeiro, anterior, ultimo;
        protected int qtosNos;
        protected bool primeiroAcessoDoPercurso;

        public ListaSimples()
        {
            primeiro = atual = anterior = ultimo = null;
            qtosNos = 0;
        }

        public bool EstaVazia { get => primeiro == null; }

        public NoLista<Dado> Primeiro { get => primeiro; }

        public NoLista<Dado> Ultimo { get => ultimo; }

        protected NoLista<Dado> Atual { get => atual; }

        //deprecated
        public void InserirAntesDoInicio(Dado d)
        {
            if (d == null) throw new Exception("Dado nulo");
            InserirAntesDoInicio(new NoLista<Dado>(d, null));
        }

        //deprecated
        public void InserirAposFim(Dado d)
        {
            if (d == null) throw new Exception("Dado nulo");
            InserirAposFim(new NoLista<Dado>(d, null));
        }

        protected void InserirAntesDoInicio(NoLista<Dado> novoNo)
        {
            novoNo.Prox = primeiro;
            primeiro = novoNo;
            if (EstaVazia)
                ultimo = novoNo;
            qtosNos++;
        }

        protected void InserirAposFim(NoLista<Dado> novoNo)
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
                NoLista<Dado> um, dois = null, tres;
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

        public ListaSimples<Dado> Casamento(ListaSimples<Dado> l)
        {
            if (l == null) throw new Exception("Lista nula");
            ListaSimples<Dado> ret = new ListaSimples<Dado>();
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

        public bool ExisteDado(Dado d)
        {
            if (d == null) throw new Exception("Dado nulo");
            atual = primeiro;
            anterior = null;
            if (EstaVazia) return false;
            if (d.CompareTo(primeiro.Info) < 0) return false;
            if (d.CompareTo(ultimo.Info) > 0)
            {
                anterior = ultimo;
                atual = null;
                return false;
            }
            while (atual != null)
            {
                if (d.CompareTo(atual.Info) == 0) return true;
                if (d.CompareTo(atual.Info) < 0) return false;
                anterior = atual;
                atual = atual.Prox;
            }
            return false;
        }

        public void InserirOrdem(Dado d)
        {
            if (d == null) throw new Exception("Dado nulo");
            if (ExisteDado(d)) throw new Exception("Dado já existente");
            NoLista<Dado> n = new NoLista<Dado>(d, null);
            if (EstaVazia || anterior == null && atual != null)
                InserirAntesDoInicio(n);
            else InserirMeio(n);
        }

        protected void InserirMeio(NoLista<Dado> novoNo)
        {
            novoNo.Prox = atual;
            anterior.Prox = novoNo;
            if (anterior == ultimo)
                ultimo = novoNo;
            qtosNos++;
        }

        public void Excluir(Dado d)
        {
            if (d == null) throw new Exception("Dado nulo");
            if (!ExisteDado(d)) throw new Exception("Dado não existente");
            RemoverNo(anterior, atual);
        }

        protected void RemoverNo(NoLista<Dado> ant, NoLista<Dado> atu)
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
                ListaSimples<Dado> ord = new ListaSimples<Dado>();
                NoLista<Dado> atuM = null, antM = null, novoNo;
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

        protected void ProcurarMenor(ref NoLista<Dado> antM, ref NoLista<Dado> atuM)
        {
            antM = anterior = null;
            atuM = atual = primeiro;
            IniciarPercursoSequencial();
            while (PodePercorrer())
            {
                if (atual.Info.CompareTo(atuM.Info) < 0)
                {
                    atuM = atual;
                    antM = anterior;
                }
            }
        }

        public void IniciarPercursoSequencial()
        {
            atual = primeiro;
            anterior = null;
            primeiroAcessoDoPercurso = true;
        }

        public bool PodePercorrer()
        {
            if (primeiroAcessoDoPercurso)
                primeiroAcessoDoPercurso = false;
            else
            {
                anterior = atual;
                atual = atual.Prox;
            }
            return atual != null;
        }

        public void PercorrerLista()
        {
            IniciarPercursoSequencial();
            while (PodePercorrer())
                Console.WriteLine(atual.Info);
        }

        /*public void Listar(ListBox onde)
        {
            onde.Items.Clear();
            IniciarPercursoSequencial();
            while (PodePercorrer())
                onde.Items.Add(atual.Info);
        }*/

        public override string ToString()
        {
            string ret = "";
            for (atual = primeiro; atual != null; atual = atual.Prox)
                ret += atual.ToString() + "\n";
            return ret;
        }
    }