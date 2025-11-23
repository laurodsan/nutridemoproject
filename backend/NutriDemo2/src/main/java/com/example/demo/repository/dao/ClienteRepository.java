package com.example.demo.repository.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.repository.entity.Cliente;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	
	@Query(value = "SELECT * FROM cliente WHERE idnutricionista = ?1", nativeQuery = true)
	public List<Cliente> findAllByNutricionista(Long idNutricionista);

    // Cambiado el tipo de retorno a Optional<Cliente>
    public Optional<Cliente> findById(Long idCliente);
    
 // Nuevo: buscar por email para login
    public Optional<Cliente> findByEmail(String email);
   
}
