package br.edu.cesarschool.cc.poo.ac.cliente.comexcecao;

public class ExcecaoRegistroInexistente extends Exception{
    String message;
    public ExcecaoRegistroInexistente(String message){
        super(message);
    }
}
