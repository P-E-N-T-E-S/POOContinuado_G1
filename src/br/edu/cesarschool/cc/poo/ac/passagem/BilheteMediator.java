package br.edu.cesarschool.cc.poo.ac.passagem;

import java.time.LocalDateTime;
import java.time.LocalTime;

import br.edu.cesarschool.cc.poo.ac.cliente.Cliente;
import br.edu.cesarschool.cc.poo.ac.cliente.ClienteMediator;
import br.edu.cesarschool.cc.poo.ac.negocio.comparadores.ComparadorBilheteDataHora;
import br.edu.cesarschool.cc.poo.ac.negocio.comparadores.ComparadorBilhetePreco;
import br.edu.cesarschool.cc.poo.ac.utils.ValidadorCPF;
import br.edu.cesarschool.cc.poo.ac.utils.ordenacao.Ordenadora;

public class BilheteMediator {
    private static BilheteMediator instancia;

    private BilheteDAO bilheteDao = new BilheteDAO();
    private BilheteVipDAO bilheteVipDao = new BilheteVipDAO();
    private VooMediator vooMediator = VooMediator.obterInstancia();
    private ClienteMediator clienteMediator = ClienteMediator.obterInstancia();

    private BilheteMediator() {}

    public static synchronized BilheteMediator obterInstancia() {
        if (instancia == null) {
            instancia = new BilheteMediator();
        }
        return instancia;
    }

    public Bilhete buscar(String numeroBilhete) {
        return bilheteDao.buscar(numeroBilhete);
    }

    public BilheteVip buscarVip(String numeroBilhete) {
        return bilheteVipDao.buscar(numeroBilhete);
    }

    public String validar(String cpf, String ciaAerea, int numeroVoo, double preco, double pagamentoEmPontos, LocalDateTime dataHora) {
        
        if (!ValidadorCPF.isCpfValido(cpf)) {
            return "CPF errado";
        }

        String validacaoCiaNumero = vooMediator.validarCiaNumero(ciaAerea, numeroVoo);
        if (validacaoCiaNumero != null) {
            return validacaoCiaNumero;
        }

        if (preco <= 0) {
            return "Preco errado";
        }

        if (pagamentoEmPontos < 0) {
            return "Pagamento pontos errado";
        }

        if (preco < pagamentoEmPontos) {
            return "Preco menor que pagamento em pontos";
        }

        LocalDateTime horaAtualMais1h = LocalDateTime.now().plusHours(1);
        if (dataHora.isBefore(horaAtualMais1h)) {
            return "data hora invalida";
        }

        Voo voo = vooMediator.buscar(ciaAerea + numeroVoo);
        if (voo == null) {
            return "Voo nao encontrado";
        }
        if(voo.getDiasDaSemana() != null && voo.getDiasDaSemana().length != 0){
            boolean isDiaValido = false;
            for(int i = 0; i < voo.getDiasDaSemana().length; i ++) {
                if(dataHora.getDayOfWeek().getValue() == voo.getDiasDaSemana()[i].getCodigo()) {
                    isDiaValido = true;
                    break;
                }
            }

            if(!isDiaValido) {
                return "Voo nao disponivel na data";
            }
        }

        if(voo.getHora() != null) {
            LocalTime horaVoo = voo.getHora();
            LocalTime horaDataHora = LocalTime.of(dataHora.getHour(), dataHora.getMinute());

            if (!horaVoo.equals(horaDataHora)) {
                return "Hora diferente da especificada no voo";
            }
        }

        return null;
    }

