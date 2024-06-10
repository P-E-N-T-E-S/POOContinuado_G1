package br.edu.cesarschool.cc.poo.ac.cliente.comexcecao;

import br.edu.cesarschool.cc.poo.ac.cliente.Cliente;
import br.edu.cesarschool.cc.poo.ac.utils.StringUtils;
import br.edu.cesarschool.cc.poo.ac.utils.ValidadorCPF;
import br.edu.cesarschool.cc.poo.ac.utils.ordenacao.Comparador;
import br.edu.cesarschool.cc.poo.ac.utils.ordenacao.Ordenadora;

public class ClienteMediator {
    private static ClienteMediator instancia;
    private ClienteDAO clienteDao = new ClienteDAO();

    public ClienteMediator() {}

    public static ClienteMediator obterInstancia() {
        if (instancia == null) {
            instancia = new ClienteMediator();
        }
        return instancia;
    }

    public Cliente buscar(String cpf) throws ExcecaoRegistroInexistente {
        return clienteDao.buscar(cpf);
    }

    public void validar(Cliente cliente) throws ExcecaoValidacao {
        ExcecaoValidacao excecaoValidacao = new ExcecaoValidacao();

        if(!ValidadorCPF.isCpfValido(cliente.getCpf())) {
            excecaoValidacao.adicionarMensagem("CPF errado");
        }

        if(StringUtils.isVaziaOuNula(cliente.getNome()) || cliente.getNome().length() < 2) {
            excecaoValidacao.adicionarMensagem("nome errado");
        }

        if(cliente.getSaldoPontos() < 0) {
            excecaoValidacao.adicionarMensagem("saldo errado");
        }
        
        if(!excecaoValidacao.getMensagens().isEmpty()) {
            throw excecaoValidacao;
        }

    }

    public void incluir(Cliente cliente) throws ExcecaoRegistroJaExistente, ExcecaoValidacao {
        validar(cliente);
        clienteDao.incluir(cliente);
    }

    public void alterar(Cliente cliente) throws ExcecaoRegistroInexistente, ExcecaoValidacao {
        validar(cliente);
        clienteDao.alterar(cliente);
    }

    public void excluir(String cpf) throws ExcecaoRegistroInexistente, ExcecaoValidacao {
        if(!ValidadorCPF.isCpfValido(cpf)) {
            ExcecaoValidacao excecaoValidacao = new ExcecaoValidacao();
            excecaoValidacao.adicionarMensagem("CPF errado");
            throw excecaoValidacao;
        }
        clienteDao.excluir(cpf);
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
