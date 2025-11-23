package com.example.demo.repository.entity;

import java.util.Date;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "evaluacion_inicial")
public class Evaluacion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// Atributo que almacena la fecha
	@Temporal(TemporalType.DATE)
	@Column(name = "fecha")
	private Date fecha;

	private Double pesoActual;

	private Double altura;

	private Integer edad;
	
    private String actividad;

    private String objetivo;

    private String restricciones;

    private String observaciones;

	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "idcliente", nullable = false)
	@ToString.Exclude
	private Cliente cliente;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Evaluacion other = (Evaluacion) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}	

}
