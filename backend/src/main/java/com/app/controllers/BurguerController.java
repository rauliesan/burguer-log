package com.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dtos.BurguerLogin;
import com.app.dtos.BurguerRequest;
import com.app.dtos.BurguerResponse;
import com.app.entities.Burguer;
import com.app.services.BurguerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
@Validated
public class BurguerController {
	
	@Autowired
	private BurguerService service;
	
	@PostMapping("/burguer/login")
	public ResponseEntity<BurguerResponse> login(@RequestBody @Valid BurguerLogin burguerLogin){
		return ResponseEntity.ok().body(service.login(burguerLogin));
	}
	
	@PostMapping("/burguer/register")
	public ResponseEntity<BurguerResponse> register(@RequestBody @Valid BurguerRequest burguerRequest){
		return ResponseEntity.ok().body(service.register(burguerRequest));
	}
	
	// Prueba para saber si la contraseña se codifica y hace bien la comparación en el login
	@GetMapping("/burguers")
	public ResponseEntity<List<Burguer>> obtenerTodosBurguers(){
		return ResponseEntity.ok().body(service.obtenerTodosBurguers());
	}
	
}
