package br.edu.cesarschool.cc.poo.ac.passagem;

import br.edu.cesarschool.next.oo.persistenciaobjetos.CadastroObjetos;

public class BilheteVipDAO {
    private CadastroObjetos cadastro = new CadastroObjetos(BilheteVip.class);

    private String obterIdUnico(BilheteVip bilheteVip){
        return bilheteVip.gerarNumero();
    }

    public BilheteVip buscar(String numeroBilhete) {
        return (BilheteVip) cadastro.buscar(numeroBilhete);
    }

    public boolean incluir(BilheteVip bilheteVip) {
        String numeroBilhete = bilheteVip.gerarNumero();
        BilheteVip cli = buscar(numeroBilhete);
        if (cli == null) {
            cadastro.incluir(bilheteVip, numeroBilhete);
            return true;
        }
        return false;
    }

    public boolean alterar(BilheteVip bilheteVip) {
        String numeroBilhete = bilheteVip.gerarNumero();
        BilheteVip cli = buscar(numeroBilhete);
        if (cli != null) {
            cadastro.alterar(bilheteVip, numeroBilhete);
            return true;
        }
        return false;
    }

    public boolean excluir(String numeroBilhete) {

        BilheteVip cli = buscar(numeroBilhete);
        if (cli != null) {
            cadastro.excluir(numeroBilhete);
            return true;
        }
        return false;
    }
}
