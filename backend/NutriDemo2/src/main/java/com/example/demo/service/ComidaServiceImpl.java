package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.dto.ClienteDTO;
import com.example.demo.model.dto.ComidaDTO;
import com.example.demo.model.dto.MenuDTO;
import com.example.demo.repository.dao.ComidaRepository;
import com.example.demo.repository.entity.Cliente;
import com.example.demo.repository.entity.Comida;
import com.example.demo.repository.entity.Menu;


@Service
public class ComidaServiceImpl implements ComidaService{
	
	private static final Logger log = LoggerFactory.getLogger(ComidaServiceImpl.class);

	@Autowired
	private ComidaRepository comidaRepository;
	
	@Override
	public List<ComidaDTO> findAllByMenu(MenuDTO menuDTO) {
		
		log.info("ComidaServiceImpl - findAllByMenu: Lista de todas las comidas del menu: "
				+ menuDTO.getId());

		List<Comida> lista = (List<Comida>) comidaRepository.findAllByMenu(menuDTO.getId());

		List<ComidaDTO> listaResultadoDTO = new ArrayList<ComidaDTO>();

		for (int i = 0; i < lista.size(); ++i) {
			listaResultadoDTO.add(ComidaDTO.convertToDTO(lista.get(i), menuDTO));
		}

		return listaResultadoDTO;
		
	}

	@Override
	public List<ComidaDTO> findAllByMenuAndDia(MenuDTO menuDTO, String diaSemana) {

	    List<Comida> lista = comidaRepository.findByMenuIdAndDiaSemana(
	            menuDTO.getId(),
	            diaSemana
	    );

	    List<ComidaDTO> resultado = new ArrayList<>();

	    for (Comida c : lista) {
	        resultado.add(ComidaDTO.convertToDTO(c, menuDTO));
	    }

	    return resultado;
	}

	@Override
	public void save(ComidaDTO comidaDTO) {
	
		log.info("ComidaServiceImpl - save: Salva la comida: " + comidaDTO + " del menu "
				+ comidaDTO.getMenuDTO().getId());

		Menu menu = new Menu();
		menu.setId(comidaDTO.getMenuDTO().getId());

		Comida comida = ComidaDTO.convertToEntity(comidaDTO, menu);

		comidaRepository.save(comida);
		
	}

	@Override
	public ComidaDTO findByComidaId(ComidaDTO comidaDTO) {

	    log.info("ComidaServiceImpl - findByComidaId: Obtener la comida con id: " + comidaDTO.getId());

	    Optional<Comida> comidaOpt = comidaRepository.findById(comidaDTO.getId());

	    if (comidaOpt.isEmpty()) {
	        return null;
	    }

	    Comida comida = comidaOpt.get();

	    // --- Convertir Cliente a ClienteDTO ---
	    Cliente cliente = comida.getMenu().getCliente();
	    ClienteDTO clienteDTO = ClienteDTO.convertToDTO(cliente, null); // null porque NO hay plan asociado aquí

	    // --- Convertir Menu a MenuDTO ---
	    Menu menu = comida.getMenu();
	    MenuDTO menuDTO = MenuDTO.convertToDTO(menu, clienteDTO);

	    // --- Convertir Comida a ComidaDTO ---
	    return ComidaDTO.convertToDTO(comida, menuDTO);
	}

	@Override
	public void delete(ComidaDTO comidaDTO) {
		
	
		log.info("ComidaServiceImpl - delete: Elimina la comida: " + comidaDTO.getId());

		comidaRepository.deleteById(comidaDTO.getId());
	}

	//Marcar hecho
	@Override
	public void toggleHecha(Long idComida) {

	    log.info("ComidaServiceImpl - toggleHecha: Cambiando estado 'hecha' de la comida {}", idComida);

	    comidaRepository.findById(idComida).ifPresentOrElse(comida -> {

	        Boolean estadoActual = comida.getHecha();
	        // si es null lo tratamos como false
	        boolean nuevoEstado = (estadoActual == null) ? true : !estadoActual;

	        comida.setHecha(nuevoEstado);
	        comidaRepository.save(comida);

	        log.info("ComidaServiceImpl - toggleHecha: Nuevo estado hecha = {} para comida {}", nuevoEstado, idComida);

	    }, () -> {
	        log.warn("ComidaServiceImpl - toggleHecha: No se encontró comida con id {}", idComida);
	    });
	}



}
