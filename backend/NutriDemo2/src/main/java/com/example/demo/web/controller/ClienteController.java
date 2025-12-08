package com.example.demo.web.controller;

import java.util.Date;
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
import com.example.demo.model.dto.NutricionistaDTO;
import com.example.demo.model.dto.PlanDTO;
import com.example.demo.service.ClienteService;
import com.example.demo.service.NutricionistaService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ClienteController {
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private NutricionistaService nutricionistaService;

	private static final Logger log = LoggerFactory.getLogger(ClienteController.class);
	
	// Dar de alta un cliente
	@GetMapping("/registro/cliente")
	public ModelAndView add() {

		log.info("ClienteController - add: Anyadimos un cliente nuevo");
		
		
		ClienteDTO clienteDTO = new ClienteDTO();
		
        // Traer lista de planes disponibles
        List<PlanDTO> listaPlanes = clienteService.findAllPlanes();

		ModelAndView mav = new ModelAndView("registro");
		mav.addObject("clienteDTO", clienteDTO);
		mav.addObject("listaPlanes", listaPlanes);
		mav.addObject("add", true);

		return mav;

	}	
	
	@PostMapping("/registro/cliente/save")
	public ModelAndView save(@ModelAttribute("clienteDTO") ClienteDTO clienteDTO, HttpSession session) {

	    log.info("ClienteController - save: Añadir un cliente");

	    if (clienteDTO.getFechaRegistro() == null) {
	        clienteDTO.setFechaRegistro(new Date());
	    }

	    // Asignar plan desde BD
	    if (clienteDTO.getPlanDTO() != null && clienteDTO.getPlanDTO().getId() != null) {
	        clienteDTO.setPlanDTO(clienteService.findPlanById(clienteDTO.getPlanDTO().getId()));
	    }

	    //Guardar cliente en la BD
	    ClienteDTO clienteGuardado = clienteService.save(clienteDTO);

	    //Guardarlo en sesión automáticamente
	    session.setAttribute("clienteLogueado", clienteGuardado);

	    //Redirigir a su dashboard
	    return new ModelAndView("redirect:/cliente/dashboard");
	}
	
	//Lista de clientes de un nutricionsta
	@GetMapping("/nutricionista/{idNutricionista}/clientes")
	public ModelAndView findAllByNutricionista(@PathVariable Long idNutricionista) {

		log.info("ClienteController - findAll: Mostramos todos los clientes del nutricionista " + idNutricionista);

		NutricionistaDTO nutricionistaDTO = new NutricionistaDTO();
		nutricionistaDTO.setId(idNutricionista);
		nutricionistaDTO = nutricionistaService.findById(nutricionistaDTO);

		List<ClienteDTO> listaClientesDTO = clienteService.findAllByNutricionista(nutricionistaDTO);

		ModelAndView mav = new ModelAndView("nutricionista/clientes-asignados");
		mav.addObject("nutricionistaDTO", nutricionistaDTO);
		mav.addObject("listaClientesDTO", listaClientesDTO);

		return mav;
	}

}
