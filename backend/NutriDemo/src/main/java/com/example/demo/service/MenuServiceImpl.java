package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.dto.ClienteDTO;
import com.example.demo.model.dto.MenuDTO;
import com.example.demo.model.dto.NutricionistaDTO;
import com.example.demo.repository.dao.ClienteRepository;
import com.example.demo.repository.dao.MenuRepository;
import com.example.demo.repository.entity.Cliente;
import com.example.demo.repository.entity.Menu;

@Service
public class MenuServiceImpl implements MenuService {

	private static final Logger log = LoggerFactory.getLogger(MenuServiceImpl.class);
	
	@Autowired
	private MenuRepository menuRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;

	@Override
	public List<MenuDTO> findAllByCliente(ClienteDTO clienteDTO) {

		log.info("MenuServiceImpl - findAllByCliente: Mostrando lista de menus de un cliente: " + clienteDTO.getId());

		// Traer el cliente completo desde la base de datos
		Cliente cliente = clienteRepository.findById(clienteDTO.getId()).orElse(null);
		if (cliente == null) {
			log.warn("Cliente no encontrado con ID: " + clienteDTO.getId());
			return new ArrayList<>();
		}

		// Obtener los menus del cliente
		List<Menu> listaMenus = menuRepository.findAllByCliente(cliente.getId());

		// Convertir los menus a DTOs
		List<MenuDTO> listaMenusDTO = new ArrayList<>();
		for (Menu menu : listaMenus) {
			// Convertir cliente y nutricionista a DTO
			ClienteDTO clienteDTOdelMenu = ClienteDTO.convertToDTO(
					menu.getCliente(),
					NutricionistaDTO.convertToDTO(menu.getCliente().getNutricionista())
			);

			MenuDTO menuDTO = MenuDTO.convertToDTO(menu, clienteDTOdelMenu);
			listaMenusDTO.add(menuDTO);
		}

		return listaMenusDTO;
	}

}