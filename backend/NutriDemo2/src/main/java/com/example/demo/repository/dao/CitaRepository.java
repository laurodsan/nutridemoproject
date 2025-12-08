package com.example.demo.repository.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.repository.entity.Cita;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface CitaRepository extends JpaRepository<Cita, Long> {
	
	@Query(value = "SELECT * FROM cita WHERE idcliente = ?1", nativeQuery = true)
	public List<Cita> findAllByCliente(Long idCliente);
	
    @Query("SELECT COUNT(c) FROM Cita c WHERE c.cliente.id = :idCliente")
    Long countByCliente(Long idCliente);

    @Query(value = "SELECT * FROM cita WHERE idnutricionista = ?1", nativeQuery = true)
	public List<Cita> findAllByNutricionista(Long idNutricionista);

}
