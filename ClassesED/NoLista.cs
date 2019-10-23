using System;

public class NoLista<Dado> where Dado : IComparable<Dado> //interface que obriga a implementação de CompareTo(Dado) na classe
{
    //object info; 
    //ancestral de qualquer outra classe, classe primitiva
    //compatível com todas
    //não vamos fazer isso porque queremos lista restrita

    Dado info;
    NoLista<Dado> prox;
    //em C# o tipo default (sem nada antes) é automaticamente private

    public Dado Info
    {
        get
        {
            return info;
        }
        set
        {
            if (value != null)
                info = value;
        }
    }
    public NoLista<Dado> Prox
    {
        get => prox;
        set => prox = value;
    }
    //propriedades criadas para acesso a atributos por outras classes
    //elas substituem os getters e setters do java, mas são mais limpas

    public NoLista(Dado info, NoLista<Dado> prox)
    {
        Info = info;
        Prox = prox;
    }
}
