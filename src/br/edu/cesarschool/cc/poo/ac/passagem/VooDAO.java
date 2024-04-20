package br.edu.cesarschool.cc.poo.ac.passagem;

import br.edu.cesarschool.cc.poo.ac.cliente.Cliente;
import br.edu.cesarschool.next.oo.persistenciaobjetos.CadastroObjetos;

public class VooDAO {
    private CadastroObjetos cadastro = new CadastroObjetos(Voo.class);

    public Voo buscar(String idVoo){
        return(Voo)cadastro.buscar(idVoo);
    }

    public boolean incluir(Voo voo){
        String idUnico = voo.obterIdVoo();
        Voo cli = buscar(idUnico);;
        if (cli == null) {
            cadastro.incluir(voo, idUnico);
            return true;
        }
        return false;
    }

    public boolean alterar(Voo voo){
        String idUnico = voo.obterIdVoo();
        Voo cli = buscar(idUnico);
        if (cli != null) {
            cadastro.alterar(voo, idUnico);
            return true;
        }
        return false;
    }
    public boolean excluir(String idVoo){
        Voo cli = buscar(idVoo);
        if (cli != null) {
            cadastro.excluir(idVoo);
            return true;
        }
        return false;
    }
}
