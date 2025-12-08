package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.dto.ClienteDTO;
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
	public NutricionistaDTO save(NutricionistaDTO nutricionistaDTO) {

		log.info("NutricionistaServiceImpl - save: Salva el nutricionista " + nutricionistaDTO);

		Nutricionista nutricionista = NutricionistaDTO.convertToEntity(nutricionistaDTO);

		nutricionistaRepository.save(nutricionista);

		return nutricionistaDTO;
	}

	@Override
	public List<ClienteDTO> findAllClientes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NutricionistaDTO loginNutricionista(String email, String password) {

		log.info("NutricionistaServiceImpl - loginNutricionista: Intentando login para correo: " + email);
		
		 Optional<Nutricionista> opt = nutricionistaRepository.findByEmail(email);
		 
		    if (opt.isPresent()) {
		        Nutricionista nutricionista = opt.get();

		        // Comparar password
		        if (password.equals(nutricionista.getPassword())) {

		            // Crear DTO completo del nutricionista
		            NutricionistaDTO nutricionistaDTO = new NutricionistaDTO();
		            nutricionistaDTO.setId(nutricionista.getId());
		            return nutricionistaDTO; 
		        }
		    }

		return null;
	}

	@Override
	public NutricionistaDTO findById(NutricionistaDTO nutricionistaDTO) {

	    log.info("NutricionistaServiceImpl - findById: Mostrar nutricionista por id: " + nutricionistaDTO.getId());

	    Optional<Nutricionista> nutricionistaOpt = nutricionistaRepository.findById(nutricionistaDTO.getId());

	    if (nutricionistaOpt.isPresent()) {
	        Nutricionista nutricionista = nutricionistaOpt.get();

	        // Si Nutricionista tiene alguna relación que quieras convertir a DTO, lo harías aquí
	        // Por ejemplo, si tiene lista de clientes:
	        // List<ClienteDTO> clientesDTO = nutricionista.getClientes()
	        //       .stream()
	        //       .map(ClienteDTO::convertToDTO)
	        //       .collect(Collectors.toList());

	        // Convertir nutricionista a DTO completo
	        nutricionistaDTO = NutricionistaDTO.convertToDTO(nutricionista /*, clientesDTO si aplica */);

	        return nutricionistaDTO;
	    } else {
	        return null;
	    }
	}

	@Override
	public List<NutricionistaDTO> findAll() {
		
		log.info("NutricionistaServiceImpl - index: Mostrar una lista de nutricionistas");
		
		List<NutricionistaDTO> listaNutricionistasDTO = new ArrayList<NutricionistaDTO>();
		List<Nutricionista> listaNutricionistas = nutricionistaRepository.findAll();

		for (int i = 0; i < listaNutricionistas.size(); i++) {
			Nutricionista nutricionista = listaNutricionistas.get(i);
			NutricionistaDTO nutricionistaDTO = NutricionistaDTO.convertToDTO(nutricionista);
			listaNutricionistasDTO.add(nutricionistaDTO);
		}

		return listaNutricionistasDTO;
		
	}
	
}
