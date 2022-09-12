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
		return transformaReservaTo(this.reservaRespository.buscaReservaNumero(nReserva));
	}
	
	private ReservaTo transformaReservaTo(Reserva reserva) {
		ReservaTo aux = new ReservaTo();
		aux.setPlaca(reserva.getVehiculo().getPlaca());
		aux.setModelo(reserva.getVehiculo().getModelo());
		aux.setEstado(reserva.getVehiculo().getDisponible());
		aux.setFecha(reserva.getFechaInicio().toString() + "-" + reserva.getFechaFin());
		aux.setCiCliente(reserva.getCliente().getCedula());
		return aux;
	}

}
