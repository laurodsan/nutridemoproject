package com.example.demo.web.controller;

import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.dto.ClienteDTO;
import com.example.demo.service.ClienteService;

@Controller
public class ClienteDashboardController {

    private static final Logger log = LoggerFactory.getLogger(ClienteDashboardController.class);
    
    @Autowired
	private ClienteService clienteService;

    @GetMapping("/cliente/dashboard/{idCliente}")
    public ModelAndView mostrarDashboard(@PathVariable("idCliente") Long idCliente,HttpSession session) {

        log.info("ClienteDashboardController - mostrarDashboard: Ver dashboard del cliente" + idCliente);

        // Recuperar cliente de la sesión
        ClienteDTO clienteDTO = (ClienteDTO) session.getAttribute("clienteLogueado");
        
        // Si no hay cliente en sesión, crear un DTO temporal con el ID de la URL
        if (clienteDTO == null) {
            clienteDTO = new ClienteDTO();
            clienteDTO.setId(idCliente);
        }
        
        
        clienteDTO = clienteService.findById(clienteDTO); 
        

        // Si no hay cliente logueado → volver al login
        if (clienteDTO == null) {
            log.warn("Intento de acceso al dashboard sin sesión. Redirigiendo a login.");
            return new ModelAndView("redirect:/login?error=Debes iniciar sesión");
        }

        // Cargar vista del dashboard
        ModelAndView mav = new ModelAndView("cliente/dashboard");
        mav.addObject("clienteDTO", clienteDTO);
        mav.addObject("titulo", "Panel del Cliente");
        
        return mav;
    }
}

