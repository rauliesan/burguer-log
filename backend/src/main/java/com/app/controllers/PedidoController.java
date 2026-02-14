package com.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dtos.CrearPedido;
import com.app.dtos.PedidoResponse;
import com.app.services.PedidoService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/api")
@Validated
public class PedidoController {

	@Autowired
	private PedidoService service;

	@PostMapping("/pedido")
	public ResponseEntity<PedidoResponse> crearPedido(@RequestBody @Valid CrearPedido request) {
		return ResponseEntity.ok().body(service.crearPedido(request));
	}

	@PutMapping("/pedido/{id}/cerrar")
	public ResponseEntity<PedidoResponse> cerrarPedido(@PathVariable @NotNull Long id) {
		return ResponseEntity.ok().body(service.cerrarPedido(id));
	}

	@GetMapping("/pedidos/{id_burguer}")
	public ResponseEntity<List<PedidoResponse>> obtenerPedidosBurguer(
			@PathVariable(name = "id_burguer") @NotNull Long idBurguer) {
		return ResponseEntity.ok().body(service.obtenerPedidosBurguer(idBurguer));
	}

}
