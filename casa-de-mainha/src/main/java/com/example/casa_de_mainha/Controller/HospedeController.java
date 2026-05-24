package com.example.casa_de_mainha.Controller;

import com.example.casa_de_mainha.Entity.Hospede;
import com.example.casa_de_mainha.Service.HospedeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/v1/hospedes")
@RequiredArgsConstructor // Injeção de dependência automática via construtor (Lombok) [cite: 66, 100]
public class HospedeController {

    private final HospedeService service; // O Controller passa a delegar tudo ao Service [cite: 96, 103]

    // 1. Registrar um novo hóspede (POST = 201 Created) [cite: 237, 255]
    @PostMapping
    public ResponseEntity<Hospede> criar(@Valid @RequestBody Hospede hospede) { // @Valid adicionado [cite: 54, 239]
        return ResponseEntity.status(201).body(service.save(hospede)); //[cite: 57, 241]
    }

    // 2. Listar todos os hóspedes [cite: 104]
    @GetMapping
    public ResponseEntity<Iterable<Hospede>> listarTodos() {
        return ResponseEntity.ok(service.findAll());//[cite: 107]
    }


    // 4. Buscar um hóspede específico por ID (Limpo, sem lógica de tratamento interna) [cite: 108]
    @GetMapping("/{id}")
    public ResponseEntity<Hospede> buscarPorId(@PathVariable Long id) { // Ajustado para Long (objeto) [cite: 109]
        return ResponseEntity.ok(service.findById(id)); // Delegação direta ao Service 
    }

    // 5. Buscar hóspedes por trecho de nome
    @GetMapping("/busca")
    public ResponseEntity<List<Hospede>> buscarPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(service.findByNomeContainingIgnoreCase(nome));
    }

    // 6. Atualizar dados de um hóspede (PUT = 200 OK) [cite: 242, 243, 257]
    @PutMapping("/{id}")
    public ResponseEntity<Hospede> atualizar(@PathVariable Long id, @Valid @RequestBody Hospede dados) { // Adicionado conforme Passo 5 [cite: 244, 246]
        return ResponseEntity.ok(service.atualizar(id, dados)); //[cite: 247]
    }

    // 7. Remover um hóspede do sistema (DELETE = 204 No Content) [cite: 248, 249, 258]
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) { // Adicionado conforme Passo 5 [cite: 250]
        service.deletar(id); // [cite: 251]
        return ResponseEntity.noContent().build(); // Retorno 204 sem corpo [cite: 252, 322]
    }
}