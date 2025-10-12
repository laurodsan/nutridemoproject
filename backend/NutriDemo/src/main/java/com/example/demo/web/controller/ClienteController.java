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
import com.example.demo.service.ClienteService;

@Controller
public class ClienteController {
	
	@Autowired
	private ClienteService clienteService;


	private static final Logger log = LoggerFactory.getLogger(ClienteController.class);

	// Listar los clientes
	@GetMapping("/clientes")
	public ModelAndView findAll() {

		log.info("ClienteController - index: Mostrar una lista de clientes");

		ModelAndView mav = new ModelAndView("clientes");
		List<ClienteDTO> listaClientesDTO = clienteService.findAll(); // Para obtener la lista de clientes DTO le pido
																		// la informacion al servicio
		mav.addObject("listaClientesDTO", listaClientesDTO);

		return mav;

	}
	
	// Visualizar cliente
	@GetMapping("/clientes/{idCliente}")
	public ModelAndView findById(@PathVariable("idCliente") Long idCliente) {

		log.info("ClienteController - findById: Mostramos la informacion del cliente:" + idCliente);

		ClienteDTO clienteDTO = new ClienteDTO();
		clienteDTO.setId(idCliente);

		clienteDTO = clienteService.findById(clienteDTO); // llamada al servicio

		ModelAndView mav = new ModelAndView("clienteshow");
		mav.addObject("clienteDTO", clienteDTO);

		return mav;

	}
	
	// Dar de alta un cliente
	@GetMapping("/clientes/add")
	public ModelAndView add() {

		log.info("ClienteController - add: Anyadimos un cliente nuevo");

		ModelAndView mav = new ModelAndView("clienteform");
		mav.addObject("clienteDTO", new ClienteDTO());
		mav.addObject("add", true);

		return mav;

	}
	
	// Save clientes
	@PostMapping("/clientes/save")
	public ModelAndView save(@ModelAttribute("clienteDTO") ClienteDTO clienteDTO) {

		log.info("ClienteController - save: Salvamos los datos del cliente:" + clienteDTO.toString());
		
		// Asignar fecha de registro automáticamente si no está establecida
	    if (clienteDTO.getFechaRegistro() == null) {
	        clienteDTO.setFechaRegistro(new Date());
	    }

		clienteService.save(clienteDTO);

		ModelAndView mav = new ModelAndView("redirect:/clientes");
		return mav;
	}
	
	//Actualizar un cliente
	@GetMapping("/clientes/update/{idCliente}")
	public ModelAndView update(@PathVariable("idCliente") Long idCliente) {

		log.info("ClienteController - update: Actualizamos el cliente: " + idCliente);

		ClienteDTO clienteDTO = new ClienteDTO();
		clienteDTO.setId(idCliente);

		clienteDTO = clienteService.findById(clienteDTO); // llamada al servicio
		
		ModelAndView mav = new ModelAndView("clienteform");
		mav.addObject("clienteDTO",clienteDTO);
		mav.addObject("add", false);

		return mav;

	}
	
	// Borrar un cliente
	@GetMapping("/clientes/delete/{idCliente}")
	public ModelAndView delete(@PathVariable("idCliente") Long idCliente) {

		log.info("ClienteController - delete: Borramos el cliente:" + idCliente);

		ClienteDTO clienteDTO = new ClienteDTO();
		clienteDTO.setId(idCliente);

		clienteService.delete(clienteDTO); // llamada al servicio

		ModelAndView mav = new ModelAndView("redirect:/clientes");

		return mav;

	}

}
