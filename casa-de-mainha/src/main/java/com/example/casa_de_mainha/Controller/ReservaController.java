package com.example.casa_de_mainha.Controller;

import com.example.casa_de_mainha.Entity.Reserva;
import com.example.casa_de_mainha.Repository.ReservaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reservas")
public class ReservaController {

    @Autowired
    private ReservaRepository reservaRepository;

    // 1. Criar uma nova reserva
    @PostMapping
    public ResponseEntity<Reserva> criar(@Valid @RequestBody @org.springframework.lang.NonNull Reserva reserva) {
        Reserva novaReserva = reservaRepository.save(reserva);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaReserva);
    }

    // 2. Listar todas as reservas
    @GetMapping
    public List<Reserva> listarTodos() {
        return reservaRepository.findAll();
    }

    // 3. Buscar uma reserva por ID
    @GetMapping("/{id}")
    public ResponseEntity<Reserva> buscarPorId(@PathVariable long id) {
        return reservaRepository.findById(id)
                .map(reserva -> ResponseEntity.ok().body(reserva))
                .orElse(ResponseEntity.notFound().build());
    }

    // 4. Atualizar uma reserva (ex: mudar status ou data)
    @PutMapping("/{id}")
    public ResponseEntity<Reserva> atualizar(@PathVariable long id, @Valid @RequestBody Reserva reservaAtualizada) {
        return reservaRepository.findById(id)
                .map(reserva -> {
                    reserva.setHospede(reservaAtualizada.getHospede());
                    reserva.setQuarto(reservaAtualizada.getQuarto());
                    reserva.setDataCheckin(reservaAtualizada.getDataCheckin());
                    reserva.setDataCheckout(reservaAtualizada.getDataCheckout());
                    reserva.setValorTotal(reservaAtualizada.getValorTotal());
                    reserva.setStatusReserva(reservaAtualizada.getStatusReserva());
                    Reserva reservaSalva = reservaRepository.save(reserva);
                    return ResponseEntity.ok().body(reservaSalva);
                }).orElse(ResponseEntity.notFound().build());
    }

    // 5. Deletar uma reserva
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable long id) {

        if (reservaRepository.existsById(id)) {
            reservaRepository.deleteById(id);
            return ResponseEntity.noContent().build(); // Retorna 204 (Deletado com sucesso)
        }
        return ResponseEntity.notFound().build(); // Retorna 404 (Não encontrado)
    }

    // 6. Buscar por status (usando o método que criamos no Repository)
    @GetMapping("/status/{status}")
    public List<Reserva> listarPorStatus(@PathVariable Reserva.StatusReserva status) {
        return reservaRepository.findByStatusReserva(status);
    }
}