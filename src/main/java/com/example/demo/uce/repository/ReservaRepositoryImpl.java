package com.example.demo.uce.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
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

	@Override
	public Reserva buscaReservaNumero(Integer nReserva) {
		TypedQuery<Reserva> myQuery = this.entityManager.createQuery("SELECT r FROM Reserva r WHERE r.numeroReserva = :nReserva",Reserva.class);
		myQuery.setParameter("nReserva", nReserva);
		return myQuery.getSingleResult();
	}

	@Override
	public void actualiza(Reserva reserva) {
		this.entityManager.merge(reserva);
	}

	

}
