package com.example.demo.repository.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.demo.repository.entity.Evaluacion;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface EvaluacionRepository extends JpaRepository<Evaluacion, Long> {

	// Buscar evaluación por ID del cliente
    @Query(value = "SELECT * FROM evaluacion_inicial WHERE idcliente = ?1", nativeQuery = true)
    Evaluacion findByClienteId(Long idCliente);

}
