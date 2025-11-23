package com.example.demo.model.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import org.springframework.format.annotation.DateTimeFormat;

import com.example.demo.repository.entity.Cliente;
import com.example.demo.repository.entity.Plan;

import lombok.Data;
import lombok.ToString;

@Data
public class ClienteDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String nombre;
	private String apellido;
	private String email;
	private String password;
	
	@ToString.Exclude
	private EvaluacionDTO evaluacionDTO;

	
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private Date fechaRegistro;	
	
	@ToString.Exclude
    private PlanDTO planDTO;
	
	
	public static ClienteDTO convertToDTO(Cliente cliente, PlanDTO plan) {


		ClienteDTO clienteDTO = new ClienteDTO();

		clienteDTO.setId(cliente.getId());
		clienteDTO.setNombre(cliente.getNombre());
		clienteDTO.setApellido(cliente.getApellido());
		clienteDTO.setEmail(cliente.getEmail());
		clienteDTO.setPassword(cliente.getPassword());
		clienteDTO.setFechaRegistro(cliente.getFechaRegistro());
		
		clienteDTO.setEvaluacionDTO(EvaluacionDTO.converToDTO(cliente.getEvaluacion(), clienteDTO));

		 if (plan != null) {
	            clienteDTO.setPlanDTO(plan);
	        } else {
	            clienteDTO.setPlanDTO(new PlanDTO());
	        }
		

		return clienteDTO;
	}

	public static Cliente convertToEntity(ClienteDTO clienteDTO, Plan plan) {

		Cliente cliente = new Cliente();
		cliente.setId(clienteDTO.getId());
		cliente.setNombre(clienteDTO.getNombre());
		cliente.setApellido(clienteDTO.getApellido());
		cliente.setEmail(clienteDTO.getEmail());
		cliente.setPassword(clienteDTO.getPassword());
		cliente.setFechaRegistro(clienteDTO.getFechaRegistro());
		
		cliente.setEvaluacion(EvaluacionDTO.converToEntity(clienteDTO.getEvaluacionDTO(), cliente));

        // Establecer el plan para el cliente
        cliente.setPlan(plan);
        
		return cliente;
	}

	public ClienteDTO() {
		super();
		this.planDTO = new PlanDTO();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClienteDTO other = (ClienteDTO) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

}
