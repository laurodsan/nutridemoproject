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
import com.example.demo.model.dto.NutricionistaDTO;
import com.example.demo.service.NutricionistaService;

@Controller
public class NutricionistaDashboardController {

    private static final Logger log = LoggerFactory.getLogger(NutricionistaDashboardController.class);

    @Autowired
    private NutricionistaService nutricionistaService;

    @GetMapping("/nutricionista/dashboard/{idNutricionista}")
    public ModelAndView mostrarDashboard(@PathVariable("idNutricionista") Long idNutricionista,
                                         HttpSession session) {

        log.info("NutricionistaDashboardController - mostrarDashboard: Ver dashboard del nutricionista " + idNutricionista);

        // Recuperar nutricionista de sesión
        NutricionistaDTO nutricionistaDTO = (NutricionistaDTO) session.getAttribute("nutricionistaLogueado");

        // Si no hay nutricionista en sesión, crear uno temporal con el ID
        if (nutricionistaDTO == null) {
            nutricionistaDTO = new NutricionistaDTO();
            nutricionistaDTO.setId(idNutricionista);
        }

        // Cargar datos del nutricionista
        nutricionistaDTO = nutricionistaService.findById(nutricionistaDTO);

        // Si sigue siendo null → acceso no permitido
        if (nutricionistaDTO == null) {
            log.warn("Intento de acceso al dashboard sin sesión. Redirigiendo a login.");
            return new ModelAndView("redirect:/login-nutricionista?error=Debes iniciar sesión");
        }

        // Cargar la vista
        ModelAndView mav = new ModelAndView("nutricionista/dashboard");
        mav.addObject("nutricionistaDTO", nutricionistaDTO);
        mav.addObject("titulo", "Panel del Nutricionista");

        return mav;
    }
    
    @GetMapping("/nutricionista/{idNutricionista}/menus")
    public ModelAndView seleccionarClienteParaMenus(@PathVariable("idNutricionista") Long idNutricionista,
                                                    HttpSession session) {

        log.info("Mostrar selección de cliente para crear menús");

        NutricionistaDTO nutricionistaDTO = (NutricionistaDTO) session.getAttribute("nutricionistaLogueado");

        if (nutricionistaDTO == null) {
            nutricionistaDTO = new NutricionistaDTO();
            nutricionistaDTO.setId(idNutricionista);
        }

        nutricionistaDTO = nutricionistaService.findById(nutricionistaDTO);

        // clientes asignados al nutricionista
        List<ClienteDTO> listaClientesDTO = nutricionistaDTO.getListaClientesDTO();

        ModelAndView mav = new ModelAndView("nutricionista/seleccionar-cliente-menu");
        mav.addObject("nutricionistaDTO", nutricionistaDTO);
        mav.addObject("listaClientesDTO", listaClientesDTO);

        return mav;
    }
    
    @GetMapping("/nutricionista/{idNutricionista}/citas")
    public ModelAndView seleccionarClienteParaCitas(@PathVariable("idNutricionista") Long idNutricionista,
                                                    HttpSession session) {

        log.info("NutricionistaDashboardController - seleccionarClienteParaCitas: Mostrar selección de cliente para gestionar citas");

        // Recuperar nutricionista de sesión
        NutricionistaDTO nutricionistaDTO = (NutricionistaDTO) session.getAttribute("nutricionistaLogueado");

        if (nutricionistaDTO == null) {
            nutricionistaDTO = new NutricionistaDTO();
            nutricionistaDTO.setId(idNutricionista);
        }

        // Cargar datos completos del nutricionista
        nutricionistaDTO = nutricionistaService.findById(nutricionistaDTO);

        // Obtener lista de clientes asignados
        List<ClienteDTO> listaClientesDTO = nutricionistaDTO.getListaClientesDTO();

        ModelAndView mav = new ModelAndView("nutricionista/seleccionar-cliente-citas");
        mav.addObject("nutricionistaDTO", nutricionistaDTO);
        mav.addObject("listaClientesDTO", listaClientesDTO);

        return mav;
    }



}

