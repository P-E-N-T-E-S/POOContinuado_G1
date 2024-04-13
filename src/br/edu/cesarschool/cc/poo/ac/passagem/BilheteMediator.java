package br.edu.cesarschool.cc.poo.ac.passagem;

import br.edu.cesarschool.cc.poo.ac.cliente.Cliente;
import br.edu.cesarschool.cc.poo.ac.cliente.ClienteMediator;
import br.edu.cesarschool.cc.poo.ac.utils.ValidadorCPF;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class BilheteMediator {
    private BilheteDAO bilheteDAO = new BilheteDAO();
    private BilheteVipDAO bilheteVipDAO = new BilheteVipDAO();
    private VooMediator vooMediator = VooMediator.getInstance();
    private ClienteMediator clienteMediator = ClienteMediator.getInstance();

    private BilheteMediator() {
    }

    public Bilhete buscar(String numeroBilhete) {
        return bilheteDAO.buscar(numeroBilhete);
    }

    public BilheteVip buscarVip(String numeroVip) {
        return bilheteVipDAO.buscar(numeroVip);
    }

    public String validar(String cpf, String ciaAerea, int numeroVoo, double preco, double pagamentoEmPontos, LocalDateTime dataHora) {
        VooMediator vooMediator = VooMediator.getInstance();
        ClienteMediator clienteMediator = ClienteMediator.getInstance();
        LocalDateTime agora = LocalDateTime.now();

        boolean validacaoCPF = ValidadorCPF.isCpfValido(cpf);
        String validacaoNumeroVoo = vooMediator.validarCiaNumero(ciaAerea, numeroVoo);
        boolean validacaoPreco = preco > 0;
        boolean validacaoPagamentoEmPontos = pagamentoEmPontos > 0;
        boolean validacaoPrecoEPontos = preco >= pagamentoEmPontos;
        boolean validacaoDataHora = (agora.until(dataHora, ChronoUnit.HOURS) >= 1);

        if (!validacaoCPF) {
            return "CPF errado";
        }
        if (validacaoNumeroVoo != null) {
            return validacaoNumeroVoo;
        }
        if (!validacaoPreco) {
            return "Preco errado";
        }
        if (!validacaoPagamentoEmPontos) {
            return "Pagamento pontos errado";
        }
        if (!validacaoPrecoEPontos) {
            return "Preco menor que pagamento em pontos errado";
        }
        if (!validacaoDataHora) {
            return "Data hora errado";
        }
        return null;

    }
}
