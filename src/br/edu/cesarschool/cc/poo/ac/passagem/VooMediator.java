package br.edu.cesarschool.cc.poo.ac.passagem;

import br.edu.cesarschool.cc.poo.ac.cliente.ClienteMediator;
import br.edu.cesarschool.cc.poo.ac.utils.StringUtils;

import java.util.ArrayList;

public class VooMediator {
    private static VooMediator instance;
    private VooDAO vooDAO = new VooDAO();

    private String[] aeroportosValidos = {"GRU", "CGH", "GIG", "SDU", "REC", "CWB", "POA", "BSB", "SSA",
            "FOR", "MAO", "SLZ", "CNF", "BEL", "JPA", "PNZ", "CAU", "FEN", "SET", "NAT", "PVH", "BVB",
            "FLN", "AJU", "PMW", "MCZ", "MCP", "VIX", "GYN", "CGB", "CGR", "THE", "RBR", "VCP", "RAO"};

    private boolean validarAeroporto(String aeroporto){
        for (String aeroportoValido : aeroportosValidos){
            if (aeroportoValido.equals(aeroporto)){
                return true;
            }
        }
        return false;
    }

    private VooMediator() {
    }

    public static synchronized VooMediator obterInstancia() {
        if (instance == null) {
            instance = new VooMediator();
        }
        return instance;
    }

    public Voo buscar(String voo){
        return vooDAO.buscar(voo);
    }

    public String validarCiaNumero(String companhiaAerea, int numeroVoo){
        boolean validacaoCia = (StringUtils.isVaziaOuNula(companhiaAerea)) && (companhiaAerea.length() == 2);

        if(!validacaoCia){
            return "CIA aerea errada";
        }

        boolean validacaoNumero = (numeroVoo >= 1000) && (numeroVoo <= 9999);

        if(!validacaoNumero){
            return "Numero voo errado";
        }

        return null;
    }

    public String validar(Voo voo){
        boolean validarAeroportoOrigem = StringUtils.isVaziaOuNula(voo.getAeroportoOrigem())
                && validarAeroporto(voo.getAeroportoOrigem());

        if(!validarAeroportoOrigem){
            return "Aeroporto origem errado";
        }

        boolean validarAeroportoDestino = StringUtils.isVaziaOuNula(voo.getAeroportoDestino())
                && validarAeroporto(voo.getAeroportoDestino());

        if(!validarAeroportoDestino){
            return "Aeroporto destino errado";
        }
        boolean validarAeroportoOrigemEDestino = voo.getAeroportoDestino().equals(voo.getAeroportoOrigem());

        if(validarAeroportoOrigemEDestino){
            return "Aeroporto origem igual a aeroporto destino";
        }

        String erro = validarCiaNumero(voo.getCompanhiaAerea(), voo.getNumeroVoo());

        if(erro != null){
            return erro;
        }

        return null;
    }

    public String incluir(Voo voo){
        String erro = validar(voo);
        if(erro == null){
            boolean verificacao = vooDAO.incluir(voo);
            if(verificacao){
                return null;
            }else{
                return "Voo ja existente";
            }
        }else{
            return erro;
        }
    }

    public String alterar(Voo voo){
        String erro = validar(voo);
        if(erro == null){
            boolean verificacao = vooDAO.alterar(voo);
            if(verificacao){
                return null;
            }else{
                return "Voo inexistente";
            }
        }else{
            return erro;
        }
    }

    public String excluir(String idvoo){
        Voo voo = buscar(idvoo);
        if(voo == null){
            return "Voo inexistente";
        }
        String erro = validar(voo);
        if(erro == null){
            boolean verificacao = vooDAO.excluir(voo.obterIdVoo());
            if(verificacao){
                return null;
            }else{
                return "Voo inexistente";
            }
        }else{
            return erro;
        }
    }
}
