package com.example.demo.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.dto.ClienteDTO;
import com.example.demo.model.dto.MenuDTO;
import com.example.demo.model.dto.NutricionistaDTO;
import com.example.demo.service.ClienteService;
import com.example.demo.service.MenuService;

@Controller
public class MenuController {

	private static final Logger log = LoggerFactory.getLogger(MenuController.class);

	@Autowired
	private MenuService menuService;

	@Autowired
	private ClienteService clienteService;

	// Listar los menus
	@GetMapping("/nutricionistas/{idNutricionista}/menus/{idCliente}")
	public ModelAndView findAll(@PathVariable("idCliente") Long idCliente,
			@PathVariable("idNutricionista") Long idNutricionista) {

		log.info("MenuController - index: Mostrar una lista de menus del cliente " + idCliente);

		// Traer el cliente completo desde el servicio
		ClienteDTO clienteDTO = new ClienteDTO();
		clienteDTO.setId(idCliente);
		clienteDTO = clienteService.findById(clienteDTO); // ahora incluye nutricionistaDTO

		if (clienteDTO == null) {
			log.error("No se encontró el cliente con id " + idCliente);
			return new ModelAndView("error"); // o cualquier vista de error que tengas
		}

		// Obtener la lista de menus del cliente
		List<MenuDTO> listaMenusDTO = menuService.findAllByCliente(clienteDTO);

		// Crear el objeto ModelAndView para la vista
		ModelAndView mav = new ModelAndView("nutricionista/menus");
		mav.addObject("listaMenusDTO", listaMenusDTO);
		mav.addObject("clienteDTO", clienteDTO);
		mav.addObject("nutricionistaDTO", clienteDTO.getNutricionistaDTO());

		return mav;
	}

	// Dar de alta un menú
	@GetMapping("/nutricionistas/{idNutricionista}/menus/{idCliente}/add")
	public ModelAndView add(@PathVariable("idCliente") Long idCliente,
			@PathVariable("idNutricionista") Long idNutricionista) {

		log.info("MenuController - add: Añadir un menú para: " + idCliente);

		ClienteDTO clienteDTO = new ClienteDTO();
		clienteDTO.setId(idCliente);

		NutricionistaDTO nutricionistaDTO = new NutricionistaDTO();
		nutricionistaDTO.setId(idNutricionista);

		MenuDTO menuDTO = new MenuDTO();
		menuDTO.setClienteDTO(clienteDTO);
		menuDTO.setNutricionistaDTO(nutricionistaDTO);

		ModelAndView mav = new ModelAndView("nutricionista/menuform");
		mav.addObject("menuDTO", menuDTO);
		mav.addObject("nutricionistaDTO", nutricionistaDTO);
		mav.addObject("add", true);

		return mav;
	}

	// Salvar un menú
	@PostMapping("/nutricionistas/{idNutricionista}/menus/{idCliente}/save")
	public ModelAndView save(@PathVariable("idCliente") Long idCliente,
			@PathVariable("idNutricionista") Long idNutricionista, @ModelAttribute("menuDTO") MenuDTO menuDTO) {

		log.info("MenuController - save: Añadir un menú para el cliente: " + idCliente);

		menuDTO.getClienteDTO().setId(idCliente); // Aseguramos que el ID del cliente esté seteado
		menuDTO.getNutricionistaDTO().setId(idNutricionista);

		// Llamar al servicio para guardar o actualizar
		menuService.save(menuDTO);

		// Redirigir a la lista de cuentas del cliente
		ModelAndView mav = new ModelAndView("redirect:/nutricionistas/" + idNutricionista + "/menus/" + idCliente);
		return mav;
	}

	// Actualizar un menú
	@GetMapping("/nutricionistas/{idNutricionista}/menus/{idCliente}/update/{idMenu}")
	public ModelAndView update(@PathVariable Long idCliente, @PathVariable Long idNutricionista,
			@PathVariable Long idMenu) {

		log.info("MenuController - update: Editar menú {}", idMenu);

		// 1. Cargar el menú completo
		MenuDTO menuDTO = new MenuDTO();
		menuDTO.setId(idMenu);
		MenuDTO menuCompleto = menuService.findByMenuId(menuDTO);

		// 2. Confirmar que el menú existe
		if (menuCompleto == null) {
			return new ModelAndView("redirect:/nutricionistas/" + idNutricionista + "/menus/" + idCliente);
		}

		// 3. Preparar datos obligatorios
		ClienteDTO clienteDTO = menuCompleto.getClienteDTO();
		NutricionistaDTO nutricionistaDTO = menuCompleto.getNutricionistaDTO();

		// 4. Construir la vista
		ModelAndView mav = new ModelAndView("nutricionista/menuform");
		mav.addObject("menuDTO", menuCompleto);
		mav.addObject("clienteDTO", clienteDTO);
		mav.addObject("nutricionistaDTO", nutricionistaDTO);
		mav.addObject("add", false); // es edición

		return mav;
	}

	// Borrar menú
	@GetMapping("/nutricionistas/{idNutricionista}/menus/{idCliente}/delete/{idMenu}")
	public ModelAndView delete(@PathVariable Long idNutricionista, @PathVariable Long idCliente,
			@PathVariable Long idMenu) {

		log.info("MenuController - delete: Borrando menú con id " + idMenu);

		MenuDTO menuDTO = new MenuDTO();
		menuDTO.setId(idMenu);

		menuService.delete(menuDTO);

		return new ModelAndView("redirect:/nutricionistas/" + idNutricionista + "/menus/" + idCliente);
	}

}
