package com.example.casa_de_mainha.Controller;

import com.example.casa_de_mainha.Entity.Consumo;
import com.example.casa_de_mainha.Service.ConsumoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/consumos")
@RequiredArgsConstructor // Injeção via construtor, remova o @Autowired [cite: 66]
public class ConsumoController {

    private final ConsumoService service; // O Controller passa a delegar tudo ao Service [cite: 96]

    // 1. Registrar um novo consumo (POST = 201 Created) [cite: 254, 255]
    @PostMapping
    public ResponseEntity<Consumo> criar(@Valid @RequestBody Consumo consumo) { // @Valid aciona as anotações da entity [cite: 155]
        return ResponseEntity.status(201).body(service.save(consumo));
    }

    // 2. Listar todos
    @GetMapping
    public ResponseEntity<Iterable<Consumo>> listarTodos() {
        return ResponseEntity.ok(service.findAll());
    }

    // 3. Buscar por reserva
    @GetMapping("/reserva/{reservaId}")
    public ResponseEntity<Iterable<Consumo>> listarPorReserva(@PathVariable Long reservaId) {
        return ResponseEntity.ok(service.findByReservaId(reservaId));
    }

    // 4. Buscar específico
    @GetMapping("/{id}")
    public ResponseEntity<Consumo> buscarPorId(@PathVariable Long id) {
        // Toda a lógica de .map() e .orElse() saiu, o Service faz tudo [cite: 109, 111]
        return ResponseEntity.ok(service.findById(id));
    }

    // 5. Atualizar (PUT = 200 OK) [cite: 256, 257]
    @PutMapping("/{id}")
    public ResponseEntity<Consumo> atualizar(@PathVariable Long id, @Valid @RequestBody Consumo dados) {
        return ResponseEntity.ok(service.atualizar(id, dados));
    }

    // 6. Remover (DELETE = 204 No Content) [cite: 258, 259]
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build(); // Retorna 204 [cite: 252]
    }
}