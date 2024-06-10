package br.edu.cesarschool.cc.poo.ac.passagem;

import br.edu.cesarschool.cc.poo.ac.utils.StringUtils;
import java.util.Set;

public class VooMediator {
    private static final Set<String> AEROPORTOS_VALIDOS = Set.of(
        "GRU", "CGH", "GIG", "SDU", "REC", "CWB", "POA", "BSB", "SSA", "FOR",
        "MAO", "SLZ", "CNF", "BEL", "JPA", "PNZ", "CAU", "FEN", "SET", "NAT",
        "PVH", "BVB", "FLN", "AJU", "PMW", "MCZ", "MCP", "VIX", "GYN", "CGB",
        "CGR", "THE", "RBR", "VCP", "RAO"
    );

    private static VooMediator instancia;
    private VooDAO vooDao = new VooDAO();

    private VooMediator() {}

    public static VooMediator obterInstancia() {
        if (instancia == null) {
            instancia = new VooMediator();
        }
        return instancia;
    } 

    public Voo buscar(String IdVoo) {
        return vooDao.buscar(IdVoo);
    }

    public String validarCiaNumero(String companhiaAerea, int numeroVoo) {
        String mensagem = null;

        if(StringUtils.isVaziaOuNula(companhiaAerea) || companhiaAerea.length() !=2) {
            mensagem = "CIA aerea errada";
            return mensagem;
        }

        if(numeroVoo <= 0 || (numeroVoo < 1000 || numeroVoo > 9999)) {
            mensagem = "Numero voo errado";
            return mensagem;
        }

        return mensagem;
    }

    public String validar(Voo voo) {
        String mensagem = null;

        if(StringUtils.isVaziaOuNula(voo.getAeroportoOrigem()) || 
        !AEROPORTOS_VALIDOS.contains(voo.getAeroportoOrigem())) {
            mensagem = "Aeroporto origem errado";
            return mensagem;
        }

        if(StringUtils.isVaziaOuNula(voo.getAeroportoDestino()) || 
        !AEROPORTOS_VALIDOS.contains(voo.getAeroportoDestino())) {
            mensagem = "Aeroporto destino errado";
            return mensagem;
        }
        
	    if (voo.getAeroportoOrigem().equals(voo.getAeroportoDestino())) {
	        mensagem = "Aeroporto origem igual a aeroporto destino";
	        return mensagem;
	    }

        if(voo.getDiasDaSemana() == null || voo.getDiasDaSemana().length == 0) {
            mensagem = "Dias da semana nao informados";
            return mensagem;
        }

        for(int i = 0; i < voo.getDiasDaSemana().length; i++) {
            for(int j = i + 1; j < voo.getDiasDaSemana().length; j++) {
                if(voo.getDiasDaSemana()[i] == voo.getDiasDaSemana()[j]) {
                    mensagem = "Dia da semana repetido";
                    return mensagem;
                }
            }
        }

        if(voo.getHora() == null) {
            mensagem = "Hora nao informada";
            return mensagem;
        }

        if(voo.getHora().getSecond() != 0 || voo.getHora().getNano() != 0) {
            mensagem = "Hora invalida";
            return mensagem;
        }
        
        mensagem = validarCiaNumero(voo.getCompanhiaAerea(), voo.getNumeroVoo());

        if(mensagem == null) {
            return null;
        }
        else {
            return mensagem;
        }
    }

    public String incluir(Voo voo) {
        String erroValidacao = validar(voo);
        if(erroValidacao != null) {
            return erroValidacao;
        }

        else {
            if(!vooDao.incluir(voo)) {
                return "Voo ja existente";
            }
            else {
                return null;
            }
        }
    }

    public String alterar(Voo voo) {
        String erroValidacao = validar(voo);
        if(erroValidacao != null) {
            return erroValidacao;
        }

        else {
            if(!vooDao.alterar(voo)) {
                return "Voo inexistente";
            }
            else {
                return null;
            }
        }
    }

    public String excluir(String idVoo) {
        if(StringUtils.isVaziaOuNula(idVoo)) {
            return "Id voo errado";
        }
        else {
            if(!vooDao.excluir(idVoo)) {
                return "Voo inexistente";
            }
            else { 
                return null;
            }
        }
    }

    public Voo[] buscarTodos() {
        return vooDao.buscarTodos();
    }

}