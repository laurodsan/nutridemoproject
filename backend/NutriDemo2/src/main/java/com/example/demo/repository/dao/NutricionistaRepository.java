package com.example.demo.repository.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.repository.entity.Nutricionista;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface NutricionistaRepository extends JpaRepository<Nutricionista, Long>{

	 // Nuevo: buscar por email para login
    public Optional<Nutricionista> findByEmail(String email);
    
    // Cambiado el tipo de retorno a Optional<Cliente>
    public Optional<Nutricionista> findById(Long idNutricionista);


    //Nuevo: buscar nutricionista por especialidad
    Nutricionista findFirstByEspecialidad(String especialidad);

    //Nuevo: nutricionista con menos clientes
    @Query("""
        SELECT n FROM Nutricionista n
        LEFT JOIN n.listaClientes c
        GROUP BY n
        ORDER BY COUNT(c.id) ASC
        LIMIT 1
    """)
    Nutricionista findNutricionistaConMenosClientes();
}
