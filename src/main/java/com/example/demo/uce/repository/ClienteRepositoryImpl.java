package com.example.demo.uce.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.example.demo.uce.repository.modelo.Cliente;

@Repository
@Transactional
public class ClienteRepositoryImpl implements IClienteRepository {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public void registrarCliente(Cliente cliente) {
		this.em.persist(cliente);
	}

	@Override
	public Cliente buscarClienteCedula(String idCliente) {
		TypedQuery<Cliente> myQuery = this.em.createQuery("SELECT c FROM Cliente c WHERE c.cedula = :idCliente",Cliente.class);
		myQuery.setParameter("idCliente", idCliente);
		return myQuery.getSingleResult();
	}
	
	//enviar cedula nombre apellido valor iva valor total
}
