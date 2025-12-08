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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.dto.ComidaDTO;
import com.example.demo.model.dto.MenuDTO;
import com.example.demo.service.ComidaService;
import com.example.demo.service.MenuService;


@Controller
public class ComidaController {
	
	private static final Logger log = LoggerFactory.getLogger(ComidaController.class);
	
	@Autowired
	private ComidaService comidaService;
	
	@Autowired
	private MenuService menuService;
	
	@GetMapping("/menus/{idMenu}/comidas")
	public ModelAndView findAllByMenu(
	        @PathVariable Long idMenu,
	        @RequestParam(required = false) String diaSemana) {

	    log.info("ComidaController - findAll: Mostramos comidas, filtro = {}", diaSemana);

	    MenuDTO menuDTO = new MenuDTO();
	    menuDTO.setId(idMenu);
	    menuDTO = menuService.findByMenuId(menuDTO);

	    List<ComidaDTO> listaComidasDTO;

	    // Si no hay filtro → todas
	    if (diaSemana == null || diaSemana.isEmpty()) {
	        listaComidasDTO = comidaService.findAllByMenu(menuDTO);
	    } else {
	        listaComidasDTO = comidaService.findAllByMenuAndDia(menuDTO, diaSemana);
	    }

	    ModelAndView mav = new ModelAndView("nutricionista/comidas");
	    mav.addObject("menuDTO", menuDTO);
	    mav.addObject("listaComidasDTO", listaComidasDTO);
	    mav.addObject("diaSeleccionado", diaSemana); // para mantener el valor
	    mav.addObject("nutricionistaDTO", menuDTO.getClienteDTO().getNutricionistaDTO());

	    return mav;
	}
	
	// Dar de alta una comida
	@GetMapping("/menus/{idMenu}/comidas/add")
	public ModelAndView add(@PathVariable("idMenu") Long idMenu) {

	    log.info("ComidaController - add: Añadir una comida para el menú : " + idMenu);

	    // Cargar el menú completo (con cliente y nutricionista)
	    MenuDTO menuDTO = new MenuDTO();
	    menuDTO.setId(idMenu);
	    menuDTO = menuService.findByMenuId(menuDTO);

	    ComidaDTO comidaDTO = new ComidaDTO();
	    comidaDTO.setMenuDTO(menuDTO);

	    ModelAndView mav = new ModelAndView("nutricionista/comidaform");
	    mav.addObject("comidaDTO", comidaDTO);
	    mav.addObject("nutricionistaDTO", menuDTO.getClienteDTO().getNutricionistaDTO());
	    mav.addObject("add", true);

	    return mav;
	}

	
	// Salvar una comida
	@PostMapping("/menus/{idMenu}/comidas/save")
	public ModelAndView save(@PathVariable("idMenu") Long idMenu,
			@ModelAttribute("comidaDTO") ComidaDTO comidaDTO) {

		log.info("ComidaController - save: Añadir una comida para el menú: " + idMenu);

		comidaDTO.getMenuDTO().setId(idMenu); // Aseguramos que el ID del menú esté seteado

		// Llamar al servicio para guardar o actualizar
		comidaService.save(comidaDTO);

		// Redirigir a la lista de cuentas del cliente
		ModelAndView mav = new ModelAndView("redirect:/menus/" + idMenu + "/comidas");
		return mav;

	}
	
	// Actualizar una comida
	@GetMapping("/menus/{idMenu}/comidas/{idComida}/update")
	public ModelAndView update(@PathVariable("idMenu") Long idMenu,
	                           @PathVariable("idComida") Long idComida) {

	    log.info("ComidaController - update: Actualiza una comida " + idComida + " para el menú " + idMenu);

	    // 1. Recuperamos la comida completa desde el servicio
	    ComidaDTO comidaDTO = new ComidaDTO();
	    comidaDTO.setId(idComida);

	    ComidaDTO comidaCompleta = comidaService.findByComidaId(comidaDTO);

	    // 2. Recuperar el nutricionista
	    // comidaCompleta → menuDTO → clienteDTO → nutricionistaDTO
	    var nutricionistaDTO = comidaCompleta
	            .getMenuDTO()
	            .getClienteDTO()
	            .getNutricionistaDTO();

	    // 3. Preparar la vista
	    ModelAndView mav = new ModelAndView("nutricionista/comidaform");
	    mav.addObject("comidaDTO", comidaCompleta);
	    mav.addObject("nutricionistaDTO", nutricionistaDTO);
	    mav.addObject("add", false); // Estamos editando, no creando

	    return mav;
	}

	// Borrar commida
	@GetMapping("/menus/{idMenu}/comidas/{idComida}/delete")
	public ModelAndView delete(@PathVariable Long idMenu, @PathVariable Long idComida) {

		log.info("ComidaController - delete: Borramos la comida con id" + idComida);

		ComidaDTO comidaDTO = new ComidaDTO();
		comidaDTO.setId(idComida);

		comidaService.delete(comidaDTO);

		ModelAndView mav = new ModelAndView("redirect:/menus/" + idMenu + "/comidas");

		return mav;

	}

}
