package com.example.demo.repository.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.repository.entity.Nutricionista;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface NutricionistaRepository extends JpaRepository<Nutricionista, Long>{
	
	 // Buscar nutricionista por email
    Optional<Nutricionista> findByEmail(String email);

}
