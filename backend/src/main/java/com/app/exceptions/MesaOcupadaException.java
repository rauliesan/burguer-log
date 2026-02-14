package com.app.exceptions;

public class MesaOcupadaException extends RuntimeException {

	private static final long serialVersionUID = -3821547934014095312L;
	
	public MesaOcupadaException(Long numero) {
		super("Error, no se puede crear un pedido, ya hay uno asignado en la mesa " + numero);
	}

}
