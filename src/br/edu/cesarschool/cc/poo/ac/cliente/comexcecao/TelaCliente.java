package br.edu.cesarschool.cc.poo.ac.cliente.comexcecao;

import java.util.Scanner;

import br.edu.cesarschool.cc.poo.ac.cliente.Cliente;

public class TelaCliente {
    private ClienteMediator clienteMediator = new ClienteMediator();

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
        System.out.println("Digite o nome do cliente:");
        String nome = scanner.nextLine();
        System.out.println("Digite o CPF do cliente:");
        String cpf = scanner.next();
        System.out.println("Digite o saldo de pontos do cliente:");
        double saldoPontos = scanner.nextDouble();
        scanner.nextLine();
        
        Cliente cliente = new Cliente(cpf, nome, saldoPontos);

        try {
            clienteMediator.incluir(cliente);
            System.out.println("Cliente incluído com sucesso");
        } 
        catch(ExcecaoValidacao excecaoValidacao) {
            for(int i = 0; i < excecaoValidacao.getMensagens().size(); i++) {
                String mensagem = excecaoValidacao.getMensagens().get(i);
                System.out.println(mensagem);
            }
        } 
        catch(ExcecaoRegistroJaExistente excecaoRegistroJaExistente) {
            System.out.println(excecaoRegistroJaExistente.getMessage());
        }
        
    }

    public void alterarCliente() {
        System.out.println("Digite o CPF do cliente:");
        String cpf = scanner.next();
        scanner.nextLine();

        try {
            Cliente cliente = clienteMediator.buscar(cpf);

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
            clienteMediator.alterar(clienteAlterado);
            System.out.println("Cliente alterado com sucesso");
        } catch(ExcecaoRegistroInexistente eri) {
            System.out.println(eri.getMessage());
        } catch(ExcecaoValidacao e) {
            for(int i = 0; i < e.getMensagens().size(); i++) {
                String mensagem = e.getMensagens().get(i);
                System.out.println(mensagem);
            }
        }
        
    }

    public void excluirCliente() {
        System.out.println("Digite o CPF do cliente:");
        String cpf = scanner.next();

        //Cliente cliente = clienteMediator.buscar(cpf);

        try {
            clienteMediator.excluir(cpf);
            System.out.println("Cliente excluído com sucesso");
        }
        catch(ExcecaoRegistroInexistente e) {
            System.out.println(e.getMessage());
        }
        catch(ExcecaoValidacao e) {
            for(int i = 0; i < e.getMensagens().size(); i++) {
                String mensagem = e.getMensagens().get(i);
                System.out.println(mensagem);
            }
        }
    }

    public void buscarCliente() {
        System.out.println("Digite o CPF do cliente:");
        String cpf = scanner.next();

        try {
            Cliente cliente = clienteMediator.buscar(cpf);
            System.out.println("Dados do cliente:");
            System.out.println("Nome: " + cliente.getNome());
            System.out.println("CPF: " + cliente.getCpf());
            System.out.println("Saldo de pontos: " + cliente.getSaldoPontos());
        }
        catch(ExcecaoRegistroInexistente e) {
            System.out.println(e.getMessage());
        }
    }
}