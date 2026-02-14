package com.app.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entities.Mesa;

@Repository
public interface MesaRepository extends JpaRepository<Mesa, Long> {

	Optional<Mesa> findByNumeroAndBurguerId(Long numero, Long idBurguer);

	List<Mesa> findByBurguerId(Long idBurguer);

}
