package com.example.trenscidadesandroid.classes.utilildades;

import java.lang.reflect.Method;

public class Utilidades
{
    public static String padRight(
        String s,
        int n)
    {
        return String.format("%-" + n + "s", s);
    }

    public static Object cloneDe(Object t)
    {
        Object ret = null;
        try
        {
            Class<?> classe = t.getClass();
            Class<?>[] tiposFormais = null;
            Method metodo = classe.getMethod("clone", tiposFormais);
            Object[] pametrosReais = null;
            ret = metodo.invoke(t, pametrosReais);
        }
        catch(Exception erro){}
        return ret;
    }
}
