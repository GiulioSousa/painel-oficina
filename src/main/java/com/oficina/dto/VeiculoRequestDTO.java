package com.oficina.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class VeiculoRequestDTO {

    @NotBlank(message = "Placa é obrigatória")
    @Size(min = 7, max = 7, message = "A placa deve ter 7 caracteres")
    @Pattern(
        regexp = "^[A-Z0-9]{7}$", 
        message = "Placa deve ser no formato ABC1234"
        )
    private String placa;

    @NotBlank(message = "Descrição do veículo é obrigatória")
    @Size(max = 100, message = "A descrição deve ter no máximo 120 caracteres")
    private String descricao;

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}

