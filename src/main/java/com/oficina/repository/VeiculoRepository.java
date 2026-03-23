package com.oficina.repository;

import com.oficina.entity.Veiculo;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {

    Optional<Veiculo> findByPlaca(String placa);
    
    boolean existsByPlaca(String placa);
}

