package com.example.demo.service;

import java.util.List;

import com.example.demo.model.dto.ClienteDTO;
import com.example.demo.model.dto.NutricionistaDTO;

public interface NutricionistaService {

	NutricionistaDTO save(NutricionistaDTO nutricionistaDTO);

	List<ClienteDTO> findAllClientes();

	NutricionistaDTO loginNutricionista(String email, String password);

	NutricionistaDTO findById(NutricionistaDTO nutricionistaDTO);
	
	List<NutricionistaDTO> findAll();

}
