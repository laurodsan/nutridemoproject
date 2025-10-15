package com.example.demo.service;

import java.util.List;

import com.example.demo.model.dto.ClienteDTO;
import com.example.demo.model.dto.MenuDTO;

public interface MenuService {

	List<MenuDTO> findAllByCliente(ClienteDTO clienteDTO);

}
