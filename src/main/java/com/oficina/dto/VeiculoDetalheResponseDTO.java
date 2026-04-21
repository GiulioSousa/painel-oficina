package com.oficina.dto;

import java.time.LocalDateTime;
import java.util.List;

public class VeiculoDetalheResponseDTO {

    private Long id;
    private String placa;
    private String descricao;
    private String status;
    private LocalDateTime createdAt;
    private boolean archived;

    private List<ItemResponseDTO> itens;

    public Long getId() {
        return id;
    }

    public String getPlaca() {
        return placa;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getStatus() {
        return status;
    }

    public List<ItemResponseDTO> getItens() {
        return itens;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setItens(List<ItemResponseDTO> itens) {
        this.itens = itens;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean getArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }
}