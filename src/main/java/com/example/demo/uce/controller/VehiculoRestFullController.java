package com.example.demo.uce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.uce.repository.modelo.Vehiculo;
import com.example.demo.uce.service.IVehiculoService;
import com.example.demo.uce.service.to.VehiculoTo;

@RestController
@RequestMapping("/vehiculos")
@CrossOrigin("http://localhost:8080/")
public class VehiculoRestFullController {

	@Autowired
	private IVehiculoService vehiculoService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<VehiculoTo> buscaVehiculosDisponibles(@RequestParam("marca") String marca,
			@RequestParam("modelo") String modelo) {
		List<VehiculoTo> lista = this.vehiculoService.buscaVehiculosDisponibles(marca, modelo);
		return lista;
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public String crear(@RequestBody Vehiculo vehiculo) {
		String msj = "Vehiculo ingresado correctamente";
		try {
			this.vehiculoService.crear(vehiculo);
		} catch (Exception e) {
			msj = "Error al ingresar el vehículo" + e;
		}
		return msj;
	}

	@GetMapping(path = "/{placa}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<VehiculoTo> buscaVehiculoPorPlaca(@PathVariable("placa") String placa) {
		return ResponseEntity.ok(vehiculoService.buscaVehiculoPorPlaca(placa));
	}
	
	@PatchMapping(path = "/disponible")
	public String actualizaEstado(@PathVariable("placa") String placa, @PathVariable("estado") String estado) {
		String msj = "Vehiculo actualizado correctamente";
		try {
			this.vehiculoService.actualizaEstado(placa, estado);
		} catch (Exception e) {
			msj = "Error al actualizar el vehículo" + e;
		}
		return msj;
	}

}
