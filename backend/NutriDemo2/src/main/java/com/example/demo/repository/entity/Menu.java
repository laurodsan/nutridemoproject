package com.example.demo.repository.entity;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
@Table(name = "menu")
public class Menu {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// Atributo que almacena la fecha
	@Temporal(TemporalType.DATE)
	@Column(name = "fechaInicio")
	private Date fechaInicio;

	@Temporal(TemporalType.DATE)
	@Column(name = "fechaFin")
	private Date fechaFin;
	@Column(name = "descripcion")
	private String descripcion;
	@Column(name = "estado")
	private String estado;

	@ManyToOne
	@JoinColumn(name = "idcliente", nullable = true)
	private Cliente cliente;

	@ManyToOne
	@JoinColumn(name = "idnutricionista", nullable = true)
	private Nutricionista nutricionista;
	
	@OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Comida> comidas;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Menu other = (Menu) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	
}
