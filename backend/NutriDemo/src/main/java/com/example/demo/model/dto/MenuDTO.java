package com.example.demo.model.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


import org.springframework.format.annotation.DateTimeFormat;

import com.example.demo.repository.entity.Cliente;
import com.example.demo.repository.entity.Comida;
import com.example.demo.repository.entity.Menu;
import com.example.demo.repository.entity.Nutricionista;

import lombok.Data;
import lombok.ToString;

@Data
public class MenuDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private Date fechaInicio;

	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private Date fechaFin;

	private String descripcion;

	private String estado;

	@ToString.Exclude
	private NutricionistaDTO nutricionistaDTO;

	@ToString.Exclude
	private ClienteDTO clienteDTO;

	@ToString.Exclude
	private List<ComidaDTO> listaComidasDTO;

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

		// Aseguramos que la listaComidasDTO esté inicializada
		if (menuDTO.getListaComidasDTO() == null) {
			menuDTO.setListaComidasDTO(new ArrayList<>());
		}

		// Cargamos la lista de clientes, que como es un HashSet hemos de convertirla a
		// ArrayList

		List<Comida> listaComidas = new ArrayList<Comida>(menu.getListaComidas());
		for (int i = 0; i < listaComidas.size(); i++) {
			ComidaDTO comidadto = ComidaDTO.convertToDTO(listaComidas.get(i), menuDTO);
			menuDTO.getListaComidasDTO().add(comidadto);
		}

		return menuDTO;
	}
	

	public static Menu convertToEntity(MenuDTO menuDTO,Cliente cliente) {

		Menu menu = new Menu();
		menu.setId(menuDTO.getId());
		menu.setFechaInicio(menuDTO.getFechaInicio());
		menu.setFechaFin(menuDTO.getFechaFin());
		menu.setDescripcion(menuDTO.getDescripcion());
		menu.setEstado(menuDTO.getEstado());
        
        // Establecer el cliente para el menu
        menu.setCliente(cliente);
        
		// CARGAR LISTA DE COMIDAS

		for (int i = 0; i < menuDTO.getListaComidasDTO().size(); i++) {
			Comida comida = ComidaDTO.convertToEntity(menuDTO.getListaComidasDTO().get(i), menu);
			menu.getListaComidas().add(comida);
		}
        
		return menu;
	}

	public MenuDTO() {
		super();
		this.nutricionistaDTO = new NutricionistaDTO();
		this.clienteDTO = new ClienteDTO();
		this.listaComidasDTO = new ArrayList<ComidaDTO>();
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
