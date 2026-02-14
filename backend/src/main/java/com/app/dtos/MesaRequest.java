package com.app.dtos;

import jakarta.validation.constraints.NotNull;

public class MesaRequest {
	
	@NotNull
	private Long numero;
	
	@NotNull
	private Long idBurguer;
	
	

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public Long getIdBurguer() {
		return idBurguer;
	}

	public void setIdBurguer(Long idBurguer) {
		this.idBurguer = idBurguer;
	}
	
}
