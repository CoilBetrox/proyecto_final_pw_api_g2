package com.example.demo.uce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.uce.repository.IReservaRespository;
import com.example.demo.uce.repository.modelo.Reserva;
import com.example.demo.uce.service.to.ReservaTo;

@Service
public class ReserveServiceImpl implements IReservaService {
	
	@Autowired
	private IReservaRespository reservaRespository;

	@Override
	public void crearReserva(Reserva reserva) {
		this.reservaRespository.crearReserva(reserva);
	}

	@Override
	public Reserva buscaReservaNumero(Integer nReserva) {
		return this.reservaRespository.buscaReservaNumero(nReserva);
	}

	@Override
	public ReservaTo buscaReservaNumeroTo(Integer nReserva) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private ReservaTo transformaReservaTo(Reserva reserva) {
		ReservaTo aux = new ReservaTo();
		aux.setPlaca(reserva.getVehiculo().getPlaca());
		aux.setModelo(reserva.getVehiculo().getModelo());
		return null;
	}

}
