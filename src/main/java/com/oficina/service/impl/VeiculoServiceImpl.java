package com.oficina.service.impl;

import com.oficina.dto.PageResponseDTO;
import com.oficina.dto.VeiculoDetalheResponseDTO;
import com.oficina.dto.VeiculoRequestDTO;
import com.oficina.dto.VeiculoResponseDTO;
import com.oficina.entity.Veiculo;
import com.oficina.entity.VeiculoStatus;
import com.oficina.exception.BusinessException;
import com.oficina.exception.ResourceNotFoundException;
import com.oficina.entity.ItemStatus;
import com.oficina.mapper.VeiculoDetalheMapper;
import com.oficina.mapper.VeiculoMapper;
import com.oficina.repository.VeiculoRepository;
import com.oficina.repository.ItemRepository;
import com.oficina.service.VeiculoService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@Transactional
public class VeiculoServiceImpl implements VeiculoService {

    private final VeiculoRepository veiculoRepository;
    private final ItemRepository itemRepository;

    public VeiculoServiceImpl(
            VeiculoRepository veiculoRepository,
            ItemRepository itemRepository) {

        this.veiculoRepository = veiculoRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public VeiculoResponseDTO criarVeiculo(VeiculoRequestDTO dto) {

        String placaNormalizada = normalizarPlaca(dto.getPlaca());

        validarPlacaUnica(placaNormalizada);

        Veiculo veiculo = VeiculoMapper.toEntity(dto);

        veiculo = veiculoRepository.save(veiculo);
        veiculo.setPlaca(placaNormalizada);

        return VeiculoMapper.toResponse(veiculo);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponseDTO<VeiculoResponseDTO> listarVeiculos(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        
        Page<Veiculo> pagina = veiculoRepository.findAll(pageable);
        
        List<VeiculoResponseDTO> content = pagina.getContent()
                .stream()
                .sorted(Comparator
                    .comparing(Veiculo::getUpdatedAt).reversed())
                .map(VeiculoMapper::toResponse)
                .toList();
        
        return new PageResponseDTO<>(content, pagina.getTotalPages());
    }

    @Override
    public VeiculoResponseDTO atualizarStatus(Long veiculoId, String novoStatus) {

        Veiculo veiculo = buscarOuFalhar(veiculoId);

        VeiculoStatus status = converterStatusSeguro(novoStatus);

        validarMudancaStatus(veiculoId, status);

        veiculo.setStatus(status);

        veiculoRepository.save(veiculo);

        return VeiculoMapper.toResponse(veiculo);
    }

    @Override
    public void excluir(Long veiculoId) {

        Veiculo veiculo = buscarOuFalhar(veiculoId);

        veiculoRepository.delete(veiculo);
    }

    @Override
    @Transactional(readOnly = true)
    public VeiculoDetalheResponseDTO buscarDetalhe(Long veiculoId) {

        Veiculo veiculo = veiculoRepository.findByIdWithItens(veiculoId)
                .orElseThrow(() -> new ResourceNotFoundException("Veículo não encontrado"));

        return VeiculoDetalheMapper.toResponse(veiculo);
    }
    
    /*
     * ==============================
     * MÉTODOS PRIVADOS DE REGRA
     * ==============================
     */

    private void validarPlacaUnica(String placa) {

        if (veiculoRepository.existsByPlaca(placa)) {
            throw new BusinessException("Já existe um veículo com essa placa");
        }
    }

    private Veiculo buscarOuFalhar(Long id) {

        return veiculoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Veículo não encontrado"));
    }

    private void validarMudancaStatus(Long veiculoId, VeiculoStatus status) {

        long pendentes = itemRepository.countByVeiculoIdAndStatus(
                veiculoId,
                ItemStatus.PENDENTE);

        if (status == VeiculoStatus.PRONTO && pendentes > 0) {
            throw new BusinessException(
                    "Não é possível marcar veículo como PRONTO com itens pendentes");
        }
    }

    private String normalizarPlaca(String placa) {

        return placa.trim().toUpperCase();
    }

    private VeiculoStatus converterStatusSeguro(String status) {
        try {
            return VeiculoStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BusinessException("Status inválido");
        }
    }



}