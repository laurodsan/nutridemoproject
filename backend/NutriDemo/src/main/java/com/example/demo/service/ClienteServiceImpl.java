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
import com.example.demo.repository.dao.ClienteRepository;
import com.example.demo.repository.entity.Cliente;
import com.example.demo.repository.entity.Nutricionista;

@Service
public class ClienteServiceImpl implements ClienteService{
	
	private static final Logger log = LoggerFactory.getLogger(ClienteServiceImpl.class);
	
	@Autowired
	private ClienteRepository clienteRepository;


	@Override
	public List<ClienteDTO> findAllByNutricionista(NutricionistaDTO nutricionistaDTO) {
		
		log.info("ClienteServiceImpl - findAllByNutricionistaAll: Lista de todas los clientes del nutricionista: " + nutricionistaDTO.getId());
		
		List<Cliente> lista = (List<Cliente>) clienteRepository.findAllByNutricionista(nutricionistaDTO.getId());

		List<ClienteDTO> listaResultadoDTO = new ArrayList<ClienteDTO>();

		for (int i = 0; i < lista.size(); ++i) {
			listaResultadoDTO.add(ClienteDTO.convertToDTO(lista.get(i), nutricionistaDTO));
		}

		return listaResultadoDTO;
	}
	
	@Override
	public void delete(ClienteDTO clienteDTO) {

		log.info("ClienteServiceImpl - delete: Elimina el cliente: " + clienteDTO.getId());

		clienteRepository.deleteById(clienteDTO.getId());
	}

	@Override
	public void save(ClienteDTO clienteDTO) {
		
		log.info("ClienteServiceImpl - save: Salva el cliente" + clienteDTO + " del nutricionista " + clienteDTO.getNutricionistaDTO().getId());

		Nutricionista nutricionista = new Nutricionista();
		nutricionista.setId(clienteDTO.getNutricionistaDTO().getId());

		Cliente cliente = ClienteDTO.convertToEntity(clienteDTO, nutricionista);
		
		clienteRepository.save(cliente);
	}
	

	@Override
	public ClienteDTO findByClienteId(ClienteDTO clienteDTO) {
		
		log.info("ClienteServiceImpl - findByClienteId: Obtener el cliente con id: " + clienteDTO.getId());

		// Buscar el cliente por ID usando el repositorio
		Cliente cliente = clienteRepository.findById(clienteDTO.getId()).orElse(null);

		if (cliente != null) {
			// Convertir el cliente a DTO y devolverla
			NutricionistaDTO nutricionistaDTO = NutricionistaDTO.convertToDTO(cliente.getNutricionista());
			return ClienteDTO.convertToDTO(cliente, nutricionistaDTO);
		} else {
			// Si no se encuentra el cliente, devolver null o lanzar una excepción si lo
			// prefieres
			return null;
		}
	}



}
