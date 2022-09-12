package com.example.demo.uce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.uce.repository.modelo.Reserva;
import com.example.demo.uce.service.IReservaService;
import com.example.demo.uce.service.IVehiculoService;

@RestController
@RequestMapping("/reservas")
@CrossOrigin("http://localhost:8080/")
public class ReservaRestFullController {
	
	@Autowired
	private IReservaService reservaService;
	
	@Autowired
	private IVehiculoService vehiculoService;
	
	@PutMapping(path = "/retirar")
	public String retiraVehiculoReservado(@RequestParam("nReserva") Integer nReserva) {
		Reserva aux = this.reservaService.buscaReservaNumero(nReserva);
		
		return null;
	}
	
	
	
}
