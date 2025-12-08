package com.example.demo.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.dto.ClienteDTO;
import com.example.demo.model.dto.ProgresoDTO;
import com.example.demo.repository.dao.ProgresoRepository;
import com.example.demo.repository.entity.Cliente;
import com.example.demo.repository.entity.Progreso;

@Service
public class ProgresoServiceImpl implements ProgresoService{
	
	private static final Logger log = LoggerFactory.getLogger(ProgresoServiceImpl.class);
	
	@Autowired
	private ProgresoRepository progresoRepository;

	@Override
	public List<ProgresoDTO> findAllByCliente(ClienteDTO clienteDTO) {
		
		log.info("ProgresoServiceImpl - findAllByCliente: Lista de todas los progresos del cliente: "
				+ clienteDTO.getId());

		List<Progreso> lista = (List<Progreso>) progresoRepository.findAllByCliente(clienteDTO.getId());

		List<ProgresoDTO> listaResultadoDTO = new ArrayList<ProgresoDTO>();

		for (int i = 0; i < lista.size(); ++i) {
			listaResultadoDTO.add(ProgresoDTO.convertToDTO(lista.get(i), clienteDTO));
		}

		return listaResultadoDTO;		
	
	}

	@Override
	public void save(ProgresoDTO progresoDTO) {

	    log.info("ProgresoServiceImpl - save: Guardando progreso del cliente "
	            + progresoDTO.getClienteDTO().getId());

	    Cliente cliente = new Cliente();
	    cliente.setId(progresoDTO.getClienteDTO().getId());

	    //Convertir java.util.Date a LocalDate
	    if (progresoDTO.getFecha() == null) {
	        throw new IllegalArgumentException("La fecha del progreso no puede ser nula");
	    }

	    LocalDate fecha = progresoDTO.getFecha().toInstant()
	            .atZone(ZoneId.systemDefault())
	            .toLocalDate();

	    int anio = fecha.getYear();
	    int semana = fecha.get(WeekFields.ISO.weekOfWeekBasedYear());

	    //Validamos si ya existe un progreso esa semana (solo si es nuevo)
	    List<Progreso> yaExiste = progresoRepository.findByClienteAndWeek(
	            cliente.getId(), anio, semana);

	    if (!yaExiste.isEmpty() && progresoDTO.getId() == null) {
	        throw new IllegalArgumentException("Ya existe un progreso registrado para esta semana");
	    }

	    //Convertir a entidad y guardar
	    Progreso progreso = ProgresoDTO.convertToEntity(progresoDTO, cliente);

	    progresoRepository.save(progreso);
	}

}
