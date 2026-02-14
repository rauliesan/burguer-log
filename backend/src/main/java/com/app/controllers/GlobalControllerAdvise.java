package com.app.controllers;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.app.errors.MiError;
import com.app.exceptions.BurguerIncorrectPasswordException;
import com.app.exceptions.BurguerNotFoundException;
import com.app.exceptions.EntidadNotCreatedException;
import com.app.exceptions.EntidadNotDeletedException;
import com.app.exceptions.EntidadNotFoundException;
import com.app.exceptions.MesaDuplicateException;

@RestControllerAdvice
public class GlobalControllerAdvise {
	
	@ExceptionHandler(BurguerNotFoundException.class)
	public ResponseEntity<MiError> handlerBurguerNotFound(BurguerNotFoundException ex){
		MiError miError = new MiError();
		miError.setStatus(HttpStatus.NOT_FOUND);
		miError.setMensaje(ex.getMessage());
		miError.setFecha(LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(miError);
	}
	
	@ExceptionHandler(BurguerIncorrectPasswordException.class)
	public ResponseEntity<MiError> handlerBurguerIncorrectPassword(BurguerIncorrectPasswordException ex){
		MiError miError = new MiError();
		miError.setStatus(HttpStatus.NOT_FOUND);
		miError.setMensaje(ex.getMessage());
		miError.setFecha(LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(miError);
	}
	
	@ExceptionHandler(EntidadNotCreatedException.class)
	public ResponseEntity<MiError> handlerEntidadNotCreated(EntidadNotCreatedException ex){
		MiError miError = new MiError();
		miError.setStatus(HttpStatus.CONFLICT);
		miError.setMensaje(ex.getMessage());
		miError.setFecha(LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.CONFLICT).body(miError);
	}
	
	@ExceptionHandler(MesaDuplicateException.class)
	public ResponseEntity<MiError> handlerMesaDuplicate(MesaDuplicateException ex){
		MiError miError = new MiError();
		miError.setStatus(HttpStatus.BAD_REQUEST);
		miError.setMensaje(ex.getMessage());
		miError.setFecha(LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(miError);
	}
	
	@ExceptionHandler(EntidadNotDeletedException.class)
	public ResponseEntity<MiError> handlerEntidadNotDeleted(EntidadNotDeletedException ex){
		MiError miError = new MiError();
		miError.setStatus(HttpStatus.CONFLICT);
		miError.setMensaje(ex.getMessage());
		miError.setFecha(LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.CONFLICT).body(miError);
	}
	
	@ExceptionHandler(EntidadNotFoundException.class)
	public ResponseEntity<MiError> handlerEntidadNotFound(EntidadNotFoundException ex){
		MiError miError = new MiError();
		miError.setStatus(HttpStatus.NOT_FOUND);
		miError.setMensaje(ex.getMessage());
		miError.setFecha(LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(miError);
	}
	
}
