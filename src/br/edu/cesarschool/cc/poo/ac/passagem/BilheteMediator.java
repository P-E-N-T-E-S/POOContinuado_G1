package br.edu.cesarschool.cc.poo.ac.passagem;

import br.edu.cesarschool.cc.poo.ac.cliente.Cliente;
import br.edu.cesarschool.cc.poo.ac.cliente.ClienteMediator;
import br.edu.cesarschool.cc.poo.ac.utils.ValidadorCPF;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class BilheteMediator {
    private static BilheteMediator instance;
    private BilheteDAO bilheteDAO = new BilheteDAO();
    private BilheteVipDAO bilheteVipDAO = new BilheteVipDAO();
    private VooMediator vooMediator = VooMediator.obterInstancia();
    private ClienteMediator clienteMediator = ClienteMediator.obterInstancia();

    private BilheteMediator() {
    }

    public static synchronized BilheteMediator obterInstancia() {
        if (instance == null) {
            instance = new BilheteMediator();
        }
        return instance;
    }

    public Bilhete buscar(String numeroBilhete) {
        return bilheteDAO.buscar(numeroBilhete);
    }

    public BilheteVip buscarVip(String numeroVip) {
        return bilheteVipDAO.buscar(numeroVip);
    }

    public String validar(String cpf, String ciaAerea, int numeroVoo, double preco, double pagamentoEmPontos, LocalDateTime dataHora) {
        VooMediator vooMediator = VooMediator.obterInstancia();
        ClienteMediator clienteMediator = ClienteMediator.obterInstancia();
        LocalDateTime agora = LocalDateTime.now();

        boolean validacaoCPF = ValidadorCPF.isCpfValido(cpf);

        if (!validacaoCPF) {
            return "CPF errado";
        }

        String validacaoNumeroVoo = vooMediator.validarCiaNumero(ciaAerea, numeroVoo);

        if (validacaoNumeroVoo != null) {
            return validacaoNumeroVoo;
        }

        boolean validacaoPreco = preco > 0;

        if (!validacaoPreco) {
            return "Preco errado";
        }

        boolean validacaoPagamentoEmPontos = pagamentoEmPontos >= 0;

        if (!validacaoPagamentoEmPontos) {
            return "Pagamento pontos errado";
        }

        boolean validacaoPrecoEPontos = preco >= pagamentoEmPontos;

        if (!validacaoPrecoEPontos) {
            return "Preco menor que pagamento em pontos";
        }

        boolean validacaoDataHora = (agora.until(dataHora, ChronoUnit.HOURS) >= 1);

        if (!validacaoDataHora) {
            return "data hora invalida";
        }
        return null;
    }

    public ResultadoGeracaoBilhete gerarBilhete(String cpf, String ciaAerea, int numeroVoo, double preco, double pagamentoEmPontos, LocalDateTime dataHora) {
        String validacao = validar(cpf, ciaAerea, numeroVoo, preco, pagamentoEmPontos, dataHora);

        if (validacao != null) {
            return new ResultadoGeracaoBilhete(null, null, validacao);
        }
        Voo voo = new Voo(null, null, ciaAerea, numeroVoo);
        String idVoo = voo.obterIdVoo();
        Voo vooBuscado = vooMediator.buscar(idVoo);

        if (vooBuscado == null) {
            return new ResultadoGeracaoBilhete(null, null, "Voo nao encontrado");
        }

        Cliente cliente = clienteMediator.buscar(cpf);

        if (cliente == null) {
            return new ResultadoGeracaoBilhete(null, null, "Cliente nao encontrado");
        }

        double valorPontosNecessarios = pagamentoEmPontos * 20;

        if (cliente.getSaldoPontos() < valorPontosNecessarios) {
            return new ResultadoGeracaoBilhete(null, null, "Pontos insuficientes");
        }

        Bilhete bilhete = new Bilhete(cliente, vooBuscado, preco, pagamentoEmPontos, dataHora);
        cliente.debitarPontos(valorPontosNecessarios);
        cliente.creditarPontos(bilhete.obterValorPontuacao());

        if (!bilheteDAO.incluir(bilhete)) {
            return new ResultadoGeracaoBilhete(null, null, "Bilhete ja existente");
        }

        if (clienteMediator.alterar(cliente) != null) {
            return new ResultadoGeracaoBilhete(null, null, "Erro ao atualizar cliente");
        }

        return new ResultadoGeracaoBilhete(bilhete, null, null);
    }

    public ResultadoGeracaoBilhete gerarBilheteVip(String cpf, String ciaAerea, int numeroVoo, double preco, double pagamentoEmPontos, LocalDateTime dataHora, double bonusPontuacao) {
        String validacao = validar(cpf, ciaAerea, numeroVoo, preco, pagamentoEmPontos, dataHora);

        if (validacao != null) {
            return new ResultadoGeracaoBilhete(null, null, validacao);
        }

        if(bonusPontuacao > 100 || bonusPontuacao < 0){
            return new ResultadoGeracaoBilhete(null, null, "Bonus errado");
        }

        Voo voo = new Voo(null, null, ciaAerea, numeroVoo);
        String idVoo = voo.obterIdVoo();
        Voo vooBuscado = vooMediator.buscar(idVoo);

        if (vooBuscado == null) {
            return new ResultadoGeracaoBilhete(null, null, "Voo nao encontrado");
        }

        Cliente cliente = clienteMediator.buscar(cpf);

        if (cliente == null) {
            return new ResultadoGeracaoBilhete(null, null, "Cliente nao encontrado");
        }

        double valorPontosNecessarios = pagamentoEmPontos * 20;

        if (cliente.getSaldoPontos() < valorPontosNecessarios) {
            return new ResultadoGeracaoBilhete(null, null, "Pontos insuficientes");
        }

        BilheteVip bilheteVip = new BilheteVip(cliente, vooBuscado, preco, pagamentoEmPontos, dataHora, bonusPontuacao);
        cliente.debitarPontos(valorPontosNecessarios);
        cliente.creditarPontos(bilheteVip.obterValorPontuacaoVip());

        if (!bilheteVipDAO.incluir(bilheteVip)) {
            return new ResultadoGeracaoBilhete(null, null, "Bilhete vip ja existente");
        }

        if (clienteMediator.alterar(cliente) != null) {
            return new ResultadoGeracaoBilhete(null, null, "Erro ao atualizar cliente");
        }

        return new ResultadoGeracaoBilhete(null, bilheteVip, null);
    }
}