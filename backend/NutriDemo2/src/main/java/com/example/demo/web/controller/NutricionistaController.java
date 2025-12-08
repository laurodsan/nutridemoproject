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
import com.example.demo.model.dto.PlanDTO;
import com.example.demo.service.NutricionistaService;

import jakarta.servlet.http.HttpSession;

@Controller
public class NutricionistaController {

    @Autowired
    private NutricionistaService nutricionistaService;

    private static final Logger log = LoggerFactory.getLogger(NutricionistaController.class);

    // Dar de alta un nutricionista
    @GetMapping("/registro/nutricionista")
    public ModelAndView add() {
        log.info("NutricionistaController - add: Añadimos un nutricionista nuevo");

        NutricionistaDTO nutricionistaDTO = new NutricionistaDTO();
        
     // Traer lista de planes disponibles
        List<ClienteDTO> listaClientes = nutricionistaService.findAllClientes();

        ModelAndView mav = new ModelAndView("registro"); 
        mav.addObject("nutricionistaDTO", nutricionistaDTO);
        mav.addObject("listaClientes", listaClientes);
        mav.addObject("add", true);

        return mav;
    }

 // Mostrar formulario edición nutricionista
    @GetMapping("/nutricionista/dashboard/{id}/update")
    public ModelAndView update(@PathVariable("id") Long id, HttpSession session) {
        
        log.info("NutricionistaController - update: Editar nutricionista {}", id);

        NutricionistaDTO sesionNutri = (NutricionistaDTO) session.getAttribute("nutricionistaLogueado");

        if (sesionNutri == null) {
            return new ModelAndView("redirect:/login");
        }

        NutricionistaDTO nutricionistaDTO = nutricionistaService.findById(sesionNutri);

        ModelAndView mav = new ModelAndView("nutricionista/nutricionistaform");
        mav.addObject("nutricionistaDTO", nutricionistaDTO);
        mav.addObject("add", false);

        return mav;
    }



    // Guardar cambios nutricionista
    @PostMapping("/nutricionista/dashboard/{id}/save")
    public ModelAndView save(
            @PathVariable("id") Long id,
            @ModelAttribute("nutricionistaDTO") NutricionistaDTO nutricionistaDTO,
            HttpSession session) {

        log.info("NutricionistaController - save: Guardar / actualizar nutricionista {}", id);

        NutricionistaDTO sesionNutri = (NutricionistaDTO) session.getAttribute("nutricionistaLogueado");

        if (sesionNutri == null) {
            return new ModelAndView("redirect:/login");
        }

        nutricionistaDTO.setId(sesionNutri.getId());

        NutricionistaDTO guardado = nutricionistaService.save(nutricionistaDTO);

        session.setAttribute("nutricionistaLogueado", guardado);

        return new ModelAndView("redirect:/nutricionista/dashboard/" + guardado.getId());
    }

}
