package br.edu.cesarschool.cc.poo.ac.cliente;

import br.edu.cesarschool.cc.poo.ac.tela.Tela;

public class ProgramaCadastroCliente {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Tela tela = new Tela();
                tela.criarTela();
            }
        });

        TelaCliente telaCliente = new TelaCliente();
        telaCliente.inicializaTelasCadastroProduto();
    }
}