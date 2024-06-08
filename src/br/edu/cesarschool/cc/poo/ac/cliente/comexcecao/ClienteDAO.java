package br.edu.cesarschool.cc.poo.ac.cliente.comexcecao;

import br.edu.cesarschool.cc.poo.ac.cliente.Cliente;
import br.edu.cesarschool.cc.poo.ac.utils.SuperDAO;
import br.edu.cesarschool.cc.poo.ac.utils.Registro;

public class ClienteDAO extends SuperDAO<Cliente> {

    @Override
    public Class<Cliente> obterTipo() {
        return Cliente.class;
    }

    public Cliente buscar(String cpf) throws ExcecaoRegistroInexistente {
        Cliente cliente = (Cliente) daoGenerico.buscar(cpf);
        if (cliente == null) {
        }
        return cliente;
    }

    public void incluir(Cliente cliente) throws ExcecaoRegistroJaExistente{
        Cliente cliente1 = (Cliente) daoGenerico.buscar(cliente.getCpf());
        if (cliente1.getCpf() != null) {
        }
        daoGenerico.incluir(cliente);
    }

    public void alterar(Cliente cliente) throws ExcecaoRegistroInexistente {
        if (buscar(cliente.getCpf()) == null) {
        }
        daoGenerico.alterar(cliente);
    }

    public void excluir(String cpf) throws ExcecaoRegistroInexistente {
        Cliente cliente = buscar(cpf);
        if (cliente == null) {
        }
        daoGenerico.excluir(cpf);
    }

    public Cliente[] buscarTodos() {
        Registro[] registros = daoGenerico.buscarTodos();
        Cliente[] clientes = new Cliente[registros.length];
        for (int i = 0; i < registros.length; i++) {
            clientes[i] = (Cliente) registros[i];
        }
        return clientes;
    }
}
