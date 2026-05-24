package com.example.casa_de_mainha.Controller;

import com.example.casa_de_mainha.Entity.ItemServiço;
import com.example.casa_de_mainha.Service.ItemServiçoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/itens-servico") // Evite usar 'ç' em URLs (boas práticas web)
@RequiredArgsConstructor // Injeta via construtor o Service abaixo
public class ItemServiçoController {

    // 1. O Controller delega tudo para o Service, NUNCA para o Repository [cite: 103, 115]
    private final ItemServiçoService service; 

    // 2. GET: Listar todos
    @GetMapping
    public ResponseEntity<Iterable<ItemServiço>> listar() {
        return ResponseEntity.ok(service.findAll()); // [cite: 107]
    }

    // 3. GET por ID: Limpo, sem .map() ou .orElse() [cite: 108, 109, 111]
    @GetMapping("/{id}")
    public ResponseEntity<ItemServiço> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    // 4. POST: Criar novo item (Usa @Valid para acionar Bean Validation) [cite: 154, 155]
    @PostMapping
    public ResponseEntity<ItemServiço> criar(@Valid @RequestBody ItemServiço dados) {
        return ResponseEntity.status(201).body(service.save(dados)); // Retorna 201 Created [cite: 238, 239, 241]
    }

    // 5. PUT: Atualizar item existente [cite: 243, 244]
    @PutMapping("/{id}")
    public ResponseEntity<ItemServiço> atualizar(@PathVariable Long id, @Valid @RequestBody ItemServiço dados) {
        return ResponseEntity.ok(service.atualizar(id, dados)); // Retorna 200 OK [cite: 247, 257]
    }

    // 6. DELETE: Remover item [cite: 249, 250]
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id); // [cite: 251]
        return ResponseEntity.noContent().build(); // Retorna 204 No Content [cite: 252, 259]
    }
}