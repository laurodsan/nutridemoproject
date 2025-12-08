package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.dto.CitaDTO;
import com.example.demo.model.dto.ClienteDTO;
import com.example.demo.model.dto.NutricionistaDTO;
import com.example.demo.repository.dao.CitaRepository;
import com.example.demo.repository.dao.ClienteRepository;
import com.example.demo.repository.entity.Cita;
import com.example.demo.repository.entity.Cliente;
import com.example.demo.repository.entity.Nutricionista;

@Service
public class CitaServiceImpl implements CitaService{

	private static final Logger log = LoggerFactory.getLogger(CitaServiceImpl.class);

    @Autowired
    private CitaRepository citaRepository;
    
	@Autowired
	private ClienteRepository clienteRepository;
    
	@Override
	public List<CitaDTO> findAllByCliente(ClienteDTO clienteDTO) {
		
        log.info("CitaServiceImpl - findAllByCliente: Listar citas del cliente {}", clienteDTO.getId());

        // Buscar en BD citas del cliente
        List<Cita> lista = citaRepository.findAllByCliente(clienteDTO.getId());

        // Convertir entidades a DTO
        List<CitaDTO> listaResultadoDTO = new ArrayList<>();

        for (Cita cita : lista) {
            listaResultadoDTO.add(CitaDTO.convertToDTO(cita, clienteDTO, clienteDTO.getNutricionistaDTO()));
        }

        return listaResultadoDTO;
	}

	@Override
	public void save(CitaDTO citaDTO) {

	    Long idCliente = citaDTO.getClienteDTO().getId();

	    log.info("CitaServiceImpl - save: Salva la cita: {}, cliente {}",
	            citaDTO, idCliente);

	    Cliente cliente = clienteRepository.findById(idCliente)
	            .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

	    Nutricionista nutricionista = cliente.getNutricionista();
	    if (nutricionista == null) {
	        throw new RuntimeException("El cliente no tiene nutricionista asignado");
	    }

	    int citasPermitidas = cliente.getPlan().getCitasIncluidas();

	    // Si el plan no incluye citas → bloquear
	    if (citasPermitidas == 0) {
	        throw new RuntimeException("Tu plan no incluye citas.");
	    }

	    Long citasRealizadas = citaRepository.countByCliente(idCliente);

	    if (citasRealizadas >= citasPermitidas) {
	        throw new RuntimeException(
	                "Has alcanzado el número máximo de citas permitido (" + citasPermitidas + ")."
	        );
	    }

	    Cita cita = CitaDTO.convertToEntity(citaDTO, cliente, nutricionista);

	    citaRepository.save(cita);
	}

	@Override
	public List<CitaDTO> findAllByNutricionista(NutricionistaDTO nutricionistaDTO) {

	    Long idNutricionista = nutricionistaDTO.getId();

	    log.info("CitaServiceImpl - findAllByNutricionista: Listar citas del nutricionista {}", idNutricionista);

	    // Buscar en BD citas del nutricionista
	    List<Cita> lista = citaRepository.findAllByNutricionista(idNutricionista);

	    // Convertir entidades a DTO
	    List<CitaDTO> listaResultadoDTO = new ArrayList<>();

	    for (Cita cita : lista) {
	        // Aquí usamos convertToDTO
	        ClienteDTO clienteDTO = ClienteDTO.convertToDTO(cita.getCliente(), null);
	        listaResultadoDTO.add(
	            CitaDTO.convertToDTO(cita, clienteDTO, nutricionistaDTO)
	        );
	    }

	    return listaResultadoDTO;
	}

	@Override
	public void updateEstado(Long idCita, String estado) {

	    Cita cita = citaRepository.findById(idCita)
	            .orElseThrow(() -> new RuntimeException("Cita no encontrada"));

	    cita.setEstado(estado);

	    citaRepository.save(cita);
	}



}
