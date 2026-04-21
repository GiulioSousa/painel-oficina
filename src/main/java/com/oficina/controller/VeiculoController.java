package com.oficina.controller;

import com.oficina.dto.PageResponseDTO;
import com.oficina.dto.VeiculoArchivedRequestDTO;
import com.oficina.dto.VeiculoDetalheResponseDTO;
import com.oficina.dto.VeiculoRequestDTO;
import com.oficina.dto.VeiculoResponseDTO;
import com.oficina.service.VeiculoService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/veiculos")
public class VeiculoController {

    private final VeiculoService veiculoService;

    public VeiculoController(VeiculoService veiculoService) {
        this.veiculoService = veiculoService;
    }

    @PostMapping
    public ResponseEntity<VeiculoResponseDTO> criar(
            @Valid @RequestBody VeiculoRequestDTO dto) {

        VeiculoResponseDTO response = veiculoService.criarVeiculo(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<PageResponseDTO<VeiculoResponseDTO>> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        PageResponseDTO<VeiculoResponseDTO> response = veiculoService.listarVeiculos(page, size);

        return ResponseEntity.ok(response);
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

    @GetMapping("/{id}/detalhe")
    public ResponseEntity<VeiculoDetalheResponseDTO> detalhar(@PathVariable Long id) {

        VeiculoDetalheResponseDTO response = veiculoService.buscarDetalhe(id);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/arquivar")
    public ResponseEntity<VeiculoResponseDTO> arquivar(
            @PathVariable Long id,
            @Valid @RequestBody VeiculoArchivedRequestDTO dto) {

        VeiculoResponseDTO response = veiculoService.arquivar(id, dto);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VeiculoResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody VeiculoRequestDTO dto) {

        VeiculoResponseDTO response = veiculoService.atualizarVeiculo(id, dto);

        return ResponseEntity.ok(response);
    }
}
