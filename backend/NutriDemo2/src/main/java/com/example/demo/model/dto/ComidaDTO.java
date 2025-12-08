package com.example.demo.model.dto;

import java.io.Serializable;
import java.util.Objects;
import com.example.demo.repository.entity.Comida;
import com.example.demo.repository.entity.Menu;

import lombok.Data;
import lombok.ToString;

@Data
public class ComidaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String tipo;
    private String descripcion;
    private Double calorias;

    // Valor por defecto: siempre false al crear
    private Boolean hecha = false;

    private String diaSemana;

    @ToString.Exclude
    private MenuDTO menuDTO;


    // Conversión de entidad a DTO
    public static ComidaDTO convertToDTO(Comida comida, MenuDTO menu) {
        if (comida == null)
            return null;

        ComidaDTO comidaDTO = new ComidaDTO();
        comidaDTO.setId(comida.getId());
        comidaDTO.setTipo(comida.getTipo());
        comidaDTO.setDescripcion(comida.getDescripcion());
        comidaDTO.setCalorias(comida.getCalorias());

        // Evitar null => siempre false si viene sin valor
        comidaDTO.setHecha(comida.getHecha() != null ? comida.getHecha() : false);

        comidaDTO.setDiaSemana(comida.getDiaSemana());

        if (menu != null) {
            comidaDTO.setMenuDTO(menu);
        } else {
            comidaDTO.setMenuDTO(new MenuDTO());
        }

        return comidaDTO;
    }

    // Conversión de DTO a entidad
    public static Comida convertToEntity(ComidaDTO comidaDTO, Menu menu) {
        Comida comida = new Comida();
        comida.setId(comidaDTO.getId());
        comida.setTipo(comidaDTO.getTipo());
        comida.setDescripcion(comidaDTO.getDescripcion());
        comida.setCalorias(comidaDTO.getCalorias());

        // Evitar null => siempre false por defecto
        comida.setHecha(comidaDTO.getHecha() != null ? comidaDTO.getHecha() : false);

        comida.setDiaSemana(comidaDTO.getDiaSemana());
        comida.setMenu(menu);

        return comida;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        ComidaDTO other = (ComidaDTO) obj;
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // Constructor vacío
    public ComidaDTO() {
        super();
        this.menuDTO = new MenuDTO();
        this.hecha = false; // reforzar valor inicial
    }
}
