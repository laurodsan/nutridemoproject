package com.example.demo.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PlanesController {

    private static final Logger log = LoggerFactory.getLogger(PlanesController.class);

    @GetMapping("/planes")
    public ModelAndView mostrarPlanes() {

        log.info("PlanesController - mostrarPlanes: Mostrar página de planes");

        ModelAndView mav = new ModelAndView("planes"); // templates/planes.html
        mav.addObject("titulo", "Planes NutriDemo");

        return mav;
    }
}

