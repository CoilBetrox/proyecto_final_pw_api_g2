package com.example.demo.uce.service;

import com.example.demo.uce.repository.modelo.Reserva;
import com.example.demo.uce.service.to.ReservaTo;

public interface IReservaService {

	public void crearReserva(Reserva reserva);
	
	public Reserva buscaReservaNumero(Integer nReserva);
	
	public ReservaTo buscaReservaNumeroTo(Integer nReserva);
}
