package com.example.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.dto.ClienteDTO;
import com.example.demo.model.dto.EvaluacionDTO;
import com.example.demo.repository.dao.EvaluacionRepository;
import com.example.demo.repository.entity.Cliente;
import com.example.demo.repository.entity.Evaluacion;

@Service
public class EvaluacionServiceImpl implements EvaluacionService{
	
	private static final Logger log = LoggerFactory.getLogger(EvaluacionServiceImpl.class);
	
	@Autowired
	private EvaluacionRepository evaluacionRepository;
	
	@Autowired
	private ClienteService clienteService;

	@Override
	public void save(EvaluacionDTO evaluacionDTO) {
		
		log.info("EvaluacionServiceImpl - save: Salva la evaluacion: " + evaluacionDTO + " del cliente "
				+ evaluacionDTO.getClienteDTO().getId());
		
		Cliente cliente = new Cliente();
		cliente.setId(evaluacionDTO.getClienteDTO().getId());
		
		Evaluacion evaluacion = EvaluacionDTO.converToEntity(evaluacionDTO, cliente);
		
		evaluacionRepository.save(evaluacion);
		
	}

	@Override
	public EvaluacionDTO findByClienteId(Long idCliente) {

	    log.info("EvaluacionServiceImpl - findByClienteId: Buscar evaluación del cliente: " + idCliente);

	    Evaluacion evaluacion = evaluacionRepository.findByClienteId(idCliente);

	    if (evaluacion != null) {

	        ClienteDTO clienteDTO = clienteService.findById(
	                new ClienteDTO() {{
	                    setId(idCliente);
	                }}
	        );

	        EvaluacionDTO evaluacionDTO = EvaluacionDTO.converToDTO(evaluacion, clienteDTO);

	        return evaluacionDTO;
	    } 
	  
	    return null;
	}



}
