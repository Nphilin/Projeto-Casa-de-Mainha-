package com.example.casa_de_mainha.Service;

import com.example.casa_de_mainha.Repository.ReservaRepository;
import com.example.casa_de_mainha.Entity.Reserva; // Ajuste o pacote do model se necessário
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository repository;

    // 1. Listar todas as reservas
    public List<Reserva> listarTodos() {
        return repository.findAll();
    }

    // 2. Buscar uma reserva por ID
    public Optional<Reserva> buscarPorId(Long id) {
        return repository.findById(id);
    }

    // 3. Salvar uma nova reserva
    @Transactional
    public Reserva salvar(Reserva reserva) {
        return repository.save(reserva);
    }

    // 4. Atualizar uma reserva existente (Padrão igual ao seu QuartoService)
    @Transactional
    public Reserva atualizar(Long id, Reserva dados) {
        // Busca a reserva existente ou lança uma exceção se não encontrar
        Reserva reserva = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada com o ID: " + id));

        // Copia apenas os campos fornecidos do body para a entidade gerenciada
        if (dados.getHospede() != null) {
            reserva.setHospede(dados.getHospede());
        }
        
        if (dados.getQuarto() != null) {
            reserva.setQuarto(dados.getQuarto());
        }

        if (dados.getDataCheckin() != null) {
            reserva.setDataCheckin(dados.getDataCheckin());
        }

        if (dados.getDataCheckout() != null) {
            reserva.setDataCheckout(dados.getDataCheckout());
        }

        if (dados.getStatusReserva() != null) {
            reserva.setStatusReserva(dados.getStatusReserva()); // Aqui pode ser o Enum de Status (ex: PENDENTE, CONFIRMADA)
        }

        if (dados.getValorTotal() != null) {
            reserva.setValorTotal(dados.getValorTotal());
        }

        // 5. Persiste e retorna - o JPA emite UPDATE na transação
        return repository.save(reserva);
    }

    // 6. Deletar uma reserva
    @Transactional
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Não é possível deletar. Reserva não encontrada.");
        }
        repository.deleteById(id);
    }
}