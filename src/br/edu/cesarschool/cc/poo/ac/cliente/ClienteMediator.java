package br.edu.cesarschool.cc.poo.ac.cliente;

import br.edu.cesarschool.cc.poo.ac.utils.StringUtils;
import br.edu.cesarschool.cc.poo.ac.utils.ValidadorCPF;

public class ClienteMediator {
    private static ClienteMediator instance;
    private ClienteDAO clienteDao = new ClienteDAO();

    private ClienteMediator() {
    }

    public static synchronized ClienteMediator getInstance() {
        if (instance == null) {
            instance = new ClienteMediator();
        }
        return instance;
    }

    public Cliente buscar(String cpf) {
        return clienteDao.buscar(cpf);
    }

    public String validar(Cliente cliente) {
        boolean cpfvalido = ValidadorCPF.isCpfValido(cliente.getCpf());
        boolean nomevalido = (StringUtils.isVaziaOuNula(cliente.getNome())) && (cliente.getNome().length() > 2);
        boolean saldovalido = cliente.getSaldoPontos() > 0;

        if(!cpfvalido){
            return "CPF errado";
        }
        if(!nomevalido){
            return "nome errado";
        }
        if(!saldovalido){
            return "saldo errado";
        }
        return null;
    }

    public String incluir(Cliente cliente) {
        String erro = validar(cliente);
        if(erro == null){
            boolean inclusao = clienteDao.incluir(cliente);
            if(inclusao){
                return null;
            }else{
                return "Cliente ja existente";
            }
        }else{
            return erro;
        }
    }

    public String alterar(Cliente cliente) {
        String erro = validar(cliente);
        if(erro == null){
            boolean alteracao = clienteDao.alterar(cliente);
            if(alteracao){
                return null;
            }else{
                return "Cliente inexistente";
            }
        }else{
            return erro;
        }
    }

    public String excluir(Cliente cliente) {
        String erro = validar(cliente);
        if(erro == null){
            boolean exclusao = clienteDao.excluir(cliente.getCpf());
            if(exclusao){
                return null;
            }else{
                return "Cliente inexistente";
            }
        }else{
            return erro;
        }
    }
}
