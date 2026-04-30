package com.oficina.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oficina.dto.ItemRequestDTO;
import com.oficina.dto.ItemResponseDTO;
import com.oficina.entity.*;
import com.oficina.exception.BusinessException;
import com.oficina.exception.ResourceNotFoundException;
import com.oficina.mapper.ItemMapper;
import com.oficina.repository.ItemRepository;
import com.oficina.repository.VeiculoRepository;
import com.oficina.service.ItemService;
import com.oficina.util.DescricaoValidator;

@Service
@Transactional
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final VeiculoRepository veiculoRepository;

    public ItemServiceImpl(ItemRepository itemRepository,
            VeiculoRepository veiculoRepository) {
        this.itemRepository = itemRepository;
        this.veiculoRepository = veiculoRepository;
    }

    @Override
    public ItemResponseDTO adicionarItem(Long veiculoId, ItemRequestDTO dto) {

        DescricaoValidator.validar(dto.getDescricao());

        Veiculo veiculo = buscarVeiculoOuFalhar(veiculoId);

        if (veiculo.getStatus() == VeiculoStatus.ENTREGUE) {
            throw new BusinessException("Não é possível adicionar itens a um veículo entregue");
        }

        Item item = ItemMapper.toEntity(dto);
        item.setVeiculo(veiculo);

        item = itemRepository.save(item);

        return ItemMapper.toResponseDTO(item);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ItemResponseDTO> listarPorVeiculo(Long veiculoId) {

        buscarVeiculoOuFalhar(veiculoId);

        return itemRepository.findByVeiculoId(veiculoId)
                .stream()
                .map(ItemMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ItemResponseDTO atualizarItem(Long itemId, ItemRequestDTO dto) {

        DescricaoValidator.validar(dto.getDescricao());

        Item item = buscarItemOuFalhar(itemId);

        item.setDescricao(dto.getDescricao());
        item.setTipo(dto.getTipo());

        item = itemRepository.save(item);

        return ItemMapper.toResponseDTO(item);
    }

    @Override
    public ItemResponseDTO atualizarStatus(Long itemId, String status) {

        Item item = buscarItemOuFalhar(itemId);

        ItemStatus novoStatus = converterStatusSeguroItem(status);

        item.setStatus(novoStatus);

        item = itemRepository.save(item);

        return ItemMapper.toResponseDTO(item);
    }

    @Override
    public void excluirItem(Long itemId) {

        Item item = buscarItemOuFalhar(itemId);
        itemRepository.delete(item);
    }

    /*
     * =========================
     * MÉTODOS PRIVADOS
     * =========================
     */

    private Veiculo buscarVeiculoOuFalhar(Long id) {

        return veiculoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Veículo não encontrado"));
    }

    private Item buscarItemOuFalhar(Long id) {

        return itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item não encontrado"));
    }

    private ItemStatus converterStatusSeguroItem(String status) {
        try {
            return ItemStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BusinessException("Status inválido");
        }
    }
}
