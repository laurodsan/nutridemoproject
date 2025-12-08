package com.example.demo.repository.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.repository.entity.Comida;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface ComidaRepository extends JpaRepository<Comida, Long>{
	
	@Query(value = "SELECT * FROM comida WHERE idmenu = ?1", nativeQuery = true)
	public List<Comida> findAllByMenu(Long idMenu);

	public List<Comida> findByMenuIdAndDiaSemana(Long id, String diaSemana);

}
