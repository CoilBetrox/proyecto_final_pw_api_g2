package com.example.demo.uce.service;

import com.example.demo.uce.repository.modelo.Cliente;
import com.example.demo.uce.service.to.ClienteTo;

public interface IClienteService {

	public void registrarCliente(Cliente cliente);
	
	public Cliente buscarClienteCedula(String idCliente);
	
}
