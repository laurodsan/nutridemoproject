package com.example.demo.service;

import java.util.List;

import com.example.demo.model.dto.ComidaDTO;
import com.example.demo.model.dto.MenuDTO;

public interface ComidaService {

	List<ComidaDTO> findAllByMenu(MenuDTO menuDTO);

	List<ComidaDTO> findAllByMenuAndDia(MenuDTO menuDTO, String diaSemana);

	void save(ComidaDTO comidaDTO);

	ComidaDTO findByComidaId(ComidaDTO comidaDTO);

	void delete(ComidaDTO comidaDTO);

	void toggleHecha(Long idComida);

}
