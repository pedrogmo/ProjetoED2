//Gustavo Henrique de Meira - 18015
//Pedro Gomes Moreira - 18174

package com.example.trenscidadesandroid.classes.utilildades;

import java.lang.reflect.Method;

//Classe com métodos auxiliares ao projeto
public class Utilidades
{
    //Método que movimenta o texto n casas à direita
    public static String padRight(
        String s,
        int n)
    {
        return String.format("%-" + n + "s", s);
    }

    //Método que retorna um clone do objeto passado por parâmetro
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
