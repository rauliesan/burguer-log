package com.app.exceptions;

public class BurguerNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -2146549566096529732L;
	
	public BurguerNotFoundException(String email) {
		super("Error, no se ha podido encontrar ningún burguer con el email " + email + ".");
	}
	
	public BurguerNotFoundException(Long id) {
        super("Error, no se ha podido encontrar ningún burguer con el id " + id + ".");
    }
	
}
