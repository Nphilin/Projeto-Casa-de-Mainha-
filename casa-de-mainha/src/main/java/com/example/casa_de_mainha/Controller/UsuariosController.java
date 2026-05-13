package com.example.casa_de_mainha.Controller;

import com.example.casa_de_mainha.Entity.Usuarios;
import com.example.casa_de_mainha.Repository.UsuariosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
public class UsuariosController {

    private final UsuariosRepository repository;

    @GetMapping
    public ResponseEntity<Iterable<Usuarios>> listar() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuarios> buscar(@PathVariable long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
