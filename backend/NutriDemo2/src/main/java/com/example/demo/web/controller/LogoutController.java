package com.example.demo.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class LogoutController {

    private static final Logger log = LoggerFactory.getLogger(LogoutController.class);

    @GetMapping("/logout")
    public String logout(HttpSession session) {

        log.info("LogoutController - logout: Cerrando sesión");

        // Eliminar TODOS los datos de sesión
        session.invalidate();

        // Redirigir al login
        return "redirect:/login";
    }
}

