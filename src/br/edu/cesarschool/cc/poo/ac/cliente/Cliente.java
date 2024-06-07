package br.edu.cesarschool.cc.poo.ac.cliente;

import br.edu.cesarschool.cc.poo.ac.utils.ordenacao.Comparavel;
import br.edu.cesarschool.cc.poo.ac.utils.Registro;

public class Cliente extends Registro implements Comparavel {
	private String cpf;
	private String nome;
	private double saldoPontos;

	public Cliente(String cpf, String nome, double saldoPontos) {
		this.cpf = cpf;
		this.nome = nome;
		this.saldoPontos = saldoPontos;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public double getSaldoPontos() {
		return saldoPontos;
	}

	public void creditarPontos(double valor) {
		saldoPontos += valor;
	}

	public void debitarPontos(double valor) {
		saldoPontos -= valor;
	}

	@Override
	public String getIdUnico() {
		return getCpf();
	}

	@Override
	public int comparar(Object o) {
		Cliente outro = (Cliente) o;
		return this.nome.compareTo(outro.getNome());
	}

	@Override
	public String toString() {
		return  nome + cpf + saldoPontos;
	}
}
