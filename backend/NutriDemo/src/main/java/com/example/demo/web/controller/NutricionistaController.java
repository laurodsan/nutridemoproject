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
import com.example.demo.model.dto.NutricionistaDTO;
import com.example.demo.service.NutricionistaService;

@Controller
public class NutricionistaController {

	private static final Logger log = LoggerFactory.getLogger(NutricionistaController.class);

	@Autowired
	private NutricionistaService nutricionistaService;

	// Listar los nutricionistas
	@GetMapping("/nutricionistas")
	public ModelAndView findAll() {

		log.info("NutricionistaController - index: Mostrar una lista de nutricionista");

		ModelAndView mav = new ModelAndView("nutricionistas");
		List<NutricionistaDTO> listaNutricionistasDTO = nutricionistaService.findAll(); // Para obtener la lista de
																						// nutricionistas DTO le pido
		// la informacion al servicio
		mav.addObject("listaNutricionistasDTO", listaNutricionistasDTO);

		return mav;

	}

	// Visualizar nutricionista
	@GetMapping("/nutricionistas/{idNutricionista}")
	public ModelAndView findById(@PathVariable("idNutricionista") Long idNutricionista) {

		log.info("NutricionistaController - findById: Mostramos la informacion del nutricionista:" + idNutricionista);

		NutricionistaDTO nutricionistaDTO = new NutricionistaDTO();
		nutricionistaDTO.setId(idNutricionista);

		nutricionistaDTO = nutricionistaService.findById(nutricionistaDTO); // llamada al servicio

		ModelAndView mav = new ModelAndView("nutricionistashow");
		mav.addObject("nutricionistaDTO", nutricionistaDTO);

		return mav;

	}
	
	// Dar de alta un nutricionista
	@GetMapping("/nutricionistas/add")
	public ModelAndView add() {

		log.info("NutricionistaController - add: Anyadimos un nutricionista nuevo");

		ModelAndView mav = new ModelAndView("nutricionistaform");
		mav.addObject("nutricionistaDTO", new NutricionistaDTO());
		mav.addObject("add", true);

		return mav;

	}

	// Save nutricionistas
	@PostMapping("/nutricionistas/save")
	public ModelAndView save(@ModelAttribute("nutricionistaDTO") NutricionistaDTO nutricionistaDTO) {

		log.info("NutricionistaController - save: Salvamos los datos del nutricionista:" + nutricionistaDTO.toString());

		nutricionistaService.save(nutricionistaDTO);

		ModelAndView mav = new ModelAndView("redirect:/nutricionistas");
		return mav;
	}
	
	//Actualizar un nutricionista
	@GetMapping("/nutricionistas/update/{idNutricionista}")
	public ModelAndView update(@PathVariable("idNutricionista") Long idNutricionista) {

		log.info("NutricionistaController - update: Actualizamos el nutricionista: " + idNutricionista);

		NutricionistaDTO nutricionistaDTO = new NutricionistaDTO();
		nutricionistaDTO.setId(idNutricionista);

		nutricionistaDTO = nutricionistaService.findById(nutricionistaDTO); // llamada al servicio
		
		ModelAndView mav = new ModelAndView("nutricionistaform");
		mav.addObject("nutricionistaDTO",nutricionistaDTO);
		mav.addObject("add", false);

		return mav;

	}
	
	// Borrar un cliente
	@GetMapping("/nutricionistas/delete/{idNutricionista}")
	public ModelAndView delete(@PathVariable("idNutricionista") Long idNutricionista) {

		log.info("NutricionistaController - delete: Borramos el nutricionista:" + idNutricionista);

		NutricionistaDTO nutricionistaDTO = new NutricionistaDTO();
		nutricionistaDTO.setId(idNutricionista);

		nutricionistaService.delete(nutricionistaDTO); // llamada al servicio

		ModelAndView mav = new ModelAndView("redirect:/nutricionistas");

		return mav;

	}
		
}
