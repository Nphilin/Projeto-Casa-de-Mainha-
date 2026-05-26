package com.example.casa_de_mainha.Service;

import com.example.casa_de_mainha.Entity.Reserva;
import com.example.casa_de_mainha.Repository.ReservaRepository;
import com.example.casa_de_mainha.Exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor // Lombok substitui o @Autowired e cria o construtor
public class ReservaService {

    private final ReservaRepository repository; // Obrigatório ser private final

    @Transactional(readOnly = true)
    public List<Reserva> listarTodos() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Reserva buscarPorId(Long id) {
        // Lança a sua exceção customizada (que vira Erro 404) se não encontrar
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva não encontrada com id: " + id));
    }

    @Transactional
    public Reserva salvar(Reserva reserva) {
        return repository.save(reserva);
    }

    @Transactional
    public Reserva atualizar(Long id, Reserva dados) {
        // 1. Busca a reserva existente (já lança 404 se não achar)
        Reserva atual = buscarPorId(id);

        // 2. Atualiza os campos limpos, sem aquele monte de if()
        atual.setHospede(dados.getHospede());
        atual.setQuarto(dados.getQuarto());
        atual.setDataCheckin(dados.getDataCheckin());
        atual.setDataCheckout(dados.getDataCheckout());
        atual.setStatusReserva(dados.getStatusReserva());
        atual.setValorTotal(dados.getValorTotal());
        atual.setItemServiço(dados.getItemServiço());

        // 3. Salva e retorna
        return repository.save(atual);
    }

    @Transactional
    public void deletar(Long id) {
        // Garante o Erro 404 antes de deletar
        Reserva reserva = buscarPorId(id);
        repository.delete(reserva);
    }

    @Transactional(readOnly = true)
    public List<Reserva> listarPorStatus(Reserva.StatusReserva status) {
        return repository.findByStatusReserva(status);
    }
}