package com.example.casa_de_mainha.Controller;

import com.example.casa_de_mainha.Entity.Quarto;
import com.example.casa_de_mainha.Service.QuartoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/quartos")
@RequiredArgsConstructor
public class QuartoController {

    private final QuartoService service;

    @PostMapping
    public ResponseEntity<Quarto> criar(@RequestBody Quarto dados) {
        return ResponseEntity.status(201).body(service.save(dados));
    }

    @GetMapping
    public ResponseEntity<Iterable<Quarto>> listar() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Quarto> buscar(@PathVariable long id) {
        return ResponseEntity.ok(service.findById(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Quarto> atualizar(@PathVariable long id,@Valid @RequestBody Quarto dados) {
        return ResponseEntity.ok(service.atualizar(id, dados));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
               
}