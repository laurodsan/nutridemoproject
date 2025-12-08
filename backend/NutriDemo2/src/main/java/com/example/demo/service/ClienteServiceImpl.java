package com.example.demo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.dto.ClienteDTO;
import com.example.demo.model.dto.NutricionistaDTO;
import com.example.demo.model.dto.PlanDTO;
import com.example.demo.repository.dao.ClienteRepository;
import com.example.demo.repository.dao.EvaluacionRepository;
import com.example.demo.repository.dao.NutricionistaRepository;
import com.example.demo.repository.dao.PlanRepository;
import com.example.demo.repository.entity.Cliente;
import com.example.demo.repository.entity.Evaluacion;
import com.example.demo.repository.entity.Nutricionista;
import com.example.demo.repository.entity.Plan;

@Service
public class ClienteServiceImpl implements ClienteService{
	
	private static final Logger log = LoggerFactory.getLogger(ClienteServiceImpl.class);
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private PlanRepository planRepository;
	
	@Autowired
	private NutricionistaRepository nutricionistaRepository;

	@Autowired
	private EvaluacionRepository evaluacionRepository;


	@Override
	public ClienteDTO save(ClienteDTO clienteDTO) {

	    log.info("ClienteServiceImpl - save: Salva el cliente " + clienteDTO);
	    
	    // Asignar fecha si viene vacía
	    if (clienteDTO.getFechaRegistro() == null) {
	        clienteDTO.setFechaRegistro(new Date());
	    }

	    // Buscar el plan en la BD
	    Plan plan = planRepository.findById(clienteDTO.getPlanDTO().getId())
	            .orElseThrow(() -> new RuntimeException("El plan seleccionado no existe"));
		plan.setId(clienteDTO.getPlanDTO().getId());

		Cliente cliente = ClienteDTO.convertToEntity(clienteDTO, plan);

		clienteRepository.save(cliente);
		
		return clienteDTO;
	}


	@Override
	public ClienteDTO loginCliente(String email, String password) {

	    log.info("ClienteServiceImpl - loginCliente: Intentando login para correo: " + email);

	    Optional<Cliente> opt = clienteRepository.findByEmail(email);

	    if (opt.isPresent()) {
	        Cliente cliente = opt.get();

	        // Comparar password
	        if (password.equals(cliente.getPassword())) {

	            // Crear DTO completo del cliente
	            ClienteDTO clienteDTO = new ClienteDTO();
	            clienteDTO.setId(cliente.getId());
	            clienteDTO.setNombre(cliente.getNombre());
	            clienteDTO.setApellido(cliente.getApellido());
	            clienteDTO.setEmail(cliente.getEmail());
	            clienteDTO.setPassword(cliente.getPassword());
	            clienteDTO.setFechaRegistro(cliente.getFechaRegistro());
	            return clienteDTO; 
	        }
	    }

	    return null;
	}


	@Override
	public PlanDTO findPlanById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PlanDTO> findAllPlanes() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ClienteDTO findById(ClienteDTO clienteDTO) {

	    log.info("ClienteServiceImpl - findById: Mostrar cliente por id: " + clienteDTO.getId());

	    Optional<Cliente> clienteOpt = clienteRepository.findById(clienteDTO.getId());

	    if (clienteOpt.isPresent()) {
	        Cliente cliente = clienteOpt.get();

	        // Convertir plan a DTO si existe
	        Plan plan = cliente.getPlan();
	        PlanDTO planDTO = plan != null ? PlanDTO.convertToDTO(plan) : null;

	        // Convertir cliente a DTO completo
	        clienteDTO = ClienteDTO.convertToDTO(cliente, planDTO);
	        return clienteDTO;
	    } else {
	        return null;
	    }
	}

	
	@Override
	public ClienteDTO asignarNutricionista(ClienteDTO clienteDTO) {

	    log.info("Asignando nutricionista a cliente ID=" + clienteDTO.getId());

	    Cliente cliente = clienteRepository.findById(clienteDTO.getId())
	            .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

	    // 1. Si ya tiene nutricionista → no asignar otro
	    if (cliente.getNutricionista() != null) {
	        log.info("Cliente ya tiene nutricionista asignado");
	        return ClienteDTO.convertToDTO(cliente, clienteDTO.getPlanDTO());
	    }
	    
	 // 2. Obtener restricciones desde Evaluación Inicial
	    Evaluacion evaluacion = evaluacionRepository.findByClienteId(cliente.getId());
	    if (evaluacion == null) {
	        throw new RuntimeException("El cliente no ha rellenado su Evaluación Inicial");
	    }

	    String restriccion = evaluacion.getRestricciones(); 
	    log.info("Restricción del cliente: " + restriccion);

	    Nutricionista nutricionistaSeleccionado = null;

	    // 3A. Si es alto rendimiento → nutricionista deportivo
	    if ("rendimiento".equalsIgnoreCase(restriccion)) {
	        nutricionistaSeleccionado = nutricionistaRepository.findFirstByEspecialidad("deportiva");
	    }
	    
	 // 3B. Si es vegana o vegetariana → nutricionista vegano
	    else if ("vegana".equalsIgnoreCase(restriccion) || 
	             "vegetariana".equalsIgnoreCase(restriccion)) {
	        nutricionistaSeleccionado = nutricionistaRepository.findFirstByEspecialidad("vegana");
	    }

	    // 3C. Si siguen siendo null → asignar nutricionista con menos clientes
	    if (nutricionistaSeleccionado == null) {
	        nutricionistaSeleccionado = nutricionistaRepository.findNutricionistaConMenosClientes();
	    }

	    if (nutricionistaSeleccionado == null) {
	        throw new RuntimeException("No se encontró nutricionista disponible");
	    }

	    log.info("Nutricionista seleccionado: " + nutricionistaSeleccionado.getNombre());

	    // 4. Asignar nutricionista y guardar
	    cliente.setNutricionista(nutricionistaSeleccionado);
	    clienteRepository.save(cliente);

	    // 5. Devolver DTO actualizado
	    return ClienteDTO.convertToDTO(cliente, clienteDTO.getPlanDTO());
	}


	@Override
	public List<ClienteDTO> findAllByNutricionista(NutricionistaDTO nutricionistaDTO) {

	    log.info("ClienteServiceImpl - findAllByNutricionista: Lista de todos los clientes del nutricionista: "
	            + nutricionistaDTO.getId());

	    List<Cliente> lista = clienteRepository.findAllByNutricionista(nutricionistaDTO.getId());

	    List<ClienteDTO> listaResultadoDTO = new ArrayList<>();

	    for (Cliente c : lista) {
	        // Pasamos el plan del cliente al convertir a DTO
	        PlanDTO planDTO = c.getPlan() != null ? PlanDTO.convertToDTO(c.getPlan()) : new PlanDTO();
	        listaResultadoDTO.add(ClienteDTO.convertToDTO(c, planDTO));
	    }

	    return listaResultadoDTO;
	}



}
