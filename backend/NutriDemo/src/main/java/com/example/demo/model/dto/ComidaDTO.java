package com.example.demo.model.dto;

import java.io.Serializable;

import com.example.demo.repository.entity.Comida;
import com.example.demo.repository.entity.Menu;

import lombok.Data;
import lombok.ToString;

@Data
public class ComidaDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String tipo;
    private String descripcion;
    private double calorias;
    
    @ToString.Exclude
	private MenuDTO menuDTO;

	public ComidaDTO() {
		super();
		this.menuDTO = new MenuDTO();
	}
	
	public static ComidaDTO convertToDTO(Comida comida, MenuDTO menu) {
		ComidaDTO comidaDTO = new ComidaDTO();
		comidaDTO.setId(comida.getId());
		comidaDTO.setTipo(comida.getTipo());
		comidaDTO.setDescripcion(comida.getDescripcion());
		comidaDTO.setCalorias(comida.getCalorias());

		comidaDTO.setMenuDTO(menu);

		return comidaDTO;
	}
    
	public static Comida convertToEntity(ComidaDTO comidaDTO, Menu menu) {
		Comida comida = new Comida();
		comida.setId(comidaDTO.getId());
		comida.setTipo(comidaDTO.getTipo());
		comida.setDescripcion(comidaDTO.getDescripcion());
		comida.setCalorias(comidaDTO.getCalorias());

		comida.setMenu(menu);

		return comida;
	}
    
}
