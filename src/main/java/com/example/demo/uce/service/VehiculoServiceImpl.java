package com.example.demo.uce.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.uce.repository.IClienteRepository;
import com.example.demo.uce.repository.IVehiculoRepository;
import com.example.demo.uce.repository.modelo.Cliente;
import com.example.demo.uce.repository.modelo.Reserva;
import com.example.demo.uce.repository.modelo.Vehiculo;
import com.example.demo.uce.service.to.ReservaAux;
import com.example.demo.uce.service.to.VehiculoTo;

@Service
public class VehiculoServiceImpl implements IVehiculoService {
	
	public static final Double IVA = 0.12;

	@Autowired
	private IVehiculoRepository vehiculoRepository;
	
	@Autowired
	private IClienteService clienteService;
	
	@Autowired
	private IReservaService reservaService;
	
	@Override
	public void crear(Vehiculo vehiculo) {
		this.vehiculoRepository.crear(vehiculo);
	}

	@Override
	public List<VehiculoTo> buscaVehiculosDisponibles(String marca, String modelo) {
		List<Vehiculo> lista = this.vehiculoRepository.buscaVehiculosDisponibles(marca, modelo);
		List<VehiculoTo> listaFinal = lista.stream().map(vehiculo -> this.convertir(vehiculo)).collect(Collectors.toList());
		return listaFinal;
	}
	
	@Override
	public VehiculoTo buscaVehiculoPorPlaca(String placa) {
		Vehiculo vehiculo = vehiculoRepository.buscaVehiculoPorPlaca(placa);
		VehiculoTo vehiculoFin = convertir(vehiculo);
		return vehiculoFin;
	}

	@Override
	public void actualiza(Vehiculo vehiculo) {
		this.vehiculoRepository.actualiza(vehiculo);
	}
	
	
	@Override
	public String reservaVehiculo(String placa, String cedula, String fechaInicio, String fechaFin) {
		
		LocalDate fInicio = LocalDate.parse(fechaFin);
		LocalDate fFin = LocalDate.parse(fechaFin);
		String disponible = compruebaVehiculo(placa, fInicio, fFin);
		if (disponible == "ND") {
			return "El vehiculo ya está reservado";
		}else {
			Vehiculo vehiculo = this.vehiculoRepository.buscaVehiculoPorPlaca(placa);
			Reserva reserva = new Reserva();
			reserva.setFechaInicio(fInicio);
			reserva.setFechaFin(fFin);
			
			Double subtotal = (double)(diasReservado(fInicio, fFin) * vehiculo.getValorDia());
			Double valIva = (double)(subtotal*IVA);
			Double valTotal = (double)(subtotal + valIva);
			
			reserva.setValorPagar(valTotal);
			Integer num = (int)(Math.random() * (10000+1));
			reserva.setNumeroReserva(num);
			reserva.setEstado("G");
			
			Cliente cliente = this.clienteService.buscarClienteCedula(cedula);
			reserva.setCliente(cliente);
			reserva.setVehiculo(vehiculo);
			
			this.reservaService.crearReserva(reserva);
			this.vehiculoRepository.actualiza(vehiculo);
			return num.toString();
		}
		
	}
	
	public Long diasReservado(LocalDate fechaInicio, LocalDate fechaFin) {
		Long diasReservado =  ChronoUnit.DAYS.between(fechaInicio, fechaFin);
		if (diasReservado > 0) {
			return diasReservado;
		}else {
			return (long)1;
		}
	}
	
	public String compruebaVehiculo(String placa, LocalDate fechaInicio, LocalDate fechaFin) {
		Vehiculo vehiculo = vehiculoRepository.buscaVehiculoPorPlaca(placa);
		if (vehiculo != null) {
			return compruebaDisponible(fechaInicio, fechaFin, vehiculo.getReserva());
		}else {
			return null;
		}
		
	}
	
	
	public String compruebaDisponible(LocalDate fechaInicio, LocalDate fechaFin, Reserva reservacion) {
		if (reservacion == null) {
			return "D";
		} else {
			if (!fechaInicio.isBefore(reservacion.getFechaFin()) && !fechaFin.isAfter(reservacion.getFechaFin())) {
				return "ND";
			}
			if (!fechaFin.isBefore(reservacion.getFechaInicio()) && !fechaFin.isAfter(reservacion.getFechaFin())) {
				return "ND";
			}
			return "D";
		}
	}

	private VehiculoTo convertir(Vehiculo vehiculo) {
		VehiculoTo vehiT = new VehiculoTo();
		vehiT.setId(vehiculo.getId());
		vehiT.setPlaca(vehiculo.getPlaca());
		vehiT.setModelo(vehiculo.getModelo());
		vehiT.setMarca(vehiculo.getMarca());
		vehiT.setAnioFablicacion(vehiculo.getAnioFablicacion().toString());
		vehiT.setPaisFabricacion(vehiculo.getPaisFabricacion());
		vehiT.setCilindraje(vehiculo.getCilindraje());
		vehiT.setPrecioVehiculo(vehiculo.getPrecioVehiculo());
		vehiT.setValorDia(vehiculo.getValorDia());
		vehiT.setDisponible(vehiculo.getDisponible());
		return vehiT;
	}
	
	
}
