package com.example.demo.service;

import java.util.List;

import com.example.demo.model.dto.CitaDTO;
import com.example.demo.model.dto.ClienteDTO;
import com.example.demo.model.dto.NutricionistaDTO;

public interface CitaService {

	List<CitaDTO> findAllByCliente(ClienteDTO clienteDTO);

	void save(CitaDTO citaDTO);

	List<CitaDTO> findAllByNutricionista(NutricionistaDTO nutricionistaDTO);

	void updateEstado(Long idCita, String estado);

}
