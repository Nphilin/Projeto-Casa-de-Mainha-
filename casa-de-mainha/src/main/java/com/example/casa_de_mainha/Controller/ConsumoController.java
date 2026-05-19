package com.example.casa_de_mainha.Controller;

import com.example.casa_de_mainha.Entity.Consumo;
import com.example.casa_de_mainha.Repository.ConsumoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/consumos")
public class ConsumoController {

    @Autowired
    private ConsumoRepository consumoRepository;

    // 1. Registrar um novo consumo (ex: frigobar, lavanderia)
    @PostMapping
    public ResponseEntity<Consumo> criar(@Valid @RequestBody Consumo consumo) {
        Consumo novoConsumo = consumoRepository.save(consumo);
        return new ResponseEntity<>(novoConsumo, HttpStatus.CREATED);
    }

    // 2. Listar todos os consumos registrados no sistema
    @GetMapping
    public List<Consumo> listarTodos() {
        return consumoRepository.findAll();
    }

    // 3. Buscar os consumos de uma reserva específica (Útil para o fechamento da
    // conta)
    @GetMapping("/reserva/{reservaId}")
    public List<Consumo> listarPorReserva(@PathVariable long reservaId) {
        return consumoRepository.findByReservaId(reservaId);
    }

    // 4. Buscar um consumo específico por ID
    @GetMapping("/{id}")
    public ResponseEntity<Consumo> buscarPorId(@PathVariable long id) {
        return consumoRepository.findById(id)
                .map(consumo -> ResponseEntity.ok().body(consumo))
                .orElse(ResponseEntity.notFound().build());
    }

    // 5. Atualizar dados de um consumo (ex: corrigir valor ou descrição)
    @PutMapping("/{id}")
    public ResponseEntity<Consumo> atualizar(@PathVariable long id, @Valid @RequestBody Consumo consumoAtualizado) {
        return consumoRepository.findById(id)
                .map(consumo -> {
                    consumo.setDescricao(consumoAtualizado.getDescricao());
                    consumo.setValor(consumoAtualizado.getValor());
                    consumo.setReserva(consumoAtualizado.getReserva());
                    Consumo salvo = consumoRepository.save(consumo);
                    return ResponseEntity.ok().body(salvo);
                }).orElse(ResponseEntity.notFound().build());
    }

    // 6. Remover um consumo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable long id) {
        return consumoRepository.findById(id)
                .map(consumo -> {
                    consumoRepository.delete(consumo);
                    return ResponseEntity.noContent().<Void>build();
                }).orElse(ResponseEntity.notFound().build());
    }
}