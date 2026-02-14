package com.app.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.app.dtos.MesaRequest;
import com.app.dtos.MesaResponse;
import com.app.entities.Burguer;
import com.app.entities.Mesa;
import com.app.exceptions.BurguerNotFoundException;
import com.app.exceptions.EntidadNotCreatedException;
import com.app.exceptions.EntidadNotDeletedException;
import com.app.exceptions.EntidadNotFoundException;
import com.app.exceptions.MesaDuplicateException;
import com.app.repositories.BurguerRepository;
import com.app.repositories.MesaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class MesaService {

	@Autowired
	private MesaRepository mesaRepository;

	@Autowired
	private BurguerRepository burguerRepository;

	public MesaResponse crearMesa(MesaRequest request) {
		// Comprobar si existe el burguer por id
		Optional<Burguer> optionalBurguer = burguerRepository.findById(request.getIdBurguer());
		if (optionalBurguer.isEmpty()) {
			throw new BurguerNotFoundException(request.getIdBurguer());
		}
		// Comprobar si existe una mesa con el mismo número en el burguer
		Optional<Mesa> optionalMesa = mesaRepository.findByNumeroAndBurguerId(request.getNumero(),
				request.getIdBurguer());
		if (optionalMesa.isPresent()) {
			throw new MesaDuplicateException(request.getNumero());
		}
		Mesa mesa = mapperMesaRequestToEntity(request);
		mesa.setBurguer(optionalBurguer.get());
		try {
			mesaRepository.save(mesa);
		} catch (DataIntegrityViolationException ex) {
			throw new EntidadNotCreatedException("Mesa");
		}
		return mapperEntityToMesaResponse(mesa);
	}

	public Mesa mapperMesaRequestToEntity(MesaRequest request) {
		Mesa mesa = new Mesa();
		mesa.setNumero(request.getNumero());
		return mesa;
	}

	public MesaResponse mapperEntityToMesaResponse(Mesa mesa) {
		MesaResponse response = new MesaResponse();
		response.setId(mesa.getId());
		response.setNumero(mesa.getNumero());
		response.setNombreBurguer(mesa.getBurguer().getName());
		return response;
	}

	public List<MesaResponse> obtenerMesasBurguer(Long idBurguer) {
		Optional<Burguer> optionalBurguer = burguerRepository.findById(idBurguer);
		if (optionalBurguer.isEmpty()) {
			throw new BurguerNotFoundException(idBurguer);
		}
		List<Mesa> mesas = mesaRepository.findByBurguerId(idBurguer);
		List<MesaResponse> mesasResponse = new ArrayList<>();
		for (Mesa mesa : mesas) {
			mesasResponse.add(mapperEntityToMesaResponse(mesa));
		}
		return mesasResponse;
	}

	public void eliminarMesa(Long numero) {

	}

	public void eliminarMesa(Long idBurguer, Long numero) {
		Optional<Burguer> optionalBurguer = burguerRepository.findById(idBurguer);
		if (optionalBurguer.isEmpty()) {
			throw new BurguerNotFoundException(idBurguer);
		}
		Optional<Mesa> optionalMesa = mesaRepository.findByNumeroAndBurguerId(numero, idBurguer);
		if (optionalMesa.isEmpty()) {
			throw new EntidadNotFoundException("Mesa");
		}
		try {
			mesaRepository.delete(optionalMesa.get());
		} catch (DataIntegrityViolationException ex) {
			throw new EntidadNotDeletedException("Mesa", optionalMesa.get().getId());
		}
	}

}
