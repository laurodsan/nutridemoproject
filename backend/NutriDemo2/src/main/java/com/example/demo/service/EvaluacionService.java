package com.example.demo.service;

import com.example.demo.model.dto.EvaluacionDTO;

public interface EvaluacionService {

	void save(EvaluacionDTO evaluacionDTO);

	EvaluacionDTO findByClienteId(Long idCliente);

}
