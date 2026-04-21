package com.oficina.mapper;

import com.oficina.dto.ItemResponseDTO;
import com.oficina.dto.VeiculoDetalheResponseDTO;
import com.oficina.entity.Veiculo;

import java.util.List;
import java.util.stream.Collectors;

public class VeiculoDetalheMapper {

    public static VeiculoDetalheResponseDTO toResponse(Veiculo veiculo) {

        VeiculoDetalheResponseDTO dto = new VeiculoDetalheResponseDTO();

        dto.setId(veiculo.getId());
        dto.setPlaca(veiculo.getPlaca());
        dto.setDescricao(veiculo.getDescricao());
        dto.setStatus(veiculo.getStatus().name());
        dto.setCreatedAt(veiculo.getCreatedAt());
        dto.setArchived(veiculo.getArchived());

        List<ItemResponseDTO> itens = veiculo.getItens()
                .stream()
                .map(ItemMapper::toResponseDTO)
                .collect(Collectors.toList());

        dto.setItens(itens);

        return dto;
    }
}