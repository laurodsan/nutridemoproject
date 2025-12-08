package com.example.demo.web.controller;

import java.util.Date;

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
import com.example.demo.model.dto.EvaluacionDTO;
import com.example.demo.service.ClienteService;
import com.example.demo.service.EvaluacionService;

@Controller
public class EvaluacionController {
    
    private static final Logger log = LoggerFactory.getLogger(EvaluacionController.class);
    
    @Autowired
    private EvaluacionService evaluacionService;

    @Autowired
    private ClienteService clienteService;
    
    // Acceder a la evaluación: crea o muestra según exista
    @GetMapping("/cliente/dashboard/{idCliente}/evaluacion")
    public ModelAndView accederEvaluacion(@PathVariable("idCliente") Long idCliente) {

        log.info("EvaluacionController - accederEvaluacion: Cliente " + idCliente);

        EvaluacionDTO evaluacionDTO = evaluacionService.findByClienteId(idCliente);

        if (evaluacionDTO == null) {
            // No existe evaluación → formulario
            ClienteDTO clienteDTO = new ClienteDTO();
            clienteDTO.setId(idCliente);

            evaluacionDTO = new EvaluacionDTO();
            evaluacionDTO.setClienteDTO(clienteDTO);

            ModelAndView mav = new ModelAndView("cliente/evaluacionform");
            mav.addObject("evaluacionDTO", evaluacionDTO);
            mav.addObject("add", true); 
            return mav;

        } else {
            // Ya existe evaluación → mostrar solo lectura

            // 🟩 Recuperar cliente completo
            ClienteDTO clienteDTO = evaluacionDTO.getClienteDTO();
            clienteDTO = clienteService.findById(clienteDTO);

            ModelAndView mav = new ModelAndView("cliente/evaluacionshow");
            mav.addObject("evaluacionDTO", evaluacionDTO);
            mav.addObject("clienteDTO", clienteDTO);
            return mav;
        }
    }

    @PostMapping("/cliente/dashboard/{idCliente}/evaluacion/save")
    public ModelAndView saveEvaluacion(@PathVariable("idCliente") Long idCliente,
                                       @ModelAttribute("evaluacionDTO") EvaluacionDTO evaluacionDTO) {

        log.info("EvaluacionController - save: Guardar evaluación del cliente " + idCliente);
        
        if (evaluacionDTO.getFecha() == null) {
            evaluacionDTO.setFecha(new Date());
        }

        evaluacionDTO.getClienteDTO().setId(idCliente);

        evaluacionService.save(evaluacionDTO);

        return new ModelAndView("redirect:/cliente/dashboard/" + idCliente + "/evaluacion");
    }
}

