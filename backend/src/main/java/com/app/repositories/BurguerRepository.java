package com.app.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entities.Burguer;

@Repository
public interface BurguerRepository extends JpaRepository<Burguer, Long> {

	Optional<Burguer> findByEmail(String email);

}
