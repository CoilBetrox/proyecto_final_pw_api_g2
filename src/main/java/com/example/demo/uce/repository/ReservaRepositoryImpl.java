package com.example.demo.uce.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.example.demo.uce.repository.modelo.Reserva;

@Repository
@Transactional
public class ReservaRepositoryImpl implements IReservaRespository {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public void crearReserva(Reserva reserva) {
		this.entityManager.persist(reserva);
	}


}
