package com.example.demo.model.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.example.demo.repository.entity.Cliente;
import com.example.demo.repository.entity.Nutricionista;
import com.example.demo.repository.entity.Plan;

import lombok.Data;
import lombok.ToString;

@Data
public class NutricionistaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String nombre;
    private String email;
    private String password;
    private String especialidad;

    @ToString.Exclude
    private List<ClienteDTO> listaClientesDTO;

    public static NutricionistaDTO convertToDTO(Nutricionista nutricionista) {

        NutricionistaDTO nutricionistaDTO = new NutricionistaDTO();
        nutricionistaDTO.setId(nutricionista.getId());
        nutricionistaDTO.setNombre(nutricionista.getNombre());
        nutricionistaDTO.setEmail(nutricionista.getEmail());
        nutricionistaDTO.setPassword(nutricionista.getPassword());
        nutricionistaDTO.setEspecialidad(nutricionista.getEspecialidad());

        if (nutricionista.getListaClientes() != null) {
            nutricionistaDTO.setListaClientesDTO(new ArrayList<>());

            for (Cliente cliente : nutricionista.getListaClientes()) {

                ClienteDTO clienteDTO = ClienteDTO.convertToDTO(cliente, 
                        cliente.getPlan() != null ? PlanDTO.convertToDTO(cliente.getPlan()) : null);

                // Aquí añadimos los menús del cliente al DTO
                if (cliente.getListaMenus() != null) {
                    for (var menu : cliente.getListaMenus()) {
                        clienteDTO.getListaMenus().add(MenuDTO.convertToDTO(menu, null));
                    }
                }

                nutricionistaDTO.getListaClientesDTO().add(clienteDTO);
            }
        }

        return nutricionistaDTO;
    }

    public static Nutricionista convertToEntity(NutricionistaDTO nutricionistaDTO) {

        Nutricionista nutricionista = new Nutricionista();
        nutricionista.setId(nutricionistaDTO.getId());
        nutricionista.setNombre(nutricionistaDTO.getNombre());
        nutricionista.setEmail(nutricionistaDTO.getEmail());
        nutricionista.setPassword(nutricionistaDTO.getPassword());
        nutricionista.setEspecialidad(nutricionistaDTO.getEspecialidad());

        if (nutricionistaDTO.getListaClientesDTO() != null) {
            for (ClienteDTO clienteDTO : nutricionistaDTO.getListaClientesDTO()) {

                Plan plan = clienteDTO.getPlanDTO() != null ? PlanDTO.convertToEntity(clienteDTO.getPlanDTO()) : null;
                Cliente cliente = ClienteDTO.convertToEntity(clienteDTO, plan);

                // Añadimos los menús al cliente
                if (clienteDTO.getListaMenus() != null) {
                    for (MenuDTO menuDTO : clienteDTO.getListaMenus()) {
                        cliente.getListaMenus().add(MenuDTO.convertToEntity(menuDTO, cliente,
                                menuDTO.getNutricionistaDTO() != null ? NutricionistaDTO.convertToEntity(menuDTO.getNutricionistaDTO()) : null));
                    }
                }

                nutricionista.getListaClientes().add(cliente);
            }
        }

        return nutricionista;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        NutricionistaDTO other = (NutricionistaDTO) obj;
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public NutricionistaDTO() {
        super();
        this.listaClientesDTO = new ArrayList<>();
    }
}

