package com.app.dtos;

public class MesaResponse {

	private Long id;

	private Long numero;

	private String nombreBurguer;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public String getNombreBurguer() {
		return nombreBurguer;
	}

	public void setNombreBurguer(String nombreBurguer) {
		this.nombreBurguer = nombreBurguer;
	}

}
