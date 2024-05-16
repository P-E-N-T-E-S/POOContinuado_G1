package br.edu.cesarschool.cc.poo.ac.cliente;

import java.io.Serializable;
import br.edu.cesarschool.next.oo.persistenciaobjetos.CadastroObjetos;

public class ClienteDAO {
	private CadastroObjetos cadastro = new CadastroObjetos(Cliente.class);
	private String obterIdUnico(Cliente cliente) {
		return cliente.getCpf();
	}
	public Cliente buscar(String cpf) {
		return (Cliente)cadastro.buscar(cpf);
	}
	public boolean incluir(Cliente cliente) {
		String idUnico = obterIdUnico(cliente);
		Cliente cli = buscar(idUnico);
		if (cli == null) {
			cadastro.incluir(cliente, idUnico);
			return true;
		} 
		return false; 
	}
	public boolean alterar(Cliente cliente) {
		String idUnico = obterIdUnico(cliente);
		Cliente cli = buscar(idUnico);
		if (cli != null) {
			cadastro.alterar(cliente, idUnico);
			return true;
		} 
		return false;
	}
	public boolean excluir(String cpf) {
		Cliente cli = buscar(cpf);
		if (cli != null) {
			cadastro.excluir(cpf);
			return true;
		} 
		return false; 
	}	
	public Cliente[] buscarTodos() {
		Serializable[] res = cadastro.buscarTodos();
		if (res == null) {
			return null;
		} else {
			Cliente[] clientes = new Cliente[res.length];
			int i = 0;
			for (Serializable reg : res) {
				clientes[i] = (Cliente)reg;
				i++;
			}
			return clientes;
		}
	}
}
