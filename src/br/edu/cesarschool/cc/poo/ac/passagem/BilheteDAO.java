package br.edu.cesarschool.cc.poo.ac.passagem;

import br.edu.cesarschool.next.oo.persistenciaobjetos.CadastroObjetos;

public class BilheteDAO {
    private CadastroObjetos cadastro = new CadastroObjetos(Bilhete.class);

    private String obterIdUnico(Bilhete bilhete) {
        return bilhete.gerarNumero();
    }

    public Bilhete buscar(String numeroBilhete) {
        return (Bilhete)cadastro.buscar(numeroBilhete);
    }

    public boolean incluir(Bilhete bilhete) {
        String numeroBilhete = obterIdUnico(bilhete);
        Bilhete cli = buscar(numeroBilhete);

        if (cli == null) {
            cadastro.incluir(bilhete, numeroBilhete);
            return true;
        }
        return false;
    }

    public boolean alterar(Bilhete bilhete) {
        String numeroBilhete = obterIdUnico(bilhete);
        Bilhete cli = buscar(numeroBilhete);
        if (cli != null) {
            cadastro.alterar(bilhete, numeroBilhete);
            return true;
        }
        return false;
    }

    public boolean excluir(String numeroBilhete) {
        Bilhete cli = buscar(numeroBilhete);
        if (cli != null) {
            cadastro.excluir(numeroBilhete);
            return true;
        }
        return false;
    }
}
