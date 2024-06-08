package br.edu.cesarschool.cc.poo.ac.cliente.comexcecao;

import br.edu.cesarschool.cc.poo.ac.cliente.Cliente;
import java.util.Scanner;

public class TelaCliente {
    private ClienteMediator clienteMediator = ClienteMediator.obterInstancia();
    private static final Scanner ENTRADA = new Scanner(System.in);

    public void inicializaTelasCadastroProduto() {
        while(true){
            imprimeMenuPrincipal();
            int opcao = ENTRADA.nextInt();
            switch (opcao){
                case 1:
                    processaInclusao();
                    break;
                case 2:
                    processaAlteracao();
                    break;
                case 3:
                    processaExclusao();
                    break;
                case 4:
                    processaBusca();
                    break;
                case 5:
                    System.out.println("Saindo do cadastro de clientes");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opção inválida!!");
                    break;
            }
        }
    }

    public void imprimeMenuPrincipal() {
        System.out.println("1- Incluir");
        System.out.println("2- Alterar");
        System.out.println("3- Excluir");
        System.out.println("4- Buscar");
        System.out.println("5- Sair");
        System.out.print("Digite a opção: ");
    }

    public void processaInclusao() {
        try {
            System.out.println("Insira o cpf do cliente: ");
            String cpf = ENTRADA.next();
            System.out.println("Insira o nome do cliente: ");
            String nome = ENTRADA.next();
            System.out.println("Insira o saldo do cliente: ");
            double saldo = ENTRADA.nextDouble();
            Cliente cliente = new Cliente(cpf, nome, saldo);
            clienteMediator.incluir(cliente);
            System.out.println("Cliente incluído com sucesso!");
        } catch (ExcecaoRegistroJaExistente e) {
            System.out.println(e.getMessage());
        } catch (ExcecaoValidacao e) {
            for (String mensagem : e.getMensagens()) {
                System.out.println(mensagem);
            }
        }
    }

    public void processaAlteracao() {
        try {
            System.out.println("Digite o cpf do cliente que deseja alterar: ");
            String cpf = ENTRADA.next();
            Cliente cliente = clienteMediator.buscar(cpf);
            if (cliente == null) {
                System.out.println("Cliente não encontrado.");
                return;
            }
            System.out.println("Insira o novo nome do cliente: ");
            String nome = ENTRADA.next();
            System.out.println("Insira o novo saldo do cliente: ");
            double saldo = ENTRADA.nextDouble();
            cliente.setNome(nome);
            cliente.setSaldoPontos(saldo);
            clienteMediator.alterar(cliente);
            System.out.println("Cliente alterado com sucesso!");
        } catch (ExcecaoRegistroInexistente e) {
            System.out.println(e.getMessage());
        } catch (ExcecaoValidacao e) {
            for (String mensagem : e.getMensagens()) {
                System.out.println(mensagem);
            }
        }
    }

    public void processaExclusao() {
        try {
            System.out.println("Digite o cpf do cliente que deseja excluir: ");
            String cpf = ENTRADA.next();
            clienteMediator.excluir(cpf);
            System.out.println("Cliente excluído com sucesso!");
        } catch (ExcecaoRegistroInexistente e) {
            System.out.println(e.getMessage());
        } catch (ExcecaoValidacao e) {
            for (String mensagem : e.getMensagens()) {
                System.out.println(mensagem);
            }
        }
    }

    public void processaBusca() {
        try {
            System.out.println("Digite o cpf do cliente que deseja buscar: ");
            String cpf = ENTRADA.next();
            Cliente cliente = clienteMediator.buscar(cpf);
            if (cliente == null) {
                System.out.println("Cliente não encontrado.");
            } else {
                System.out.println("Nome: " + cliente.getNome());
                System.out.println("CPF: " + cliente.getCpf());
                System.out.println("Saldo: " + cliente.getSaldoPontos());
            }
        } catch (ExcecaoRegistroInexistente e) {
            System.out.println(e.getMessage());
        }
    }
}
