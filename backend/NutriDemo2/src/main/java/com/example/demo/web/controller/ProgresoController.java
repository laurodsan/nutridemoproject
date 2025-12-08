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
import com.example.demo.model.dto.NutricionistaDTO;
import com.example.demo.model.dto.ProgresoDTO;
import com.example.demo.service.ClienteService;
import com.example.demo.service.NutricionistaService;
import com.example.demo.service.ProgresoService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ProgresoController {

	 private static final Logger log = LoggerFactory.getLogger(ProgresoController.class);
	 
		@Autowired
		private ClienteService clienteService;
		
		@Autowired
		private ProgresoService progresoService;
		
		@Autowired
		private NutricionistaService nutricionistaService;
	 
	// Listar progresos
		@GetMapping("/cliente/dashboard/{idCliente}/progreso")
		public ModelAndView findAll(@PathVariable("idCliente") Long idCliente, HttpSession session) {

			log.info("ProgresoController - index: Mostrar lista de progresos del cliente " + idCliente);

			// Recuperar cliente de sesión
	        ClienteDTO clienteDTO = (ClienteDTO) session.getAttribute("clienteLogueado");

	        if (clienteDTO == null) {
	            clienteDTO = new ClienteDTO();
	            clienteDTO.setId(idCliente);
	        }

	        clienteDTO = clienteService.findById(clienteDTO);

	        // Obtener lista  de progresos
	        List<ProgresoDTO> listaProgresosDTO = progresoService.findAllByCliente(clienteDTO);

			// Crear el objeto ModelAndView para la vista
			ModelAndView mav = new ModelAndView("cliente/progreso");
			mav.addObject("listaProgresosDTO", listaProgresosDTO);
			mav.addObject("clienteDTO", clienteDTO);

			return mav;
		}
		
		// Dar de alta un progreso
		@GetMapping("/cliente/dashboard/{idCliente}/progreso/add")
		public ModelAndView add(@PathVariable("idCliente") Long idCliente) {

		    log.info("ProgresoController - add: Añadir un progreso para el cliente : " + idCliente);

		    ClienteDTO clienteDTO = new ClienteDTO();
		    clienteDTO.setId(idCliente);
		    clienteDTO = clienteService.findById(clienteDTO);

		    ProgresoDTO progresoDTO = new ProgresoDTO();
		    progresoDTO.setClienteDTO(clienteDTO);

		    ModelAndView mav = new ModelAndView("cliente/progresoform");
		    mav.addObject("progresoDTO", progresoDTO);
		    mav.addObject("clienteDTO",clienteDTO );
		    mav.addObject("add", true);

		    return mav;
		}
		
		// Salvar una comida
		@PostMapping("/cliente/dashboard/{idCliente}/progreso/save")
		public ModelAndView save(@PathVariable("idCliente") Long idCliente,
		                         @ModelAttribute("progresoDTO") ProgresoDTO progresoDTO) {

		    log.info("ProgresoController - save: Añadir un progreso para el cliente: " + idCliente);

		    progresoDTO.getClienteDTO().setId(idCliente);

		    ModelAndView mav = new ModelAndView();

		    try {
		        progresoService.save(progresoDTO);
		        mav.setViewName("redirect:/cliente/dashboard/" + idCliente + "/progreso");
		    } catch (Exception e) {
		        mav.setViewName("cliente/progresoform");
		        mav.addObject("progresoDTO", progresoDTO);
		        mav.addObject("clienteDTO", progresoDTO.getClienteDTO());
		        mav.addObject("add", true);
		        mav.addObject("errorMessage", e.getMessage());
		    }

		    return mav;
		}
		
		@GetMapping("/nutricionistas/{idNutricionista}/progreso/{idCliente}")
		public ModelAndView verProgreso(@PathVariable Long idNutricionista,
		                                @PathVariable Long idCliente) {

		    // Recuperar nutricionista (aunque solo lo usamos para volver atrás)
		    NutricionistaDTO nutricionistaDTO = new NutricionistaDTO();
		    nutricionistaDTO.setId(idNutricionista);
		    nutricionistaDTO = nutricionistaService.findById(nutricionistaDTO);

		    // Traer cliente
		    ClienteDTO clienteDTO = new ClienteDTO();
		    clienteDTO.setId(idCliente);
		    clienteDTO = clienteService.findById(clienteDTO);

		    // Traer progresos del cliente
		    List<ProgresoDTO> listaProgresos = progresoService.findAllByCliente(clienteDTO);

		    ModelAndView mav = new ModelAndView("nutricionista/progreso");
		    mav.addObject("nutricionistaDTO", nutricionistaDTO);
		    mav.addObject("clienteDTO", clienteDTO);
		    mav.addObject("listaProgresos", listaProgresos);

		    return mav;
		}

	 
}