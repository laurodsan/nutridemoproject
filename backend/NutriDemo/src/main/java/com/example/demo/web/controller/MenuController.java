package com.example.demo.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.dto.ClienteDTO;
import com.example.demo.model.dto.MenuDTO;
import com.example.demo.model.dto.NutricionistaDTO;
import com.example.demo.service.ClienteService;
import com.example.demo.service.MenuService;


@Controller
public class MenuController {
	
	private static final Logger log = LoggerFactory.getLogger(MenuController.class);
	
	 @Autowired
	 private MenuService menuService;

	
	 @Autowired
		private ClienteService clienteService;

	// Listar los menus
	    @GetMapping("/nutricionistas/{idNutricionista}/clientes/{idCliente}/menus")
	    public ModelAndView findAllByCliente(@PathVariable("idCliente") Long idCliente,
	                                        @PathVariable("idNutricionista") Long idNutricionista) {

	        log.info("MenuController - index: Mostrar una lista de menus del cliente " + idCliente);

	        // Traer el cliente completo desde el servicio
	        ClienteDTO clienteDTO = new ClienteDTO();
	        clienteDTO.setId(idCliente);
	        clienteDTO = clienteService.findByClienteId(clienteDTO); // ahora incluye nutricionistaDTO

	        if (clienteDTO == null) {
	            log.error("No se encontró el cliente con id " + idCliente);
	            return new ModelAndView("error"); // o cualquier vista de error que tengas
	        }

	        // Obtener la lista de menus del cliente
	        List<MenuDTO> listaMenusDTO = menuService.findAllByCliente(clienteDTO);

	        // Crear el objeto ModelAndView para la vista
	        ModelAndView mav = new ModelAndView("menus");
	        mav.addObject("listaMenusDTO", listaMenusDTO);
	        mav.addObject("clienteDTO", clienteDTO);
	        mav.addObject("nutricionistaDTO", clienteDTO.getNutricionistaDTO());

	        return mav;
	    }

}
