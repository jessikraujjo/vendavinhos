package com.venda.util;

public class Utils {
    public static String retiraCaracteresEspeciais(String stringFonte) {
        String retornastring = stringFonte;
        retornastring = retornastring.replaceAll("[^0-9]", "");
        return retornastring;
    }
}