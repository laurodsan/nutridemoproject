package com.example.demo.repository.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.repository.entity.Progreso;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface ProgresoRepository extends JpaRepository<Progreso, Long>{
	
	@Query(value = "SELECT * FROM progreso WHERE idcliente = ?1", nativeQuery = true)
	public List<Progreso> findAllByCliente(Long idCliente);
	
	@Query(
			   value = "SELECT * FROM progreso " +
			           "WHERE idcliente = :idCliente " +
			           "AND YEAR(fecha) = :anio " +
			           "AND WEEK(fecha, 1) = :semana",
			   nativeQuery = true)
			List<Progreso> findByClienteAndWeek(@Param("idCliente") Long idCliente,
			                                    @Param("anio") int anio,
			                                    @Param("semana") int semana);

}
