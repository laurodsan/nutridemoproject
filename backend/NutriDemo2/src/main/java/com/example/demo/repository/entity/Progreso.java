package com.example.demo.repository.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.Objects;

@Data
@Entity
@Table(name = "progreso")
public class Progreso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
	@Column(name = "fecha")
    private Date fecha;

    private Double peso;

    private Double cintura;

    private Double cadera;

    private Double grasaCorporal;

    @Column(columnDefinition = "TEXT")
    private String notas;


    @ManyToOne
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
		Progreso other = (Progreso) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
     
}