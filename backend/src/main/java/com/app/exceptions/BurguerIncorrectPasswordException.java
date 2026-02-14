package com.app.exceptions;

public class BurguerIncorrectPasswordException extends RuntimeException {

	private static final long serialVersionUID = -4975107698493516798L;
	
	public BurguerIncorrectPasswordException() {
		super("Error, la contraseña introducida es incorrecta.");
	}

}
