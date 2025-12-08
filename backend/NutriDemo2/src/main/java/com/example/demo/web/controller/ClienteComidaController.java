package com.example.demo.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.dto.ClienteDTO;
import com.example.demo.model.dto.MenuDTO;
import com.example.demo.service.ComidaService;
import com.example.demo.service.MenuService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ClienteComidaController {

    private static final Logger log = LoggerFactory.getLogger(ClienteComidaController.class);

    @Autowired
    private MenuService menuService;

    @Autowired
    private ComidaService comidaService;

    // Listar comidas del menú para el cliente
    @GetMapping("/cliente/{idCliente}/menus/{idMenu}/comidas")
    public ModelAndView verComidas(@PathVariable Long idCliente,
                                   @PathVariable Long idMenu,
                                   HttpSession session) {

        log.info("ClienteComidaController - verComidas: Cliente {} visualiza menú {}", idCliente, idMenu);

        ClienteDTO clienteSesion = (ClienteDTO) session.getAttribute("clienteLogueado");

        if (clienteSesion == null || !clienteSesion.getId().equals(idCliente)) {
            return new ModelAndView("redirect:/login");
        }

        // Cargar menú completo
        MenuDTO menuDTO = new MenuDTO();
        menuDTO.setId(idMenu);
        menuDTO = menuService.findByMenuId(menuDTO);

        ModelAndView mav = new ModelAndView("cliente/comidas");
        mav.addObject("clienteDTO", clienteSesion);
        mav.addObject("menuDTO", menuDTO);
        mav.addObject("listaComidasDTO", comidaService.findAllByMenu(menuDTO));

        return mav;
    }

    // Cliente marca/desmarca hecha
    @GetMapping("/cliente/{idCliente}/menus/{idMenu}/comidas/{idComida}/toggle")
    public ModelAndView marcarHecha(@PathVariable Long idCliente,
                                    @PathVariable Long idMenu,
                                    @PathVariable Long idComida,
                                    HttpSession session) {

        ClienteDTO clienteSesion = (ClienteDTO) session.getAttribute("clienteLogueado");

        if (clienteSesion == null || !clienteSesion.getId().equals(idCliente)) {
            return new ModelAndView("redirect:/login");
        }

        comidaService.toggleHecha(idComida);

        return new ModelAndView("redirect:/cliente/" + idCliente + "/menus/" + idMenu + "/comidas");
    }
}
