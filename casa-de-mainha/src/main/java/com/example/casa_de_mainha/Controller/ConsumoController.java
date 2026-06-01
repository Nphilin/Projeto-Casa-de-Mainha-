package com.example.casa_de_mainha.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.casa_de_mainha.DTO.ConsumoRequestDTO;
import com.example.casa_de_mainha.DTO.ConsumoResponseDTO;
import com.example.casa_de_mainha.Service.ConsumoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/consumos")
@RequiredArgsConstructor
public class ConsumoController {

    private final ConsumoService service;

    @PostMapping
    public ResponseEntity<ConsumoResponseDTO> criar(@Valid @RequestBody ConsumoRequestDTO dto) {
        return ResponseEntity.status(201).body(service.save(dto));
    }

    @GetMapping
    @Operation(summary = "Listar todos os consumos cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    public ResponseEntity<List<ConsumoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/reserva/{reservaId}")
    public ResponseEntity<List<ConsumoResponseDTO>> listarPorReserva(@PathVariable Long reservaId) {
        return ResponseEntity.ok(service.findByReservaId(reservaId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsumoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsumoResponseDTO> atualizar(@PathVariable Long id,
            @Valid @RequestBody ConsumoRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}