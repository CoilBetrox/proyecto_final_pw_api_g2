package com.example.demo.uce.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.uce.repository.IVehiculoRepository;
import com.example.demo.uce.repository.modelo.Cliente;
import com.example.demo.uce.repository.modelo.CobroRealizado;
import com.example.demo.uce.repository.modelo.Reserva;
import com.example.demo.uce.repository.modelo.Vehiculo;
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
	public Vehiculo buscaVehiculoPlaca(String placa) {
		return this.vehiculoRepository.buscaVehiculoPorPlaca(placa);
	}

	@Override
	public void actualiza(Vehiculo vehiculo) {
		this.vehiculoRepository.actualiza(vehiculo);
	}
	
	
	@Override
	public String reservaVehiculo(String placa, String cedula, String fechaInicio, String fechaFin, String numeroTarjeta) {
		
		LocalDate fInicio = LocalDate.parse(fechaInicio);
		LocalDate fFin = LocalDate.parse(fechaFin);
		String disponible = compruebaVehiculo(placa, fInicio, fFin);
		String mensaje = "";
		if (disponible == "ND") {
			mensaje = "El vehículo de placa: " + placa + " no está disponible";
			return mensaje;
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
			cliente.setTarjetaCredito(numeroTarjeta);
			reserva.setCliente(cliente);
			reserva.setVehiculo(vehiculo);
			CobroRealizado cobroRealizado = new CobroRealizado();
			cobroRealizado.setFechaCobro(fechaInicio);
			cobroRealizado.setTarjeta(numeroTarjeta);
			cobroRealizado.setValorSubtotal(subtotal);
			cobroRealizado.setValorIva(valIva);
			cobroRealizado.setValorPagar(valTotal);
			cobroRealizado.setReserva(reserva);
			
			reserva.setCobroRealizado(cobroRealizado);
			
			this.clienteService.actualizarCliente(cliente);
			this.reservaService.crearReserva(reserva);
			this.vehiculoRepository.actualiza(vehiculo);
			mensaje = "Vehiculo reservado correctamente, numero de reserva: " +num.toString();
			return mensaje;
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
	
	public String compruebaVehiculoPorPlacaFecha(String placa, String fInicio, String fFin) {
		LocalDate fechaInicio = LocalDate.parse(fInicio);
		LocalDate fechaFin = LocalDate.parse(fFin);
		Vehiculo vehiculo = vehiculoRepository.buscaVehiculoPorPlaca(placa);
		String msj = "";
		if (vehiculo != null) {
			String d = compruebaDisponible(fechaInicio, fechaFin, vehiculo.getReserva());
			
			if (d == "D") {
				Double subtotal = (double)(diasReservado(fechaInicio, fechaFin) * vehiculo.getValorDia());
				Double valIva = (double)(subtotal*IVA);
				Double valTotal = (double)(subtotal + valIva);
				msj = "El vehiculo de placa: "+ placa +" está disponible, el valor a pagar es: " + valTotal.toString();
			}else {
				msj = "El vehiculo de placa: "+ placa +" no está disponible, sus proximas fechas son: " + fechaFin.plusDays(1).toString();
			}
		}
		return msj;
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
