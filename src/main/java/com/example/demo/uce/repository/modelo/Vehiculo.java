package com.example.demo.uce.repository.modelo;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "vehiculo")
public class Vehiculo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "vehi_id")
	private Integer id;
	
	@Column(name = "vehi_placa")
	private String placa;
	
	@Column(name = "vehi_modelo")
	private String modelo;
	
	@Column(name = "vehi_marca")
	private String marca;
	
	@Column(name = "vehi_anio_fabricacion")
	private LocalDate anioFablicacion;
	
	@Column(name = "vehi_pais_fabricacion")
	private String paisFabricacion;
	
	@Column(name = "vehi_cilindraje")
	private Integer cilindraje;
	
	@Column(name = "vehi_precio_vehiculo")
	private Double precioVehiculo;
	
	@Column(name = "vehi_valor_dia")
	private Double valorDia;
	
	@Column(name = "vehi_disponible")
	private String disponible;
	
	@OneToOne(mappedBy = "vehiculo", cascade = CascadeType.ALL)
	private Reserva reserva;
	
	public LocalDate getAnioFablicacion() {
		return anioFablicacion;
	}

	public void setAnioFablicacion(LocalDate anioFablicacion) {
		this.anioFablicacion = anioFablicacion;
	}

	public Reserva getReserva() {
		return reserva;
	}

	public void setReserva(Reserva reserva) {
		this.reserva = reserva;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getPaisFabricacion() {
		return paisFabricacion;
	}

	public void setPaisFabricacion(String paisFabricacion) {
		this.paisFabricacion = paisFabricacion;
	}

	public Integer getCilindraje() {
		return cilindraje;
	}

	public void setCilindraje(Integer cilindraje) {
		this.cilindraje = cilindraje;
	}

	public Double getPrecioVehiculo() {
		return precioVehiculo;
	}

	public void setPrecioVehiculo(Double precioVehiculo) {
		this.precioVehiculo = precioVehiculo;
	}

	public Double getValorDia() {
		return valorDia;
	}

	public void setValorDia(Double valorDia) {
		this.valorDia = valorDia;
	}

	public String getDisponible() {
		return disponible;
	}

	public void setDisponible(String disponible) {
		this.disponible = disponible;
	}
	
	
	
}