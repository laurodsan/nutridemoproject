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
import com.example.demo.model.dto.PlanDTO;
import com.example.demo.repository.dao.ClienteRepository;
import com.example.demo.repository.dao.MenuRepository;
import com.example.demo.repository.entity.Cliente;
import com.example.demo.repository.entity.Menu;
import com.example.demo.repository.entity.Nutricionista;

@Service
public class MenuServiceImpl implements MenuService {

	private static final Logger log = LoggerFactory.getLogger(MenuServiceImpl.class);

	@Autowired
	private MenuRepository menuRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	// Listar menús
	@Override
	public List<MenuDTO> findAllByCliente(ClienteDTO clienteDTO) {

		log.info("MenuServiceImpl - findAllByCliente: Listando menús del cliente {}", clienteDTO.getId());

		Cliente cliente = clienteRepository.findById(clienteDTO.getId()).orElse(null);
		if (cliente == null) {
			log.warn("Cliente no encontrado con ID {}", clienteDTO.getId());
			return new ArrayList<>();
		}

		List<Menu> listaMenus = menuRepository.findAllByCliente(cliente.getId());
		List<MenuDTO> listaMenusDTO = new ArrayList<>();

		for (Menu menu : listaMenus) {

			// Convertir cliente completo, incluyendo plan y nutricionista
			PlanDTO planDTO = menu.getCliente().getPlan() != null ? PlanDTO.convertToDTO(menu.getCliente().getPlan())
					: null;

			ClienteDTO clienteDTOdelMenu = ClienteDTO.convertToDTO(menu.getCliente(), planDTO);

			NutricionistaDTO nutricionistaDTO = NutricionistaDTO.convertToDTO(menu.getNutricionista());

			MenuDTO menuDTO = MenuDTO.convertToDTO(menu, clienteDTOdelMenu);
			menuDTO.setNutricionistaDTO(nutricionistaDTO);

			listaMenusDTO.add(menuDTO);
		}

		return listaMenusDTO;
	}

	// Salvar el menú
	@Override
	public void save(MenuDTO menuDTO) {

	    log.info("MenuServiceImpl - save: Guardando menú (crear/actualizar) para cliente {}", 
	             menuDTO.getClienteDTO().getId());


	    //Si existe ID → UPDATE
	    if (menuDTO.getId() != null) {

	        Menu menuDB = menuRepository.findById(menuDTO.getId())
	                .orElseThrow(() -> new RuntimeException("No se encontró el menú a actualizar"));

	        // Actualizar SOLO los campos editables
	        menuDB.setFechaInicio(menuDTO.getFechaInicio());
	        menuDB.setFechaFin(menuDTO.getFechaFin());
	        menuDB.setDescripcion(menuDTO.getDescripcion());
	        menuDB.setEstado(menuDTO.getEstado());

	        menuRepository.save(menuDB);
	        return;
	    }


	    //Si NO existe ID → CREATE

	    Cliente cliente = new Cliente();
	    cliente.setId(menuDTO.getClienteDTO().getId());

	    Nutricionista nutricionista = new Nutricionista();
	    nutricionista.setId(menuDTO.getNutricionistaDTO().getId());

	    Menu menu = MenuDTO.convertToEntity(menuDTO, cliente, nutricionista);
	    menuRepository.save(menu);
	}



	 // Buscar menú por id
	 @Override
	    public MenuDTO findByMenuId(MenuDTO menuDTO) {

	        log.info("MenuServiceImpl - findByMenuId: Buscando el menú {}", menuDTO.getId());

	        Menu menu = menuRepository.findById(menuDTO.getId()).orElse(null);
	        if (menu == null) return null;

	        // Cliente completo
	        PlanDTO planDTO = menu.getCliente().getPlan() != null
	                ? PlanDTO.convertToDTO(menu.getCliente().getPlan())
	                : null;

	        ClienteDTO clienteDTO = ClienteDTO.convertToDTO(menu.getCliente(), planDTO);

	        // Nutricionista completo
	        NutricionistaDTO nutricionistaDTO = NutricionistaDTO.convertToDTO(menu.getNutricionista());

	        // Convertir menú
	        MenuDTO menuCompleto = MenuDTO.convertToDTO(menu, clienteDTO);
	        menuCompleto.setNutricionistaDTO(nutricionistaDTO);

	        return menuCompleto;
	    }

	 //Borrar menú
	 @Override
	 public void delete(MenuDTO menuDTO) {

	     log.info("MenuServiceImpl - delete: Eliminando menú id " + menuDTO.getId());

	     menuRepository.deleteById(menuDTO.getId());
	 }
}
