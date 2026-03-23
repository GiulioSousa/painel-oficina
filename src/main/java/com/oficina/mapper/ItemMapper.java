package com.oficina.mapper;

import com.oficina.dto.ItemRequestDTO;
import com.oficina.dto.ItemResponseDTO;
import com.oficina.entity.Item;
import com.oficina.entity.ItemStatus;
import com.oficina.entity.ItemTipo;

public class ItemMapper {

    public static Item toEntity(ItemRequestDTO dto) {

        Item item = new Item();

        item.setDescricao(dto.getDescricao());
        item.setTipo(ItemTipo.valueOf(dto.getTipo()));
        item.setStatus(ItemStatus.PENDENTE);

        return item;
    }

    public static ItemResponseDTO toResponseDTO(Item item) {

        ItemResponseDTO dto = new ItemResponseDTO();

        dto.setId(item.getId());
        dto.setDescricao(item.getDescricao());
        dto.setTipo(item.getTipo().name());
        dto.setStatus(item.getStatus().name());

        return dto;
    }
}
