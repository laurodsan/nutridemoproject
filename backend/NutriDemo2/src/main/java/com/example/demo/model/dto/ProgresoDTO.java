package com.example.demo.model.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.demo.repository.entity.Cliente;
import com.example.demo.repository.entity.Progreso;

import lombok.Data;
import lombok.ToString;

@Data
public class ProgresoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fecha;

    private Double peso;
    private Double cintura;
    private Double cadera;
    private Double grasaCorporal;

    private String notas;

    @ToString.Exclude
    private ClienteDTO clienteDTO;

    public static ProgresoDTO convertToDTO(Progreso progreso, ClienteDTO clienteDTO) {

        if (progreso == null) return null;

        ProgresoDTO progresoDTO = new ProgresoDTO();

        progresoDTO.setId(progreso.getId());
        progresoDTO.setFecha(progreso.getFecha());
        progresoDTO.setPeso(progreso.getPeso());
        progresoDTO.setCintura(progreso.getCintura());
        progresoDTO.setCadera(progreso.getCadera());
        progresoDTO.setGrasaCorporal(progreso.getGrasaCorporal());
        progresoDTO.setNotas(progreso.getNotas());

        if (clienteDTO != null) {
        	progresoDTO.setClienteDTO(clienteDTO);
        } else {
        	progresoDTO.setClienteDTO(new ClienteDTO());
        }

        return progresoDTO;
    }

    public static Progreso convertToEntity(ProgresoDTO progresoDTO, Cliente cliente) {

        Progreso progreso = new Progreso();

        progreso.setId(progresoDTO.getId());
        progreso.setFecha(progresoDTO.getFecha());
        progreso.setPeso(progresoDTO.getPeso());
        progreso.setCintura(progresoDTO.getCintura());
        progreso.setCadera(progresoDTO.getCadera());
        progreso.setGrasaCorporal(progresoDTO.getGrasaCorporal());
        progreso.setNotas(progresoDTO.getNotas());

        progreso.setCliente(cliente);

        return progreso;
    }

    public ProgresoDTO() {
        this.clienteDTO = new ClienteDTO();
    }

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProgresoDTO other = (ProgresoDTO) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

}

