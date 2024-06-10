package br.edu.cesarschool.cc.poo.ac.cliente.comexcecao;

import br.edu.cesarschool.cc.poo.ac.cliente.Cliente;
import br.edu.cesarschool.cc.poo.ac.utils.Registro;
import br.edu.cesarschool.cc.poo.ac.utils.SuperDAO;

public class ClienteDAO extends SuperDAO{
    private String clienteExiste = "Cliente existente";
    private String clienteInexistente = "Cliente inexistente";

    public ClienteDAO() {
        super();
    }

    @Override
    public Class<?> obterTipo() {
        return Cliente.class;
    }

    public Cliente buscar(String cpf) throws ExcecaoRegistroInexistente {
        Cliente cliente = (Cliente) daoGenerico.buscar(cpf);
        if(cliente == null) {
            throw new ExcecaoRegistroInexistente(clienteInexistente);
        }

        return cliente;
    }

    public void incluir(Cliente cliente) throws ExcecaoRegistroJaExistente {
        if (!daoGenerico.incluir(cliente)) {
            throw new ExcecaoRegistroJaExistente(clienteExiste);
        }
    }

    public void alterar(Cliente cliente) throws ExcecaoRegistroInexistente {
        if (!daoGenerico.alterar(cliente)) {
            throw new ExcecaoRegistroInexistente(clienteInexistente);
        }
    }

    public void excluir(String cpf) throws ExcecaoRegistroInexistente {
        if (!daoGenerico.excluir(cpf)) {
            throw new ExcecaoRegistroInexistente(clienteInexistente);
        }
    }

    public Cliente[] buscarTodos() {
       Registro[] regs = daoGenerico.buscarTodos();

       if(regs == null) {
        return null;
       } else {
            Cliente[] clientes = new Cliente[regs.length];

            for(int i = 0; i < regs.length; i++) {
                clientes[i] = (Cliente) regs[i];
            }
            return clientes;
       }
    }   
}