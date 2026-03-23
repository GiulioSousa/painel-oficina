package com.oficina.repository;

import com.oficina.entity.Item;
import com.oficina.entity.ItemStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByVeiculoId(Long veiculoId);

    long countByVeiculoIdAndStatus(Long veiculoId, ItemStatus status);
}

