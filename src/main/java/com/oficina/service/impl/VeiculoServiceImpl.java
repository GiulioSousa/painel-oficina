package com.oficina.service.impl;

import com.oficina.dto.VeiculoRequestDTO;
import com.oficina.dto.VeiculoResponseDTO;
import com.oficina.entity.Veiculo;
import com.oficina.entity.VeiculoStatus;
import com.oficina.exception.BusinessException;
import com.oficina.exception.ResourceNotFoundException;
import com.oficina.entity.ItemStatus;
import com.oficina.mapper.VeiculoMapper;
import com.oficina.repository.VeiculoRepository;
import com.oficina.repository.ItemRepository;
import com.oficina.service.VeiculoService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

        validarPlacaUnica(dto.getPlaca());

        Veiculo veiculo = VeiculoMapper.toEntity(dto);

        veiculo = veiculoRepository.save(veiculo);

        return VeiculoMapper.toResponse(veiculo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VeiculoResponseDTO> listarVeiculos() {

        return veiculoRepository.findAll()
                .stream()
                .map(VeiculoMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public VeiculoResponseDTO atualizarStatus(Long veiculoId, String novoStatus) {

        Veiculo veiculo = buscarOuFalhar(veiculoId);

        VeiculoStatus status = VeiculoStatus.valueOf(novoStatus);

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
            throw new RuntimeException(
                    "Não é possível marcar veículo como PRONTO com itens pendentes");
        }

        if (status == VeiculoStatus.ENTREGUE && pendentes > 0) {
            throw new RuntimeException(
                    "Não é possível marcar veículo como ENTREGUE com itens pendentes");
        }
    }
}