    public ResultadoGeracaoBilhete gerarBilhete(String cpf, String ciaAerea, int numeroVoo, double preco, double pagamentoEmPontos, LocalDateTime dataHora) {
        String validacao = validar(cpf, ciaAerea, numeroVoo, preco, pagamentoEmPontos, dataHora);
        if (validacao != null) {
            return new ResultadoGeracaoBilhete(null, null, validacao);
        } 
        else {
            Voo voo = new Voo(null, null, ciaAerea, numeroVoo);
            String idVoo = voo.obterIdVoo();
            Voo vooEncontrado = vooMediator.buscar(idVoo);
            if (vooEncontrado == null) {
                return new ResultadoGeracaoBilhete(null, null, "Voo nao encontrado");
            }
            
            Cliente cliente = clienteMediator.buscar(cpf);
            if (cliente == null) {
                return new ResultadoGeracaoBilhete(null, null, "Cliente nao encontrado");
            }

            double valorNecessarioPontos = pagamentoEmPontos * 20;
            if (cliente.getSaldoPontos() < valorNecessarioPontos) {
                return new ResultadoGeracaoBilhete(null, null, "Pontos insuficientes");
            }

            Bilhete bilhete = new Bilhete(cliente, vooEncontrado, preco, pagamentoEmPontos, dataHora);
            cliente.debitarPontos(valorNecessarioPontos);
            cliente.creditarPontos(bilhete.getPagamentoEmPontos());
            boolean incluirBilhete = bilheteDao.incluir(bilhete);
            if (!incluirBilhete) {
                return new ResultadoGeracaoBilhete(null, null, "Bilhete ja existente");
            }
            String alterarClienteMsg = clienteMediator.alterar(cliente);
            if (alterarClienteMsg != null) {
                return new ResultadoGeracaoBilhete(null, null, alterarClienteMsg);
            }
            return new ResultadoGeracaoBilhete(bilhete, null, null);
            
        }

    }

    public ResultadoGeracaoBilhete gerarBilheteVip(String cpf, String ciaAerea, int numeroVoo, double preco, double pagamentoEmPontos, LocalDateTime dataHora, double bonusPontuacao) {
        String validacao = validar(cpf, ciaAerea, numeroVoo, preco, pagamentoEmPontos, dataHora);
        if (validacao != null) {
            return new ResultadoGeracaoBilhete(null, null, validacao);
        } else {
            if (bonusPontuacao <= 0 || bonusPontuacao > 100) {
                return new ResultadoGeracaoBilhete(null, null, "Bonus errado");
            }
            
            Voo voo = new Voo(null, null, ciaAerea, numeroVoo);
            String idVoo = voo.obterIdVoo();
            Voo vooEncontrado = vooMediator.buscar(idVoo);
            if (vooEncontrado == null) {
                return new ResultadoGeracaoBilhete(null, null, "Voo nao encontrado");
            }
            
            Cliente cliente = clienteMediator.buscar(cpf);
            if (cliente == null) {
                return new ResultadoGeracaoBilhete(null, null, "Cliente nao encontrado");
            }

            double valorNecessarioPontos = pagamentoEmPontos * 20;
            if (cliente.getSaldoPontos() < valorNecessarioPontos) {
                return new ResultadoGeracaoBilhete(null, null, "Pontos insuficientes");
            }

            BilheteVip bilheteVip = new BilheteVip(cliente, vooEncontrado, preco, pagamentoEmPontos, dataHora, bonusPontuacao);
            cliente.debitarPontos(valorNecessarioPontos);
            cliente.creditarPontos(bilheteVip.obterValorPontuacaoVip());
            boolean incluirBilheteVip = bilheteVipDao.incluir(bilheteVip);
            if (!incluirBilheteVip) {
                return new ResultadoGeracaoBilhete(null, null, "Bilhete ja existente");
            }
            String alterarClienteMsg = clienteMediator.alterar(cliente);
            if (alterarClienteMsg != null) {
                return new ResultadoGeracaoBilhete(null, null, alterarClienteMsg);
            }
            return new ResultadoGeracaoBilhete(null, bilheteVip, null);
        }
    }

    public Bilhete[] obterBilhetesPorPreco() {
        Bilhete[] bilhetes = bilheteDao.buscarTodos();

        Ordenadora.ordenar(bilhetes, new ComparadorBilhetePreco());
        return bilhetes;
    }

    public Bilhete[] obterBilhetesPorDataHora(double precoMin) {
        Bilhete[] bilhetes = bilheteDao.buscarTodos();

        int count = 0;
        for(int i = 0; i < bilhetes.length; i++) {
            if(bilhetes[i].getPreco() >= precoMin) {
                bilhetes[count] = bilhetes[i];
                count++;
            }
        }

        Bilhete[] bilhetesAtualizados = new Bilhete[count];
        for(int i = 0; i < count; i ++) {
            bilhetesAtualizados[i] = bilhetes[i];
        }

        Ordenadora.ordenar(bilhetesAtualizados, new ComparadorBilheteDataHora());
        return bilhetesAtualizados;
    }
}

