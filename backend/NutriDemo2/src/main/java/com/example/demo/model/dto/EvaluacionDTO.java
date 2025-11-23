package com.example.demo.model.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.demo.repository.entity.Cliente;
import com.example.demo.repository.entity.Evaluacion;


import lombok.Data;
import lombok.ToString;

@Data
public class EvaluacionDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long id;
	
		@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
		private Date fecha;

		private Double pesoActual;

		private Double altura;

		private Integer edad;
		
	    private String actividad;

	    private String objetivo;

	    private String preferencias;

	    private String restricciones;

	    private String observaciones;
	    
		@ToString.Exclude
		private ClienteDTO clienteDTO;
	    
		public static EvaluacionDTO converToDTO(Evaluacion evaluacion, ClienteDTO clienteDTO) {
			
		    if (evaluacion == null) {
		        return null;
		    }

			EvaluacionDTO evaluacionDTO = new EvaluacionDTO();
			evaluacionDTO.setId(evaluacion.getId());
		    evaluacionDTO.setFecha(evaluacion.getFecha());
		    evaluacionDTO.setPesoActual(evaluacion.getPesoActual());
		    evaluacionDTO.setAltura(evaluacion.getAltura());
		    evaluacionDTO.setEdad(evaluacion.getEdad());
		    evaluacionDTO.setPreferencias(evaluacion.getPreferencias());
		    evaluacionDTO.setRestricciones(evaluacion.getRestricciones());
		    evaluacionDTO.setObservaciones(evaluacion.getObservaciones());

		    evaluacionDTO.setClienteDTO(clienteDTO);

			return evaluacionDTO;
		}
		
		public static Evaluacion converToEntity(EvaluacionDTO evaluacionDTO, Cliente cliente) {
			
		    if (evaluacionDTO == null) {
		        return null;
		    }

			Evaluacion evaluacion = new Evaluacion();
			evaluacion.setId(evaluacionDTO.getId());
			evaluacion.setFecha(evaluacionDTO.getFecha());
			evaluacion.setPesoActual(evaluacionDTO.getPesoActual());
			evaluacion.setAltura(evaluacionDTO.getAltura());
			evaluacion.setEdad(evaluacionDTO.getEdad());
			evaluacion.setPreferencias(evaluacionDTO.getPreferencias());
			evaluacion.setRestricciones(evaluacionDTO.getRestricciones());
			evaluacion.setObservaciones(evaluacionDTO.getObservaciones());

			evaluacion.setCliente(cliente);

			return evaluacion;
		}
		

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			EvaluacionDTO other = (EvaluacionDTO) obj;
			return Objects.equals(id, other.id);
		}

		@Override
		public int hashCode() {
			return Objects.hash(id);
		}
		
		

}
