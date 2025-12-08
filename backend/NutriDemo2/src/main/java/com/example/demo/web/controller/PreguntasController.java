package com.example.demo.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PreguntasController {

    private static final Logger log = LoggerFactory.getLogger(PreguntasController.class);

    @GetMapping("/preguntas")
    public ModelAndView preguntas() {
        log.info("PreguntasController - preguntas: Mostrar la página de Preguntas frecuentes");

        ModelAndView mav = new ModelAndView("preguntas"); 
        mav.addObject("titulo", "Preguntas frecuentes");

        return mav;
    }

}

