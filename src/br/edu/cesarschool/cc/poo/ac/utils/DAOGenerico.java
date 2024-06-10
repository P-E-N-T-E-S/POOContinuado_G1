package br.edu.cesarschool.cc.poo.ac.utils;

import java.io.Serializable;
import br.edu.cesarschool.next.oo.persistenciaobjetos.CadastroObjetos;

public class DAOGenerico {
    private CadastroObjetos cadastro;

    public DAOGenerico(Class<?> tipo) {
        this.cadastro = new CadastroObjetos(tipo);
    }

    public Registro buscar(String id) {
        return (Registro)cadastro.buscar(id);
    }

    public boolean incluir(Registro reg) {
        String idUnico = reg.getIdUnico();
        Registro reg2 = buscar(idUnico);

        if (reg2 == null) {
            cadastro.incluir(reg, idUnico);
            return true;
        }

        return false;
    }

    public boolean alterar(Registro reg) {
        String idUnico = reg.getIdUnico();
        Registro reg2 = buscar(idUnico);

        if (reg2 != null) {
            cadastro.alterar(reg, idUnico);
            return true;
        }

        return false;
    }

    public boolean excluir(String id) {
        Registro reg = buscar(id);

        if (reg != null) {
            cadastro.excluir(id);
            return true;
        }

        return false;
    }

    public Registro[] buscarTodos() {
        Serializable[] res = cadastro.buscarTodos();

        if (res == null) {
            return null;
        } else {
            Registro[] regs = new Registro[res.length];
            int i = 0;

            for (Serializable reg : res) {
                regs[i] = (Registro)reg;
                i++;
            }
            return regs;
        }
    }

}