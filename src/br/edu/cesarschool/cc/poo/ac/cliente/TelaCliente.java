package br.edu.cesarschool.cc.poo.ac.cliente;

import java.util.Scanner;

public class TelaCliente {
    private ClienteMediator clienteMediator = ClienteMediator.obterInstancia();

    Scanner scanner = new Scanner(System.in);

    public TelaCliente() {
        // Construtor padrão
    }

    public void exibirMenuPrincipal() {
        System.out.println("Menu Principal");
        System.out.println("1. Incluir");
        System.out.println("2. Alterar");
        System.out.println("3. Excluir");
        System.out.println("4. Buscar");
        System.out.println("5. Sair");
    }

    public void iniciar() {
        boolean rodar = true;
        while (rodar) {
            exibirMenuPrincipal();
            int opcao = lerOpcao();
            switch (opcao) {
                case 1:
                    incluirCliente();
                    break;
                case 2:
                    alterarCliente();
                    break;
                case 3:
                    excluirCliente();
                    break;
                case 4:
                    buscarCliente();
                    break;
                case 5:
                    rodar = false;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        }
    }

    public int lerOpcao() {
        int opcao = scanner.nextInt();
        scanner.nextLine();
        return opcao;
    }

    public void incluirCliente() {
        // Implementar a lógica de inclusão de cliente
        System.out.println("Digite o nome do cliente:");
        String nome = scanner.nextLine();
        System.out.println("Digite o CPF do cliente:");
        String cpf = scanner.next();
        System.out.println("Digite o saldo de pontos do cliente:");
        double saldoPontos = scanner.nextDouble();
        scanner.nextLine();
        
        Cliente cliente = new Cliente(cpf, nome, saldoPontos);
        String mensagem = clienteMediator.incluir(cliente);
        
        if (mensagem != null) {
            System.out.println(mensagem);
        } else {
            System.out.println("Cliente incluído com sucesso");
        }
        
    }

    public void alterarCliente() {
        // Implementar a lógica de alteração de cliente
        System.out.println("Digite o CPF do cliente:");
        String cpf = scanner.next();
        scanner.nextLine();

        Cliente cliente = clienteMediator.buscar(cpf);

        if (cliente == null) {
            System.out.println("Cliente não encontrado");
        } else {
            System.out.println("Dados do cliente:");
            System.out.println("Nome: " + cliente.getNome());
            System.out.println("CPF: " + cliente.getCpf());
            System.out.println("Saldo de Pontos: " + cliente.getSaldoPontos());

            System.out.println("Digite o novo nome do cliente:");
            String novoNome = scanner.nextLine();
            System.out.println("Digite o novo saldo do cliente:");
            double novoSaldo = scanner.nextDouble();
            scanner.nextLine();

            Cliente clienteAlterado = new Cliente(cpf, novoNome, novoSaldo);
            String mensagem = clienteMediator.alterar(clienteAlterado);

            if (mensagem != null) {
            System.out.println(mensagem);
            } else {
            System.out.println("Cliente alterado com sucesso");
            }
        }
        
    }

    public void excluirCliente() {
        // Implementar a lógica de exclusão de cliente
        System.out.println("Digite o CPF do cliente:");
        String cpf = scanner.next();

        Cliente cliente = clienteMediator.buscar(cpf);

        if (cliente == null) {
            System.out.println("Cliente não encontrado");
        } else {
            System.out.println("Dados do cliente:");
            System.out.println("Nome: " + cliente.getNome());
            System.out.println("CPF: " + cliente.getCpf());
            System.out.println("Saldo de pontos: " + cliente.getSaldoPontos());

            String mensagem = clienteMediator.excluir(cliente.getCpf());

            if (mensagem != null) {
                System.out.println(mensagem);
            } else {
                System.out.println("Cliente excluído com sucesso");
            }
        }

        
    }

    public void buscarCliente() {
        // Implementar a lógica de busca de cliente
 
        System.out.println("Digite o CPF do cliente:");
        String cpf = scanner.next();

        Cliente cliente = clienteMediator.buscar(cpf);

        if (cliente == null) {
            System.out.println("Cliente não encontrado");
        } else {
            System.out.println("Dados do cliente:");
            System.out.println("Nome: " + cliente.getNome());
            System.out.println("CPF: " + cliente.getCpf());
            System.out.println("Saldo de pontos: " + cliente.getSaldoPontos());
        }

        
    }
}