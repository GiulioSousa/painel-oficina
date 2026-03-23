package com.oficina.service;

import com.oficina.dto.VeiculoRequestDTO;
import com.oficina.dto.VeiculoResponseDTO;

import java.util.List;

public interface VeiculoService {

    VeiculoResponseDTO criarVeiculo(VeiculoRequestDTO dto);

    List<VeiculoResponseDTO> listarVeiculos();

    VeiculoResponseDTO atualizarStatus(Long id, String novoStatus);

    void excluir(Long veiculoId);
}