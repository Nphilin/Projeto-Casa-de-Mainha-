package com.example.casa_de_mainha.Controller;

import com.example.casa_de_mainha.Entity.TipoQuarto;
import com.example.casa_de_mainha.Service.TipoQuartoService;
import jakarta.validation.Valid; 
import lombok.RequiredArgsConstructor; 
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tipos-quarto")
@RequiredArgsConstructor
public class TipoQuartoController {

    private final TipoQuartoService service;

    @GetMapping
    public ResponseEntity<Iterable<TipoQuarto>> listar() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoQuarto> buscar(@PathVariable long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<TipoQuarto> criar(@Valid @RequestBody TipoQuarto tipoQuarto) {
        return ResponseEntity.status(201).body(service.save(tipoQuarto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoQuarto> atualizar(@PathVariable long id, @Valid @RequestBody TipoQuarto dados) {
        return ResponseEntity.ok(service.atualizar(id, dados));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}