package com.app.exceptions;

public class MesaDuplicateException extends RuntimeException {

	private static final long serialVersionUID = 7599604810514518657L;

	public MesaDuplicateException(Long numero) {
		super("Error, en el burguer ya existe una mesa con el número " + numero);
	}
	
}
