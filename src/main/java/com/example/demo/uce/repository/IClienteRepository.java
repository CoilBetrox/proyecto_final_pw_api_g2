package com.example.demo.uce.repository;

import com.example.demo.uce.repository.modelo.Cliente;

public interface IClienteRepository {

	public void registrarCliente(Cliente cliente);
	
	public Cliente buscarCliente(Integer idCliente);
	
}
