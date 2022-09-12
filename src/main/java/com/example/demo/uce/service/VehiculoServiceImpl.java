package com.example.demo.uce.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.uce.repository.IVehiculoRepository;
import com.example.demo.uce.repository.modelo.Vehiculo;
import com.example.demo.uce.service.to.VehiculoTo;

@Service
public class VehiculoServiceImpl implements IVehiculoService {

	@Autowired
	private IVehiculoRepository vehiculoRepository;
	
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
	public void actualizaEstado(String placa, String estado) {
		this.vehiculoRepository.actualizaEstado(placa, estado);
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
