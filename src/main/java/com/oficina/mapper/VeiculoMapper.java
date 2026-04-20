package com.oficina.mapper;

import com.oficina.dto.VeiculoRequestDTO;
import com.oficina.dto.VeiculoResponseDTO;
import com.oficina.entity.Veiculo;
import com.oficina.entity.VeiculoStatus;

public class VeiculoMapper {

    public static Veiculo toEntity(VeiculoRequestDTO dto) {
        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca(dto.getPlaca());
        veiculo.setDescricao(dto.getDescricao());
        veiculo.setStatus(VeiculoStatus.PENDENTE);
        
        return veiculo;
    }

    public static VeiculoResponseDTO toResponse(Veiculo veiculo) {
        VeiculoResponseDTO dto = new VeiculoResponseDTO();

        dto.setId(veiculo.getId());
        dto.setPlaca(veiculo.getPlaca());
        dto.setDescricao(veiculo.getDescricao());
        dto.setStatus(veiculo.getStatus().name());
        dto.setCreatedAt(veiculo.getCreatedAt());
        dto.setArchived(veiculo.getArchived());

        return dto;
    }
}
