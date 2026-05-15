package com.example.casa_de_mainha.Controller;

import com.example.casa_de_mainha.Entity.Serviços;
import com.example.casa_de_mainha.Repository.ServiçosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/serviço")
@RequiredArgsConstructor
public class ServiçosController {

    private final ServiçosRepository repository;

    @GetMapping
    public ResponseEntity<Iterable<Serviços>> listar() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Serviços> buscar(@PathVariable long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
