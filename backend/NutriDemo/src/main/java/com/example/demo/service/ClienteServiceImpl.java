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
import com.example.demo.model.dto.PlanDTO;
import com.example.demo.repository.dao.ClienteRepository;
import com.example.demo.repository.entity.Cliente;

@Service
public class ClienteServiceImpl implements ClienteService{
	
	private static final Logger log = LoggerFactory.getLogger(ClienteServiceImpl.class);
	
	@Autowired
	private ClienteRepository clienteRepository;


	@Override
	public void save(ClienteDTO clienteDTO) {

	    log.info("ClienteServiceImpl - save: Salva el cliente " + clienteDTO;

	    // Convertir DTO a entidad
	    Cliente cliente = ClienteDTO.convertToEntity(clienteDTO, null, clienteDTO.getPlanDTO());

	    clienteRepository.save(cliente);
	}


	@Override
	public ClienteDTO loginCliente(String email, String password) {
		 log.info("ClienteServiceImpl - loginCliente: Intentando login para correo: " + email);

	        // Buscar cliente por email
	        Optional<Cliente> opt = clienteRepository.findByEmail(email);

	        if (opt.isPresent()) {
	            Cliente cliente = opt.get();

	            // Comprobar contraseña (texto plano)
	            if (password.equals(cliente.getPassword())) {
	                // Convertimos la entidad a DTO
	                ClienteDTO clienteDTO = new ClienteDTO();
	                clienteDTO.setId(cliente.getId());
	                clienteDTO.setNombre(cliente.getNombre());
	                clienteDTO.setEmail(cliente.getEmail());
	                return clienteDTO;
	            }
	        }

	        // Si no coincide o no existe, devolvemos null
	        return null;
	    }

	@Override
	public PlanDTO findPlanById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PlanDTO> findAllPlanes() {
		// TODO Auto-generated method stub
		return null;
	}

}
