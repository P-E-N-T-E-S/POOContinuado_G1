package br.edu.cesarschool.cc.poo.ac.cliente;

import br.edu.cesarschool.cc.poo.ac.utils.StringUtils;
import br.edu.cesarschool.cc.poo.ac.utils.ValidadorCPF;
import br.edu.cesarschool.cc.poo.ac.utils.ordenacao.Comparador;
import br.edu.cesarschool.cc.poo.ac.utils.ordenacao.Ordenadora;

public class ClienteMediator {
    private static ClienteMediator instancia;
    private ClienteDAO clienteDao = new ClienteDAO();

    private ClienteMediator() {}

    public static ClienteMediator obterInstancia() {
        if (instancia == null) {
            instancia = new ClienteMediator();
        }
        return instancia;
    }

    public Cliente buscar(String cpf) {
        return clienteDao.buscar(cpf);
    }

    public String validar(Cliente cliente) {
        String mensagem = "";

        if(!ValidadorCPF.isCpfValido(cliente.getCpf())) {
            mensagem += "CPF errado";
        }

        if(StringUtils.isVaziaOuNula(cliente.getNome()) || cliente.getNome().length() < 2) {
            mensagem += "nome errado";
        }

        if(cliente.getSaldoPontos() < 0) {
            mensagem += "saldo errado";
        }
        
        if(mensagem == "") {
            return null;
        }

        return mensagem;

    }

    public String incluir(Cliente cliente) {
        String erroValidacao = validar(cliente);
        if(erroValidacao != null) {
            return erroValidacao;
        }

        else {
            if(!clienteDao.incluir(cliente)) {
                return "Cliente ja existente";
            }
            else {
                return null;
            }
        }
    }

    public String alterar(Cliente cliente) {
        String erroValidacao = validar(cliente);
        if(erroValidacao != null) {
            return erroValidacao;
        }

        else {
            if(!clienteDao.alterar(cliente)) {
                return "Cliente inexistente";
            }
            else {
                return null;
            }
        }
    }

    public String excluir(String cpf) {
        if(!ValidadorCPF.isCpfValido(cpf)) {
            return "CPF errado";
        }

        else {
            if(!clienteDao.excluir(cpf)) {
                return "Cliente inexistente";
            }
            else {
                return null;
            }
        }
    }

    public Cliente[] obterClientesPorNome() {
        Cliente[] clientes = clienteDao.buscarTodos();
        
        Ordenadora.ordenar(clientes, new Comparador() {
            @Override
            public int comparar(Object o1, Object o2) {
                Cliente cliente = (Cliente) o1;
                Cliente cliente_aux = (Cliente) o2;

                int result = cliente.getNome().compareTo(cliente_aux.getNome());
                return result;
            }
        });
        
        return clientes;
    }

}
