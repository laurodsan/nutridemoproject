package com.example.demo.model.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.example.demo.repository.entity.Cliente;
import com.example.demo.repository.entity.Nutricionista;
import com.example.demo.repository.entity.Plan;

import lombok.Data;
import lombok.ToString;

@Data
public class NutricionistaDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Long id;

	private String nombre;
	private String email;
	private String password;
	private String especialidad;
	
	@ToString.Exclude
	private List<ClienteDTO> listaClientesDTO;
	
	public static NutricionistaDTO convertToDTO(Nutricionista nutricionista) {

		NutricionistaDTO nutricionistaDTO = new NutricionistaDTO();

		nutricionistaDTO.setId(nutricionista.getId());
		nutricionistaDTO.setNombre(nutricionista.getNombre());
		nutricionistaDTO.setEmail(nutricionista.getEmail());
		nutricionistaDTO.setPassword(nutricionista.getPassword());
		nutricionistaDTO.setEspecialidad(nutricionista.getEspecialidad());

		// Aseguramos que la listaClientesDTO esté inicializada
		if (nutricionistaDTO.getListaClientesDTO() == null) {
			nutricionistaDTO.setListaClientesDTO(new ArrayList<>());
		}

		// Cargamos la lista de clientes, que como es un HashSet hemos de convertirla a
		// ArrayList
		
		
		 List<Cliente> listaClientes = new ArrayList<>(nutricionista.getListaClientes());
		    for (Cliente cliente : listaClientes) {
		        // Pasamos planDTO si existe
		        PlanDTO planDTO = cliente.getPlan() != null ? PlanDTO.convertToDTO(cliente.getPlan()) : null;
		        ClienteDTO clientedto = ClienteDTO.convertToDTO(cliente, nutricionistaDTO, planDTO);
		        nutricionistaDTO.getListaClientesDTO().add(clientedto);
		    }
		
		return nutricionistaDTO;

	}
	
	public static Nutricionista convertToEntity(NutricionistaDTO nutricionistaDTO) {

		Nutricionista nutricionista = new Nutricionista();
		
		nutricionista.setId(nutricionistaDTO.getId());
		nutricionista.setNombre(nutricionistaDTO.getNombre());
		nutricionista.setEmail(nutricionistaDTO.getEmail());
		nutricionista.setPassword(nutricionistaDTO.getPassword());
		nutricionista.setEspecialidad(nutricionistaDTO.getEspecialidad());


		// CARGAR LISTA DE CLIENTES
		
	    for (ClienteDTO clienteDTO : nutricionistaDTO.getListaClientesDTO()) {
	        Plan plan = clienteDTO.getPlanDTO() != null ? PlanDTO.convertToEntity(clienteDTO.getPlanDTO()) : null;
	        Cliente cliente = ClienteDTO.convertToEntity(clienteDTO, nutricionista, plan);
	        nutricionista.getListaClientes().add(cliente);
	    }

		return nutricionista;

	}
	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NutricionistaDTO other = (NutricionistaDTO) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	public NutricionistaDTO() {
		super();
		this.listaClientesDTO = new ArrayList<ClienteDTO>();
	}

	
}
