package com.example.demo.service;

import java.util.List;

import com.example.demo.model.dto.ClienteDTO;
import com.example.demo.model.dto.NutricionistaDTO;
import com.example.demo.model.dto.PlanDTO;


public interface ClienteService {

	ClienteDTO save(ClienteDTO clienteDTO);

	ClienteDTO loginCliente(String email, String password);

	PlanDTO findPlanById(Long id);

	List<PlanDTO> findAllPlanes();

	ClienteDTO findById(ClienteDTO clienteDTO);

	ClienteDTO asignarNutricionista(ClienteDTO clienteDTO);

	List<ClienteDTO> findAllByNutricionista(NutricionistaDTO nutricionistaDTO);

}
