package com.example.demo.uce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.uce.repository.IReservaRespository;
import com.example.demo.uce.repository.modelo.Reserva;

@Service
public class ReserveServiceImpl implements IReservaService {
	
	@Autowired
	private IReservaRespository reservaRespository;

	@Override
	public void crearReserva(Reserva reserva) {
		reservaRespository.crearReserva(reserva);
	}

}
