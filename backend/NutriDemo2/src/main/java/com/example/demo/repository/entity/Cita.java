package com.example.demo.repository.entity;
import java.util.Date;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
@Table(name = "cita")
public class Cita {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// Guardamos fecha y hora juntas
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechaHora")
	private Date fechaHora;

	@Column(name = "motivo")
	private String motivo;

	@Column(name = "estado")
	private String estado;

	@Column(name = "notas", columnDefinition = "TEXT")
	private String notas;


	@ManyToOne
	@JoinColumn(name = "idcliente", nullable = false)
	private Cliente cliente;

	@ManyToOne
	@JoinColumn(name = "idnutricionista", nullable = false)
	private Nutricionista nutricionista;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cita other = (Cita) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

}
