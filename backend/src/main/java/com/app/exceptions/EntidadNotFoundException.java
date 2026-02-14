package com.app.exceptions;

public class EntidadNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 6661703177543273107L;
	
	public EntidadNotFoundException(String entidad) {
		super("Error, no se ha podido encontrar la entidad " + entidad);
	}

}
