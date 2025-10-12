package com.example.demo.model.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.demo.repository.entity.Cliente;

import lombok.Data;

@Data
public class ClienteDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String nombre;
	private String apellido;
	private String email;
	private String password;
	private int edad;
	private double pesoActual;
	private double altura;
	private String objetivo;
	private String actividad;
	private String preferencias;
	
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private Date fechaRegistro;
	
	public static ClienteDTO convertToDTO(Cliente cliente) {
		if (cliente == null)
			return null;

		ClienteDTO clienteDTO = new ClienteDTO();

		clienteDTO.setId(cliente.getId());
		clienteDTO.setNombre(cliente.getNombre());
		clienteDTO.setApellido(cliente.getApellido());
		clienteDTO.setEmail(cliente.getEmail());
		clienteDTO.setPassword(cliente.getPassword());
		clienteDTO.setEdad(cliente.getEdad());
		clienteDTO.setPesoActual(cliente.getPesoActual());
		clienteDTO.setAltura(cliente.getAltura());
		clienteDTO.setObjetivo(cliente.getObjetivo());
		clienteDTO.setActividad(cliente.getActividad());
		clienteDTO.setPreferencias(cliente.getPreferencias());
		clienteDTO.setFechaRegistro(cliente.getFechaRegistro());

		return clienteDTO;
	}

	public static Cliente convertToEntity(ClienteDTO clienteDTO) {

		Cliente cliente = new Cliente();
		cliente.setId(clienteDTO.getId());
		cliente.setNombre(clienteDTO.getNombre());
		cliente.setApellido(clienteDTO.getApellido());
		cliente.setEmail(clienteDTO.getEmail());
		cliente.setPassword(clienteDTO.getPassword());
		cliente.setEdad(clienteDTO.getEdad());
		cliente.setPesoActual(clienteDTO.getPesoActual());
		cliente.setAltura(clienteDTO.getAltura());
		cliente.setObjetivo(clienteDTO.getObjetivo());
		cliente.setActividad(clienteDTO.getActividad());
		cliente.setPreferencias(clienteDTO.getPreferencias());
		cliente.setFechaRegistro(clienteDTO.getFechaRegistro());

		return cliente;
	}

	public ClienteDTO() {
		super();
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
