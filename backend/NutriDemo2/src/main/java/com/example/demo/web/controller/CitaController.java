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

import com.example.demo.model.dto.CitaDTO;
import com.example.demo.model.dto.ClienteDTO;
import com.example.demo.model.dto.NutricionistaDTO;
import com.example.demo.model.dto.ProgresoDTO;
import com.example.demo.service.CitaService;
import com.example.demo.service.ClienteService;

import jakarta.servlet.http.HttpSession;

@Controller
public class CitaController {

	private static final Logger log = LoggerFactory.getLogger(CitaController.class);

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private CitaService citaService;

	// Listar citas del cliente
	@GetMapping("/cliente/dashboard/{idCliente}/citas")
	public ModelAndView findAll(@PathVariable("idCliente") Long idCliente, HttpSession session) {

		log.info("CitaController - findAll: Mostrar citas del cliente: " + idCliente);

		// Recuperar cliente de sesión
		ClienteDTO clienteDTO = (ClienteDTO) session.getAttribute("clienteLogueado");

		if (clienteDTO == null) {
			clienteDTO = new ClienteDTO();
			clienteDTO.setId(idCliente);
		}

		clienteDTO = clienteService.findById(clienteDTO);

		List<CitaDTO> listaCitasDTO = citaService.findAllByCliente(clienteDTO);

		ModelAndView mav = new ModelAndView("cliente/citas");
		mav.addObject("clienteDTO", clienteDTO);
		mav.addObject("listaCitasDTO", listaCitasDTO);

		return mav;
	}

	@GetMapping("/cliente/dashboard/{idCliente}/citas/add")
	public ModelAndView add(@PathVariable("idCliente") Long idCliente, HttpSession session) {

		log.info("CitaController - add: Añadir cita para el cliente " + idCliente);

		ClienteDTO clienteDTO = (ClienteDTO) session.getAttribute("clienteLogueado");

		if (clienteDTO == null) {
			clienteDTO = new ClienteDTO();
			clienteDTO.setId(idCliente);
		}

		clienteDTO = clienteService.findById(clienteDTO);

		CitaDTO citaDTO = new CitaDTO();
		citaDTO.setClienteDTO(clienteDTO);

		ModelAndView mav = new ModelAndView("cliente/citasform");
		mav.addObject("clienteDTO", clienteDTO);
		mav.addObject("citaDTO", citaDTO);

		return mav;
	}

	// Salvar una cita
	@PostMapping("/cliente/dashboard/{idCliente}/citas/save")
	public ModelAndView save(@PathVariable("idCliente") Long idCliente, @ModelAttribute("citaDTO") CitaDTO citaDTO,
			HttpSession session) {

		log.info("CitaController - save: Salva la cita del cliente {}", idCliente);

		ClienteDTO clienteDTO = (ClienteDTO) session.getAttribute("clienteLogueado");

		if (clienteDTO == null) {
			clienteDTO = new ClienteDTO();
			clienteDTO.setId(idCliente);
		}

		citaDTO.setClienteDTO(clienteDTO);
		citaDTO.setEstado("pendiente");

		try {
			citaService.save(citaDTO);

		} catch (RuntimeException e) {

			ModelAndView mav = new ModelAndView("cliente/citasform");

			mav.addObject("clienteDTO", clienteDTO);
			mav.addObject("citaDTO", citaDTO);
			mav.addObject("errorMessage", e.getMessage());

			return mav;
		}

		return new ModelAndView("redirect:/cliente/dashboard/" + idCliente + "/citas");

	}

	@GetMapping("/nutricionista/{idNutricionista}/citas/{idCliente}")
	public ModelAndView verCitasCliente(@PathVariable Long idNutricionista, @PathVariable Long idCliente) {

		log.info("CitaController - verCitasCliente: citas del cliente {} para nutricionista {}", idCliente,
				idNutricionista);

		NutricionistaDTO nutricionistaDTO = new NutricionistaDTO();
		nutricionistaDTO.setId(idNutricionista);

		ClienteDTO clienteDTO = new ClienteDTO();
		clienteDTO.setId(idCliente);

		clienteDTO = clienteService.findById(clienteDTO);

		List<CitaDTO> listaCitas = citaService.findAllByCliente(clienteDTO);

		ModelAndView mav = new ModelAndView("nutricionista/citas");
		mav.addObject("nutricionistaDTO", nutricionistaDTO);
		mav.addObject("clienteDTO", clienteDTO);
		mav.addObject("listaCitasDTO", listaCitas);

		return mav;
	}

	@PostMapping("/nutricionista/{idNutricionista}/citas/{idCliente}/{idCita}/estado")
	public ModelAndView actualizarEstadoCita(@PathVariable Long idNutricionista, @PathVariable Long idCliente,
			@PathVariable Long idCita, @ModelAttribute("estado") String estado) {

		log.info("CitaController - actualizarEstadoCita: cita {}, nuevo estado {}", idCita, estado);

		citaService.updateEstado(idCita, estado);

		// Volver a la pantalla de gestión de citas de ese cliente
		return new ModelAndView("redirect:/nutricionista/" + idNutricionista + "/citas/" + idCliente);
	}

}
