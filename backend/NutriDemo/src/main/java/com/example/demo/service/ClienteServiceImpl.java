package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.dto.ClienteDTO;
import com.example.demo.repository.dao.ClienteRepository;
import com.example.demo.repository.entity.Cliente;

@Service
public class ClienteServiceImpl implements ClienteService{
	
	private static final Logger log = LoggerFactory.getLogger(ClienteServiceImpl.class);
	
	@Autowired
	private ClienteRepository clienteRepository;

	@Override
	public List<ClienteDTO> findAll() {
		log.info("ClienteServiceImpl - index: Mostrar una lista de clientes");

		List<ClienteDTO> listaClientesDTO = new ArrayList<ClienteDTO>();
		List<Cliente> listaClientes = clienteRepository.findAll();

		for (int i = 0; i < listaClientes.size(); i++) {
			Cliente cliente = listaClientes.get(i);
			ClienteDTO clienteDTO = ClienteDTO.convertToDTO(cliente);
			listaClientesDTO.add(clienteDTO);
		}

		return listaClientesDTO;
	}

	@Override
	public ClienteDTO findById(ClienteDTO clienteDTO) {

		log.info("ClienteServiceImpl - findById: Mostrar cliente por id: " + clienteDTO.getId());

		Optional<Cliente> cliente = clienteRepository.findById(clienteDTO.getId());
		if (cliente.isPresent()) {
			clienteDTO = ClienteDTO.convertToDTO(cliente.get());
			return clienteDTO;
		} else {
			return null;
		}

	}

	@Override
	public void save(ClienteDTO clienteDTO) {
		
		log.info("ClienteServiceImpl - save: Salvamos el cliente: " + clienteDTO.toString());

		Cliente cliente = ClienteDTO.convertToEntity(clienteDTO);
		clienteRepository.save(cliente);
	}

	@Override
	public void delete(ClienteDTO clienteDTO) {

		log.info("ClienteServiceImpl - delete: Borramos el cliente: " + clienteDTO.getId());

		Cliente cliente = new Cliente();
		cliente.setId(clienteDTO.getId());

		Optional<Cliente> clienteOpt = clienteRepository.findById(clienteDTO.getId());
	    if (clienteOpt.isPresent()) {
	        clienteRepository.delete(clienteOpt.get());
	    } else {
	        log.warn("ClienteServiceImpl - delete: Cliente con ID " + clienteDTO.getId() + " no encontrado.");
	    }
	}

}
