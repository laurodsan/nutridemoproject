package com.example.demo.service;

import java.util.List;

import com.example.demo.model.dto.NutricionistaDTO;

public interface NutricionistaService {

	List<NutricionistaDTO> findAll();

	NutricionistaDTO findById(NutricionistaDTO nutricionistaDTO);

	void save(NutricionistaDTO nutricionistaDTO);

	void delete(NutricionistaDTO nutricionistaDTO);


}
