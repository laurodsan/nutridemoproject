package com.example.demo.model.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.demo.repository.entity.Cita;
import com.example.demo.repository.entity.Cliente;
import com.example.demo.repository.entity.Nutricionista;

import lombok.Data;
import lombok.ToString;

@Data
public class CitaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private Date fechaHora;
	private String motivo;
	private String estado;
	private String notas;

	@ToString.Exclude
	private ClienteDTO clienteDTO;

	@ToString.Exclude
	private NutricionistaDTO nutricionistaDTO;

	public static CitaDTO convertToDTO(Cita cita, ClienteDTO clienteDTO, NutricionistaDTO nutricionistaDTO) {

		CitaDTO citaDTO = new CitaDTO();

		citaDTO.setId(cita.getId());
		citaDTO.setFechaHora(cita.getFechaHora());
		citaDTO.setMotivo(cita.getMotivo());
		citaDTO.setEstado(cita.getEstado());
		citaDTO.setNotas(cita.getNotas());

		citaDTO.setClienteDTO(clienteDTO);
		citaDTO.setNutricionistaDTO(nutricionistaDTO);

		return citaDTO;
	}

	public static Cita convertToEntity(CitaDTO citaDTO, Cliente cliente, Nutricionista nutricionista) {

		Cita cita = new Cita();

		cita.setId(citaDTO.getId());
		cita.setFechaHora(citaDTO.getFechaHora());
		cita.setMotivo(citaDTO.getMotivo());
		cita.setEstado(citaDTO.getEstado());
		cita.setNotas(citaDTO.getNotas());

		cita.setCliente(cliente);
		cita.setNutricionista(nutricionista);

		return cita;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CitaDTO other = (CitaDTO) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	public CitaDTO() {
	}
	
}
