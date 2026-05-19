package com.example.casa_de_mainha.Controller;

import com.example.casa_de_mainha.Entity.Hospede;
import com.example.casa_de_mainha.Repository.HospedeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hospedes")
@RequiredArgsConstructor // Injeção de dependência automática via construtor
public class HospedeController {

    private final HospedeRepository repository;

    @GetMapping
    public ResponseEntity<Iterable<Hospede>> listarTodos() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hospede> buscarPorId(@PathVariable long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build()); // Retorna 404 se não achar
    }

    @GetMapping("/busca")
    public ResponseEntity<List<Hospede>> buscarPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(repository.findByNomeContainingIgnoreCase(nome));
    }
}