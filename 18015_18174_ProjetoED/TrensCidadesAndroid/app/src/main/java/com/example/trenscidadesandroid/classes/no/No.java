package com.example.trenscidadesandroid.classes.no;

import java.io.Serializable;
import java.lang.reflect.Method;

public class No<T>
    implements Serializable
{
    private T info;
    public No<T> prox;

    public No(
            T info,
            No<T> prox) throws Exception
    {
        setInfo(info);
        this.prox = prox;
    }

    public T getInfo()
    {
        return info;
    }

    public void setInfo(
            T info) throws Exception
    {
        if (info == null)
            throw new Exception("No<T> - setInfo: info nula");
        if (info instanceof Cloneable)
            this.info = cloneDeT(info);
        this.info = info;
    }

    private T cloneDeT(T t)
    {
        T ret = null;
        try
        {
            Class<?> classe = t.getClass();
            Class<?>[] tiposFormais = null;
            Method metodo = classe.getMethod("clone", tiposFormais);
            Object[] pametrosReais = null;
            ret = (T) metodo.invoke(t, pametrosReais);
        }
        catch(Exception erro){}
        return ret;
    }
}
