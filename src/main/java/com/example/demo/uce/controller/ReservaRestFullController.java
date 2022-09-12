package com.example.demo.uce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.uce.service.IReservaService;

@RestController
@RequestMapping("APINomina/V1/reservas")
@CrossOrigin("http://localhost:8080/")
public class ReservaRestFullController {
	
	@Autowired
	private IReservaService reservaService;
	
	
}
