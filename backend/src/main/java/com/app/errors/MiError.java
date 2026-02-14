package com.app.errors;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

public class MiError {
	
	private HttpStatus status;
	
	private String mensaje;
	
	private LocalDateTime fecha;

	
	
	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}
	
}
