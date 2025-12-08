package com.example.demo.model.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.demo.repository.entity.Cliente;
import com.example.demo.repository.entity.Menu;
import com.example.demo.repository.entity.Nutricionista;

import lombok.Data;
import lombok.ToString;

@Data
public class MenuDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaInicio;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaFin;
	
	private String descripcion;
	private String estado;
	
	@ToString.Exclude
    private ClienteDTO clienteDTO;
	
	@ToString.Exclude
    private NutricionistaDTO nutricionistaDTO;
	
	public static MenuDTO convertToDTO(Menu menu, ClienteDTO cliente) {

		if (menu == null)
			return null;

		MenuDTO menuDTO = new MenuDTO();

		menuDTO.setId(menu.getId());
		menuDTO.setFechaInicio(menu.getFechaInicio());
		menuDTO.setFechaFin(menu.getFechaFin());
		menuDTO.setDescripcion(menu.getDescripcion());
		menuDTO.setEstado(menu.getEstado());

		if (cliente != null) {
			menuDTO.setClienteDTO(cliente);
		} else {
			menuDTO.setClienteDTO(new ClienteDTO()); // Evitar NullPointerException
		}

		return menuDTO;
	}
	

	public static Menu convertToEntity(MenuDTO menuDTO,Cliente cliente, Nutricionista nutricionista) {

		Menu menu = new Menu();
		menu.setId(menuDTO.getId());
		menu.setFechaInicio(menuDTO.getFechaInicio());
		menu.setFechaFin(menuDTO.getFechaFin());
		menu.setDescripcion(menuDTO.getDescripcion());
		menu.setEstado(menuDTO.getEstado());
        
        // Establecer el cliente para el menu
        menu.setCliente(cliente);
        menu.setNutricionista(nutricionista);
        
		return menu;
	}

	public MenuDTO() {
		super();
		this.nutricionistaDTO = new NutricionistaDTO();
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
		MenuDTO other = (MenuDTO) obj;
		return Objects.equals(id, other.id);
	}


	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	
	
}
