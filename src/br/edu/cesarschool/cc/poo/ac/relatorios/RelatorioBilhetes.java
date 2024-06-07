package br.edu.cesarschool.cc.poo.ac.relatorios;

import br.edu.cesarschool.cc.poo.ac.passagem.Bilhete;
import br.edu.cesarschool.cc.poo.ac.passagem.BilheteMediator;


public class RelatorioBilhetes {
    public static void gerarRelatorioBilhetesPorPreco() {
        BilheteMediator bilheteMediator = BilheteMediator.obterInstancia();
        Bilhete[] todosBilhetes = bilheteMediator.obterBilhetesPorPreco();

        for (Bilhete bilhete : todosBilhetes) {
            System.out.println(bilhete);
        }
    }

    public static void gerarRelatorioBilhetesPorDH(double precoMin) {
        BilheteMediator bilheteMediator = BilheteMediator.obterInstancia();
        Bilhete[] bilhetes = bilheteMediator.obterBilhetesPorDataHora(precoMin);

        for (Bilhete bilhete : bilhetes) {
            System.out.println(bilhete);
        }
    }
}
