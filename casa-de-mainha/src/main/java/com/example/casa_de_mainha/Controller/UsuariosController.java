package com.example.casa_de_mainha.Controller;

import com.example.casa_de_mainha.Entity.Usuarios;
import com.example.casa_de_mainha.Service.UsuariosService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
public class UsuariosController {

    private final UsuariosService service;

    @GetMapping
    public ResponseEntity<Iterable<Usuarios>> listar() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuarios> buscar(@PathVariable long id) {
        return ResponseEntity.ok(service.findById(id));           
    }

    @PostMapping
    public ResponseEntity<Usuarios> criar(@Valid @RequestBody Usuarios dados) {
        return ResponseEntity.status(201).body(service.save(dados));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuarios> atualizar(@PathVariable long id, @Valid @RequestBody Usuarios dados) {
        return ResponseEntity.ok(service.atualizar(id, dados));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
