package com.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dtos.MesaRequest;
import com.app.dtos.MesaResponse;
import com.app.services.MesaService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/api")
@Validated
public class MesaController {
	
	@Autowired
	private MesaService service;
	
	@PostMapping("/mesa")
	public ResponseEntity<MesaResponse> crearMesa(@Valid @RequestBody MesaRequest request){
		return ResponseEntity.ok().body(service.crearMesa(request));
	}
	
	@GetMapping("/mesas/{id_burguer}")
	public ResponseEntity<List<MesaResponse>> obtenerMesasBurguer(@PathVariable(name = "id_burguer") Long idBurguer){
		return ResponseEntity.ok().body(service.obtenerMesasBurguer(idBurguer));
	}
	
	@DeleteMapping("/mesa/{id_burguer}/{numero}")
	public ResponseEntity<String> eliminarMesa(@PathVariable(name = "id_burguer") @NotNull Long id, @PathVariable @NotNull Long numero){
		service.eliminarMesa(id, numero);
		return ResponseEntity.ok().body("Mesa " + numero + " eliminada correctamente.");
	}
	
}
