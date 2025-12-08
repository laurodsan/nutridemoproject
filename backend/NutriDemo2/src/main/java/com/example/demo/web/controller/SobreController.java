package com.example.demo.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SobreController {

    private static final Logger log = LoggerFactory.getLogger(SobreController.class);

    @GetMapping("/sobre")
    public ModelAndView sobre() {
        log.info("SobreController - sobre: Mostrar la página Sobre nosotros");

        ModelAndView mav = new ModelAndView("sobre");
        mav.addObject("titulo", "Sobre NutriDemo");

        return mav;
    }
}

