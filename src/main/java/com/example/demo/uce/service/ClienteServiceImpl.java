package com.example.demo.uce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.uce.repository.ClienteRepositoryImpl;
import com.example.demo.uce.repository.modelo.Cliente;
import com.example.demo.uce.service.to.ClienteTo;

@Service
public class ClienteServiceImpl implements IClienteService {

	@Autowired
	private ClienteRepositoryImpl clienteRepository;
	
	@Override
	public void registrarCliente(Cliente cliente) {
		this.clienteRepository.registrarCliente(cliente);
	}

	@Override
	public Cliente buscarCliente(Integer idCliente) {
		return this.clienteRepository.buscarCliente(idCliente);
	}

	private ClienteTo convertir(Cliente cliente) {
		ClienteTo clTo = new ClienteTo();
		clTo.setCedula(cliente.getCedula());
		clTo.setNombre(cliente.getNombre());
		clTo.setApellido(cliente.getApellido());
		clTo.setFechaNacimiento(cliente.getFechaNacimiento());
		clTo.setGenero(cliente.getGenero());
		clTo.setTipoRegistro(cliente.getTipoRegistro());
		return clTo;
	}
	
}
