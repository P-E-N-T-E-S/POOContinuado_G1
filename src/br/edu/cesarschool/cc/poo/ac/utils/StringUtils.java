package br.edu.cesarschool.cc.poo.ac.utils;

public class StringUtils {
    private StringUtils(){

    }
    public static boolean isVaziaOuNula(String valor){
        if(valor == null){
            return false;
        }
        if(valor.trim().equals("")){
            return false;
        }else{
            return true;
        }
    }
}
