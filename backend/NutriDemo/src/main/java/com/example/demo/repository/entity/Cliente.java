package com.example.demo.repository.entity;


import java.util.Date;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
@Table(name = "cliente")
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "nombre")
	private String nombre;
	@Column(name = "apellido")
	private String apellido;
	@Column(name = "email")
	private String email;
	@Column(name = "password")
	private String password;
	@Column(name = "edad")
	private int edad;
	@Column(name = "pesoActual")
	private double pesoActual;
	@Column(name = "altura")
	private double altura;
	@Column(name = "objetivo")
	private String objetivo;
	@Column(name = "actividad")
	private String actividad;

	@Column(columnDefinition = "TEXT")
	private String preferencias;

	// Atributo que almacena la fecha 
	@Temporal(TemporalType.DATE)
	@Column(name = "fechaRegistro")
	private Date fechaRegistro;
	

	public Cliente() {
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
		Cliente other = (Cliente) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	
}
