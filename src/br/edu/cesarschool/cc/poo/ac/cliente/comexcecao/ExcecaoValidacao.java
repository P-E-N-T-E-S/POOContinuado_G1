package br.edu.cesarschool.cc.poo.ac.cliente.comexcecao;

public class ExcecaoValidacao extends Exception {
    private java.util.List<String> mensagens;

    public ExcecaoValidacao() {
        this.mensagens = new java.util.ArrayList<String>();
    }

    public java.util.List<String> getMensagens() {
        return mensagens;
    }

    public void adicionarMensagem(String mensagem) {
        mensagens.add(mensagem);
    }
    
}