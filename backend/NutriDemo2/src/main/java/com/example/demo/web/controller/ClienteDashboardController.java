package com.example.demo.web.controller;

import jakarta.servlet.http.HttpSession;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.dto.ClienteDTO;
import com.example.demo.model.dto.ComidaDTO;
import com.example.demo.model.dto.MenuDTO;
import com.example.demo.service.ClienteService;
import com.example.demo.service.EvaluacionService;
import com.example.demo.service.MenuService;

@Controller
public class ClienteDashboardController {

    private static final Logger log = LoggerFactory.getLogger(ClienteDashboardController.class);
    
    @Autowired
	private ClienteService clienteService;
    
    @Autowired
    private MenuService menuService;
    
    @Autowired
    private EvaluacionService evaluacionService;
    
    
    //Panel del cliente
    @GetMapping("/cliente/dashboard/{idCliente}")
    public ModelAndView mostrarDashboard(@PathVariable("idCliente") Long idCliente, HttpSession session) {

        log.info("ClienteDashboardController - mostrarDashboard: Ver dashboard del cliente" + idCliente);

        // Recuperar cliente de la sesión
        ClienteDTO clienteDTO = (ClienteDTO) session.getAttribute("clienteLogueado");
        
        // Si no hay cliente en sesión, crear un DTO temporal con el ID de la URL
        if (clienteDTO == null) {
            clienteDTO = new ClienteDTO();
            clienteDTO.setId(idCliente);
        }
        
        // Cargar cliente completo
        clienteDTO = clienteService.findById(clienteDTO); 

        // Si no hay cliente logueado → volver al login
        if (clienteDTO == null) {
            log.warn("Intento de acceso al dashboard sin sesión. Redirigiendo a login.");
            return new ModelAndView("redirect:/login?error=Debes iniciar sesión");
        }

        //Comprobar si tiene evaluación inicial
        boolean tieneEvaluacionInicial = evaluacionService.findByClienteId(clienteDTO.getId()) != null;

        // Cargar vista del dashboard
        ModelAndView mav = new ModelAndView("cliente/dashboard");
        mav.addObject("clienteDTO", clienteDTO);
        mav.addObject("tieneEvaluacionInicial", tieneEvaluacionInicial);
        mav.addObject("titulo", "Panel del Cliente");
        
        return mav;
    }

    
	///Asignar un nutricionista al cliente
	@GetMapping("/cliente/dashboard/{idCliente}/asignar-nutricionista")
	public ModelAndView asignarNutricionista(@PathVariable("idCliente") Long idCliente) {

		log.info("ClienteDashboardController - asignarNutricionista: Actualizamos el cliente: " + idCliente + " añadiendo el nutricionista");

		ClienteDTO clienteDTO = new ClienteDTO();
		clienteDTO.setId(idCliente);

		clienteDTO = clienteService.asignarNutricionista(clienteDTO); // llamada al servicio asignar nutricionista
		
		ModelAndView mav = new ModelAndView("cliente/dashboard");
		mav.addObject("clienteDTO", clienteDTO);
        mav.addObject("titulo", "Panel del Cliente");

		return mav;

	}
	
	//Ver los menús del cliente
    @GetMapping("/cliente/{idCliente}/menus")
    public ModelAndView verMenusCliente(@PathVariable("idCliente") Long idCliente,
                                        HttpSession session) {

        log.info("ClienteDashboardController - verMenusCliente: Ver menús del cliente " + idCliente);

        // Recuperar cliente de sesión o por ID
        ClienteDTO clienteDTO = (ClienteDTO) session.getAttribute("clienteLogueado");

        if (clienteDTO == null) {
            clienteDTO = new ClienteDTO();
            clienteDTO.setId(idCliente);
        }

        clienteDTO = clienteService.findById(clienteDTO);

        if (clienteDTO == null) {
            log.warn("Intento de acceso a menús sin sesión. Redirigiendo a login.");
            return new ModelAndView("redirect:/login?error=Debes iniciar sesión");
        }

        // Listar menús del cliente
        List<MenuDTO> listaMenusDTO = menuService.findAllByCliente(clienteDTO);

        ModelAndView mav = new ModelAndView("cliente/menus");
        mav.addObject("clienteDTO", clienteDTO);
        mav.addObject("listaMenusDTO", listaMenusDTO);
        mav.addObject("titulo", "Tus menús");

        return mav;
    }

}

