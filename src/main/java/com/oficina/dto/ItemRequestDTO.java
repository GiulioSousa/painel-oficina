package com.oficina.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ItemRequestDTO {

    @NotBlank(message = "Tipo do item é obrigatório")
    @Pattern(
        regexp = "^(PEÇA|SERVIÇO)$", 
        message = "Tipo deve ser PEÇA ou SERVIÇO"
        )
    private String tipo;

    @NotBlank(message = "Descrição do item é obrigatória")
    @Size(max = 150, message = "A descrição deve ter no máximo 150 caracteres")
    private String descricao;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
