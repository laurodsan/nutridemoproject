package com.example.demo.service;

import java.util.List;

import com.example.demo.model.dto.ClienteDTO;
import com.example.demo.model.dto.NutricionistaDTO;


public interface ClienteService {

	List<ClienteDTO> findAllByNutricionista(NutricionistaDTO nutricionistaDTO);

	void save(ClienteDTO clienteDTO);

	void delete(ClienteDTO clienteDTO);

	ClienteDTO findByClienteId(ClienteDTO clienteDTO);

	ClienteDTO loginCliente(String email, String password);


}
