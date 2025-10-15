package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.dto.NutricionistaDTO;
import com.example.demo.repository.dao.NutricionistaRepository;
import com.example.demo.repository.entity.Cliente;
import com.example.demo.repository.entity.Nutricionista;


@Service
public class NutricionistaServiceImpl implements NutricionistaService {
	
	private static final Logger log = LoggerFactory.getLogger(NutricionistaServiceImpl.class);
	
	@Autowired
	private NutricionistaRepository nutricionistaRepository;

	@Override
	public List<NutricionistaDTO> findAll() {
		
		log.info("NutricionistaServiceImpl - index: Mostrar una lista de nutricionista");

		List<NutricionistaDTO> listaNutricionistasDTO = new ArrayList<NutricionistaDTO>();
		List<Nutricionista> listaNutricionistas = nutricionistaRepository.findAll();

		for (int i = 0; i < listaNutricionistas.size(); i++) {
			Nutricionista nutricionista = listaNutricionistas.get(i);
			NutricionistaDTO nutricionistaDTO = NutricionistaDTO.convertToDTO(nutricionista);
			listaNutricionistasDTO.add(nutricionistaDTO);
		}

		return listaNutricionistasDTO;
	}

	@Override
	public NutricionistaDTO findById(NutricionistaDTO nutricionistaDTO) {

		log.info("NutricionistaServiceImpl - findById: Mostrar nutricionista por id: " + nutricionistaDTO.getId());

		Optional<Nutricionista> nutricionista = nutricionistaRepository.findById(nutricionistaDTO.getId());
		if (nutricionista.isPresent()) {
			nutricionistaDTO = NutricionistaDTO.convertToDTO(nutricionista.get());
			return nutricionistaDTO;
		} else {
			return null;
		}

	}

	@Override
	public void save(NutricionistaDTO nutricionistaDTO) {
		
		log.info("NutricionistaServiceImpl - save: Salvamos el nutricionista: " + nutricionistaDTO.toString());

		Nutricionista nutricionista = NutricionistaDTO.convertToEntity(nutricionistaDTO);
		nutricionistaRepository.save(nutricionista);
		
	}

	@Override
	public void delete(NutricionistaDTO nutricionistaDTO) {

		log.info("NutricionistaServiceImpl - delete: Borramos el nutricionista: " + nutricionistaDTO.getId());

		Nutricionista nutricionista = new Nutricionista();
		nutricionista.setId(nutricionistaDTO.getId());

		Optional<Nutricionista> nutricionistaOpt = nutricionistaRepository.findById(nutricionistaDTO.getId());
	    if (nutricionistaOpt.isPresent()) {
	    	nutricionistaRepository.delete(nutricionistaOpt.get());
	    } else {
	        log.warn("NutricionistaServiceImpl - delete: Nutricionista con ID " + nutricionistaDTO.getId() + " no encontrado.");
	    }
	}

}
