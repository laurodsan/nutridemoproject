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
import com.example.demo.service.ClienteService;
import com.example.demo.service.NutricionistaService;

@Controller
public class ClienteController {
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private NutricionistaService nutricionistaService;


	private static final Logger log = LoggerFactory.getLogger(ClienteController.class);

	// Listar los clientes
	@GetMapping("/nutricionistas/{idNutricionista}/clientes")
	public ModelAndView findAllByNutricionista(@PathVariable Long idNutricionista) {

		log.info("ClienteController - index: Mostrar una lista de clientes");
		
		NutricionistaDTO nutricionistaDTO = new NutricionistaDTO();
		nutricionistaDTO.setId(idNutricionista);
		nutricionistaDTO = nutricionistaService.findById(nutricionistaDTO);
		
		List<ClienteDTO> listaClientesDTO = clienteService.findAllByNutricionista(nutricionistaDTO);

		ModelAndView mav = new ModelAndView("clientes");
		mav.addObject("nutricionistaDTO", nutricionistaDTO);
		mav.addObject("listaClientesDTO", listaClientesDTO);

		return mav;

	}
	
	// Dar de alta un cliente
	@GetMapping("/nutricionistas/{idNutricionista}/clientes/add")
	public ModelAndView add(@PathVariable("idNutricionista") Long idNutricionista) {

		log.info("ClienteController - add: Anyadimos un cliente nuevo para " + idNutricionista);
		
		NutricionistaDTO nutricionistaDTO = new NutricionistaDTO();
		nutricionistaDTO.setId(idNutricionista);
		
		ClienteDTO clienteDTO = new ClienteDTO();
		clienteDTO.setNutricionistaDTO(nutricionistaDTO);
		

		ModelAndView mav = new ModelAndView("clienteform");
		mav.addObject("clienteDTO", clienteDTO);
		mav.addObject("add", true);

		return mav;

	}
	
	// Save clientes
	@PostMapping("/nutricionistas/{idNutricionista}/clientes/save")
	public ModelAndView save(@PathVariable("idNutricionista") Long idNutricionista, @ModelAttribute("clienteDTO") ClienteDTO clienteDTO) {

		log.info("ClienteController - save: Añadir una cuenta para: " + idNutricionista);
		
		
		clienteDTO.getNutricionistaDTO().setId(idNutricionista); // Aseguramos que el ID del cliente esté seteado
		
		// Asignar fecha de registro automáticamente si no está establecida
	    if (clienteDTO.getFechaRegistro() == null) {
	        clienteDTO.setFechaRegistro(new Date());
	    }

		clienteService.save(clienteDTO);

		ModelAndView mav = new ModelAndView("redirect:/nutricionistas/" + idNutricionista + "/clientes");
		return mav;
	}
	
	//Actualizar un cliente
	@GetMapping("/nutricionistas/{idNutricionista}/clientes/{idCliente}/update")
	public ModelAndView update(@PathVariable("idNutricionista") Long idNutricionista, @PathVariable("idCliente") Long idCliente) {

		log.info("ClienteController - update: Actualiza un cliente" + idCliente + " para el nutricionista: " + idNutricionista);

		ClienteDTO clienteDTO = new ClienteDTO();
		clienteDTO.setId(idCliente);
		
		NutricionistaDTO nutricionistaDTO = new NutricionistaDTO();
		nutricionistaDTO.setId(idNutricionista);
		
		clienteDTO.setNutricionistaDTO(nutricionistaDTO);
		
		// Buscar la cuenta a actualizar
		ClienteDTO clienteDtoDeseado = clienteService.findByClienteId(clienteDTO);
		
		ModelAndView mav = new ModelAndView("clienteform");
		mav.addObject("clienteDTO", clienteDtoDeseado);
		mav.addObject("add", false); // Indicamos que es una actualización, no una creación

		return mav;

	}
	
	// Borrar un cliente
	@GetMapping("/nutricionistas/{idNutricionista}/clientes/delete/{idCliente}")
	public ModelAndView delete(@PathVariable Long idNutricionista, @PathVariable Long idCliente) {

		log.info("ClienteController - delete: Borramos el cliente:" + idCliente);

		ClienteDTO clienteDTO = new ClienteDTO();
		clienteDTO.setId(idCliente);

		clienteService.delete(clienteDTO); // llamada al servicio

		ModelAndView mav = new ModelAndView("redirect:/nutricionistas/{idNutricionista}/clientes");

		return mav;

	}

}
