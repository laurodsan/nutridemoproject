package com.example.demo.web.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.demo.model.dto.ClienteDTO;
import com.example.demo.model.dto.NutricionistaDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class SessionInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(SessionInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        String uri = request.getRequestURI();
        log.info("SessionInterceptor - preHandle: URI solicitada {}", uri);

        // Obtenemos la sesión SIN crear una nueva si no existe
        HttpSession session = request.getSession(false);

        ClienteDTO clienteLogueado = null;
        NutricionistaDTO nutricionistaLogueado = null;

        if (session != null) {
            clienteLogueado = (ClienteDTO) session.getAttribute("clienteLogueado");
            nutricionistaLogueado = (NutricionistaDTO) session.getAttribute("nutricionistaLogueado");
        }

        // Si no hay NADIE logueado → redirigimos a /login
        if (clienteLogueado == null && nutricionistaLogueado == null) {
            log.info("SessionInterceptor - Usuario no logueado. Redirigiendo a /login");

            // Muy importante usar getContextPath() por tu /nutridemo
            response.sendRedirect(request.getContextPath() + "/login");
            return false; // corta la ejecución del controlador
        }

        // Si hay usuario en sesión → dejar pasar
        return true;
    }
}

