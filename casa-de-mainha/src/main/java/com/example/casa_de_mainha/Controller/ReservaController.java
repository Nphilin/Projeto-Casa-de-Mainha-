package com.example.casa_de_mainha.Controller;

import com.example.casa_de_mainha.Entity.Reserva;
import com.example.casa_de_mainha.Service.ReservaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reservas")
@RequiredArgsConstructor // Injeção limpa
public class ReservaController {

    // O Controller SÓ conversa com o Service, NUNCA com o Repository
    private final ReservaService service;

    // 1. Criar uma nova reserva (POST = 201 Created)
    @PostMapping
    public ResponseEntity<Reserva> criar(@Valid @RequestBody Reserva reserva) {
        return ResponseEntity.status(201).body(service.salvar(reserva));
    }

    // 2. Listar todas as reservas (GET = 200 OK)
    @GetMapping
    public ResponseEntity<List<Reserva>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    // 3. Buscar uma reserva por ID (GET = 200 OK)
    @GetMapping("/{id}")
    public ResponseEntity<Reserva> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    // 4. Atualizar uma reserva (PUT = 200 OK)
    @PutMapping("/{id}")
    public ResponseEntity<Reserva> atualizar(@PathVariable Long id, @Valid @RequestBody Reserva dados) {
        return ResponseEntity.ok(service.atualizar(id, dados));
    }

    // 5. Deletar uma reserva (DELETE = 204 No Content)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // 6. Buscar por status (GET = 200 OK)
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Reserva>> listarPorStatus(@PathVariable Reserva.StatusReserva status) {
        return ResponseEntity.ok(service.listarPorStatus(status));
    }
}