package com.example.casa_de_mainha.Controller;

import com.example.casa_de_mainha.Entity.TipoQuarto;
import com.example.casa_de_mainha.Repository.TipoQuartoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tipos-quarto")
@RequiredArgsConstructor
public class TipoQuartoController {

    private final TipoQuartoRepository repository;

    @GetMapping
    public ResponseEntity<Iterable<TipoQuarto>> listar() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoQuarto> buscar(@PathVariable long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}