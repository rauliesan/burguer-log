package com.app.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.dtos.BurguerLogin;
import com.app.dtos.BurguerRequest;
import com.app.dtos.BurguerResponse;
import com.app.entities.Burguer;
import com.app.exceptions.BurguerIncorrectPasswordException;
import com.app.exceptions.BurguerNotFoundException;
import com.app.exceptions.EntidadNotCreatedException;
import com.app.repositories.BurguerRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BurguerService {
	
	@Autowired
	private BurguerRepository burguerRepository;
	
	@Autowired 
	private PasswordEncoder passwordEncoder;

	
	// Buscar el burguer por el email que es único y después comprobar si la contraseña es match
	public BurguerResponse login(BurguerLogin burguerLogin) {
		Optional<Burguer> optionalBurguer = burguerRepository.findByEmail(burguerLogin.getEmail());
		if(optionalBurguer.isEmpty()) {
			throw new BurguerNotFoundException(burguerLogin.getEmail());
		}
		Burguer burguer = optionalBurguer.get();
		if(!passwordEncoder.matches(burguerLogin.getPassword(), burguer.getPasswordHash())) {
			throw new BurguerIncorrectPasswordException();
		}
		return mapperBurguerEntityToResponse(burguer);
	}
	
	public BurguerResponse mapperBurguerEntityToResponse(Burguer burguer) {
		BurguerResponse response = new BurguerResponse();
		response.setName(burguer.getName());
		response.setAddress(burguer.getAddress());
		response.setEmail(burguer.getAddress());
		response.setId(burguer.getId());
		response.setTown(burguer.getTown());
		if(response.getMesas() != null) {
			response.setMesas(burguer.getCantidadMesas());
		}
		return response;
	}
	
	public Burguer mapperBurguerRequestToEntity(BurguerRequest burguerRequest) {
		Burguer burguer = new Burguer();
		burguer.setName(burguerRequest.getName());
		burguer.setAddress(burguerRequest.getAddress());
		burguer.setEmail(burguerRequest.getEmail());
		burguer.setTown(burguerRequest.getTown());
		burguer.setPasswordHash(passwordEncoder.encode(burguerRequest.getPassword()));
		return burguer;
	}

	public BurguerResponse register(BurguerRequest burguerRequest) {
		Burguer burguer = mapperBurguerRequestToEntity(burguerRequest);
		try {
			burguerRepository.save(burguer);
		} catch(DataIntegrityViolationException ex) {
			throw new EntidadNotCreatedException("Burguer");
		}
		return mapperBurguerEntityToResponse(burguer);
	}

	public List<Burguer> obtenerTodosBurguers() {
		return burguerRepository.findAll();
	}
	
	
	
}
