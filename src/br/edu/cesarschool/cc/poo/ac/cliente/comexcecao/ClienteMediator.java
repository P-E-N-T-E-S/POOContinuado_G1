package br.edu.cesarschool.cc.poo.ac.cliente.comexcecao;

import br.edu.cesarschool.cc.poo.ac.cliente.Cliente;
import br.edu.cesarschool.cc.poo.ac.utils.StringUtils;
import br.edu.cesarschool.cc.poo.ac.utils.ValidadorCPF;
import br.edu.cesarschool.cc.poo.ac.utils.ordenacao.Comparador;
import br.edu.cesarschool.cc.poo.ac.utils.ordenacao.Ordenadora;

public class ClienteMediator {
    private static final String CPF_ERRADO = "CPF errado";
    private static final String NOME_ERRADO = "Nome errado";
    private static ClienteMediator instancia;
    private ClienteDAO clienteDao = new ClienteDAO();

    public static ClienteMediator obterInstancia() {
        if (instancia == null) {
            instancia = new ClienteMediator();
        }
        return instancia;
    }

    private ClienteMediator() {
    }

    public void validar(Cliente cliente) throws ExcecaoValidacao {
        ExcecaoValidacao excecaoValidacao = new ExcecaoValidacao("Erro de validação:");

        if (cliente == null) {
            excecaoValidacao.adicionarMensagem("Cliente Inexistente");
        } else {
            if (!ValidadorCPF.isCpfValido(cliente.getCpf())) {
                excecaoValidacao.adicionarMensagem(CPF_ERRADO);
            }
            if (StringUtils.isVaziaOuNula(cliente.getNome()) || cliente.getNome().length() < 2) {
                excecaoValidacao.adicionarMensagem(NOME_ERRADO);
            }
            if (cliente.getSaldoPontos() < 0) {
                excecaoValidacao.adicionarMensagem("Saldo Invalido");
            }
        }

        if (excecaoValidacao.getMensagens().size() > 0) {
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

    public Cliente buscar(String cpf) throws ExcecaoRegistroInexistente {
        return clienteDao.buscar(cpf);
    }

    public void excluir(String cpf) throws ExcecaoRegistroInexistente, ExcecaoValidacao {
        if (!ValidadorCPF.isCpfValido(cpf)) {
            throw new ExcecaoValidacao(CPF_ERRADO);
        }
        clienteDao.excluir(cpf);
    }

    public Cliente[] obterClientesPorNome() {
        Cliente[] todosClientes = clienteDao.buscarTodos();
        Comparador comparadorNome = (o1, o2) -> {
            Cliente cliente1 = (Cliente) o1;
            Cliente cliente2 = (Cliente) o2;
            return cliente1.getNome().compareToIgnoreCase(cliente2.getNome());
        };
        Ordenadora.ordenar(todosClientes, comparadorNome);
        return todosClientes;
    }
}
