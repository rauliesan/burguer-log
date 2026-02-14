package com.app.exceptions;

public class EntidadNotCreatedException extends RuntimeException {

	private static final long serialVersionUID = 5226298622860853011L;
	
	public EntidadNotCreatedException(String entidad) {
		super("Error, no se ha podido crear la entidad " + entidad);
	}
	
}
