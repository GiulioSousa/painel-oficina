package com.oficina.service;

import com.oficina.dto.PageResponseDTO;
import com.oficina.dto.VeiculoArchivedRequestDTO;
import com.oficina.dto.VeiculoDetalheResponseDTO;
import com.oficina.dto.VeiculoRequestDTO;
import com.oficina.dto.VeiculoResponseDTO;

public interface VeiculoService {

    VeiculoResponseDTO criarVeiculo(VeiculoRequestDTO dto);

    PageResponseDTO<VeiculoResponseDTO> listarVeiculos(int page, int size);

    VeiculoResponseDTO atualizarStatus(Long id, String novoStatus);

    void excluir(Long veiculoId);

    VeiculoDetalheResponseDTO buscarDetalhe(Long veiculoId);

    VeiculoResponseDTO arquivar(Long veiculoId, VeiculoArchivedRequestDTO dto);
}