package com.example.demo.service;

import java.util.List;

import com.example.demo.model.dto.ClienteDTO;
import com.example.demo.model.dto.ProgresoDTO;

public interface ProgresoService {

	List<ProgresoDTO> findAllByCliente(ClienteDTO clienteDTO);

	void save(ProgresoDTO progresoDTO);

}
