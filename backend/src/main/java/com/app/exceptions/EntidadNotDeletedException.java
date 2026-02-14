package com.app.exceptions;

public class EntidadNotDeletedException extends RuntimeException {

	private static final long serialVersionUID = -8073161612748824144L;

	public EntidadNotDeletedException(String entidad, Long id) {
		super("Error, no se ha podido eliminar la entidad " + entidad + " con id " + id);
	}
	
}
