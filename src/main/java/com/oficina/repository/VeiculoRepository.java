package com.oficina.repository;

import com.oficina.entity.Veiculo;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
    
    Optional<Veiculo> findByPlaca(String placa);
    
    @Query("SELECT v FROM Veiculo v LEFT JOIN FETCH v.itens WHERE v.id = :id")
    Optional<Veiculo> findByIdWithItens(@Param("id") Long id);

    boolean existsByPlaca(String placa);

    List<Veiculo> findAllByOrderByStatusAscUpdatedAtDesc();

    Page<Veiculo> findAll(Pageable pageable);
}
