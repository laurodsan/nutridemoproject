package com.example.demo.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.dto.ClienteDTO;
import com.example.demo.model.dto.NutricionistaDTO;
import com.example.demo.service.ClienteService;
import com.example.demo.service.NutricionistaService;

@Controller
public class LoginController {

	private static final Logger log = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private NutricionistaService nutricionistaService;

	// Mostrar formulario de login
	@GetMapping("/login")
	public ModelAndView mostrarLogin(@RequestParam(value = "error", required = false) String error) {
		log.info("LoginController - mostrarLogin: Mostrar login");

		ModelAndView mav = new ModelAndView("login"); // busca templates/login.html
		mav.addObject("titulo", "NutriDemo | Iniciar sesión");
		if (error != null) {
			mav.addObject("errorMessage", error); // mensaje de error si lo hay
		}
		return mav;
	}

	@PostMapping("/login")
	public ModelAndView procesarLogin(@RequestParam String email, @RequestParam String password,
			@RequestParam String rol) {
		
		if ("cliente".equals(rol)) {
	        ClienteDTO clienteDTO = clienteService.loginCliente(email, password);
	        if (clienteDTO == null) {
	            ModelAndView mav = new ModelAndView("login");
	            mav.addObject("errorMessage", "Correo o contraseña incorrectos");
	            mav.addObject("titulo", "NutriDemo | Iniciar sesión");
	            return mav;
	        }
	        
	        return new ModelAndView("redirect:/clientes/" + clienteDTO.getId());
	        
	    } else if ("nutricionista".equals(rol)) {
	        NutricionistaDTO nutricionista = nutricionistaService.loginNutricionista(email, password);
	        if (nutricionista == null) {
	            ModelAndView mav = new ModelAndView("login");
	            mav.addObject("errorMessage", "Correo o contraseña incorrectos");
	            mav.addObject("titulo", "NutriDemo | Iniciar sesión");
	            return mav;
	        }
	        return new ModelAndView("redirect:/nutricionistas/" + nutricionista.getId());
	    }
		

	    // Por si no se selecciona rol
	    ModelAndView mav = new ModelAndView("login");
	    mav.addObject("errorMessage", "Debes seleccionar un rol");
	    mav.addObject("titulo", "NutriDemo | Iniciar sesión");
	    return mav;

	}
}
