package com.oficina.controller;

import com.oficina.dto.VeiculoRequestDTO;
import com.oficina.dto.VeiculoResponseDTO;
import com.oficina.service.VeiculoService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/veiculos")
public class VeiculoController {

    private final VeiculoService veiculoService;

    public VeiculoController(VeiculoService veiculoService) {
        this.veiculoService = veiculoService;
    }

    @PostMapping
    public ResponseEntity<VeiculoResponseDTO> criar(
        @Valid 
        @RequestBody 
        VeiculoRequestDTO dto
    ) {

            VeiculoResponseDTO response = veiculoService.criarVeiculo(dto);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

    @GetMapping
    public ResponseEntity<List<VeiculoResponseDTO>> listar() {

        List<VeiculoResponseDTO> lista = veiculoService.listarVeiculos();

        return ResponseEntity.ok(lista);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<VeiculoResponseDTO> atualizarStatus(
        @PathVariable Long id,
        @RequestParam String status) {
            
            VeiculoResponseDTO response = veiculoService.atualizarStatus(id, status);

            return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {

        veiculoService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}