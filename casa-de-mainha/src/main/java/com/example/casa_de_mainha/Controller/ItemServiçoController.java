package com.example.casa_de_mainha.Controller;

import com.example.casa_de_mainha.Entity.ItemServiço;
import com.example.casa_de_mainha.Repository.ItemServiçoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/item_serviço")
@RequiredArgsConstructor // injeta via construtor — sem @Autowired
public class ItemServiçoController {

    private final ItemServiçoRepository repository;

    @GetMapping
    public ResponseEntity<Iterable<ItemServiço>> listar() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemServiço> buscar(@PathVariable long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
