package br.edu.cesarschool.cc.poo.ac.cliente.comexcecao;

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;

public class ExcecaoValidacao extends Exception{
    List<String> mensagens;

    public ExcecaoValidacao(String mensagem) {
        this.mensagens = new ArrayList<>();
        this.mensagens.add(mensagem);
    }

    public List<String> getMensagens() {
        return this.mensagens;
    }

    public void adicionarMensagem(String mensagem) {
        this.mensagens.add(mensagem);
    }
}
