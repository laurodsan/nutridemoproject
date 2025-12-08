package com.example.demo.model.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.example.demo.repository.entity.Cliente;
import com.example.demo.repository.entity.Plan;

import lombok.Data;
import lombok.ToString;

@Data
public class PlanDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String nombre;
    private int duracionSemanas;
    private boolean incluyeCitas;
    private int frecuenciaCitasSemanas;

    @ToString.Exclude
    private List<ClienteDTO> listaClientesDTO;

    public static PlanDTO convertToDTO(Plan plan) {
        if (plan == null)
            return null;

        PlanDTO planDTO = new PlanDTO();
        planDTO.setId(plan.getId());
        planDTO.setNombre(plan.getNombre());
        planDTO.setDuracionSemanas(plan.getDuracionSemanas());
        planDTO.setIncluyeCitas(plan.isIncluyeCitas());
        planDTO.setFrecuenciaCitasSemanas(plan.getFrecuenciaCitasSemanas());

        if (planDTO.getListaClientesDTO() == null) {
            planDTO.setListaClientesDTO(new ArrayList<>());
        }

        // Convertimos los clientes asignados al plan a DTOs
        List<Cliente> listaClientes = new ArrayList<>(plan.getListaClientes());
        for (Cliente cliente : listaClientes) {
            ClienteDTO clientedto = ClienteDTO.convertToDTO(cliente, null, planDTO);
            planDTO.getListaClientesDTO().add(clientedto);
        }

        return planDTO;
    }

    public static Plan convertToEntity(PlanDTO planDTO) {
        if (planDTO == null)
            return null;

        Plan plan = new Plan();
        plan.setId(planDTO.getId());
        plan.setNombre(planDTO.getNombre());
        plan.setDuracionSemanas(planDTO.getDuracionSemanas());
        plan.setIncluyeCitas(planDTO.isIncluyeCitas());
        plan.setFrecuenciaCitasSemanas(planDTO.getFrecuenciaCitasSemanas());

        if (planDTO.getListaClientesDTO() != null) {
            for (ClienteDTO clienteDTO : planDTO.getListaClientesDTO()) {
                Cliente cliente = ClienteDTO.convertToEntity(clienteDTO, null, plan);
                plan.getListaClientes().add(cliente);
            }
        }

        return plan;
    }

    public PlanDTO() {
        super();
        this.listaClientesDTO = new ArrayList<>();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PlanDTO other = (PlanDTO) obj;
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
