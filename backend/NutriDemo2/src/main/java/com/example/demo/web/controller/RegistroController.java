package com.example.demo.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.dto.ClienteDTO;
import com.example.demo.service.ClienteService;

@Controller
public class RegistroController {

	private static final Logger log = LoggerFactory.getLogger(RegistroController.class);

	@Autowired
	private ClienteService clienteService;

	/*@Autowired
	private NutricionistaService nutricionistaService;*/

	// Mostrar formulario de registro
	@GetMapping("/registro")
	public ModelAndView mostrarRegistro(@RequestParam(value = "error", required = false) String error) {
		log.info("RegistroController - mostrarRegistro: Mostrar registro Cliente/Nutricionista");

		ModelAndView mav = new ModelAndView("registro"); // busca templates/registro.html
		mav.addObject("titulo", "NutriDemo | Registro");
		if (error != null) {
			mav.addObject("errorMessage", error); // mensaje de error si lo hay
		}
		return mav;
	}

	// Procesar registro de CLIENTE
	@PostMapping("/registro/cliente")
	public ModelAndView registrarCliente(@ModelAttribute ClienteDTO clienteDTO) {
	    log.info("RegistroController - registrarCliente: Registro de nuevo cliente: {}", clienteDTO.getEmail());

	    try {
	        // No asignamos nutricionista aquí
	        clienteService.save(clienteDTO);
	        log.info("Cliente registrado correctamente con email: {}", clienteDTO.getEmail());
	        return new ModelAndView("redirect:/login");

	    } catch (Exception e) {
	        log.error("Error al registrar cliente: {}", e.getMessage());
	        ModelAndView mav = new ModelAndView("registro");
	        mav.addObject("errorMessage", "No se pudo registrar el cliente. Inténtalo nuevamente.");
	        mav.addObject("titulo", "NutriDemo | Registro");
	        return mav;
	    }
	}

}
