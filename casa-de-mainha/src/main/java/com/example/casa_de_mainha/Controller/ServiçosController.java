package com.example.casa_de_mainha.Controller;

import com.example.casa_de_mainha.Entity.Serviços;
import com.example.casa_de_mainha.Service.ServiçosService; // IMPORT CORRIGIDO PARA SUMIR O ERRO
import jakarta.validation.Valid; // Necessário para o Passo 3 do professor
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
// DICA WEB: Nunca use 'ç' em URLs. Troquei para "servicos" para evitar bugs no navegador.
@RequestMapping("/api/v1/servicos") 
@RequiredArgsConstructor
public class ServiçosController {

    // O Controller passa a delegar tudo ao Service [cite: 96, 103]
    private final ServiçosService service;

    // 1. GET: Listar todos os serviços
    @GetMapping
    public ResponseEntity<Iterable<Serviços>> listar() {
        return ResponseEntity.ok(service.findAll()); //[cite: 107]
    }

    // 2. GET: Buscar serviço por ID
    @GetMapping("/{id}")
    public ResponseEntity<Serviços> buscar(@PathVariable Long id) { // Troquei 'long' para 'Long' (objeto)
        return ResponseEntity.ok(service.findById(id)); //[cite: 111]
    }

    // 3. POST: Criar novo serviço (Passo 5 - CRUD Completo)
    @PostMapping
    public ResponseEntity<Serviços> criar(@Valid @RequestBody Serviços dados) { //[cite: 239]
        // Retorna HTTP 201 Created quando cria com sucesso [cite: 241, 254, 255]
        return ResponseEntity.status(201).body(service.save(dados)); //[cite: 241]
    }

    // 4. PUT: Atualizar serviço existente
    @PutMapping("/{id}")
    public ResponseEntity<Serviços> atualizar(@PathVariable Long id, @Valid @RequestBody Serviços dados) { //[cite: 243, 244, 246]
        return ResponseEntity.ok(service.atualizar(id, dados)); //[cite: 247]
    }

    // 5. DELETE: Remover serviço
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) { //[cite: 249, 250]
        service.deletar(id); //[cite: 251]
        // Retorna HTTP 204 No Content após deletar com sucesso [cite: 252, 258, 259]
        return ResponseEntity.noContent().build(); //[cite: 252]
    }
}