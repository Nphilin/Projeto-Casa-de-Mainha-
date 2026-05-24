package com.example.casa_de_mainha.Service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import com.example.casa_de_mainha.Repository.ConsumoRepository;
import com.example.casa_de_mainha.Entity.Consumo;
import com.example.casa_de_mainha.Exception.ResourceNotFoundException;
import com.example.casa_de_mainha.Exception.ValidationException;

import java.util.List;

@Service
@RequiredArgsConstructor // Lombok gera o construtor com final fields (DI) 
public class ConsumoService {

    private final ConsumoRepository consumoRepository;

    @Transactional(readOnly = true) // Otimiza leituras: sem lock, sem flush [cite: 86, 87]
    public Iterable<Consumo> findAll() {
        return consumoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Consumo findById(Long id) {
        // Lança exceção se não encontrado, nunca retorna null [cite: 90, 91]
        return consumoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Consumo not found with id " + id));
    }

    @Transactional(readOnly = true)
    public List<Consumo> findByReservaId(Long reservaId) {
        return consumoRepository.findByReservaId(reservaId);
    }

    @Transactional // Atomicidade: rollback automático em exceção [cite: 88, 89]
    public Consumo save(Consumo consumo) {
        for (Consumo existente : consumoRepository.findAll()) {
            if (existente.getDescricao() != null && existente.getDescricao().equalsIgnoreCase(consumo.getDescricao())) {
                throw new ValidationException("Consumo", consumo.getDescricao());
            }
        }
        return consumoRepository.save(consumo);
    }

    // A lógica do PUT veio para cá [cite: 262, 267]
    @Transactional
    public Consumo atualizar(Long id, Consumo dados) {
        // 1. Busca (lança 404 se não existir graças ao método acima) [cite: 269]
        Consumo atual = findById(id);
        
        // 2. Atualiza só os campos permitidos. Nunca faça atual = dados. [cite: 271, 287, 288]
        atual.setDescricao(dados.getDescricao());
        atual.setValor(dados.getValor());
        atual.setReserva(dados.getReserva());
        
        // 3. Salva e retorna atualizado [cite: 275, 276]
        return consumoRepository.save(atual);
    }

    // A lógica do DELETE veio para cá [cite: 277, 279]
    @Transactional
    public void deletar(Long id) {
        // Garante 404 antes de tentar deletar e usa delete(obj) [cite: 281, 289, 290]
        Consumo consumo = findById(id);
        consumoRepository.delete(consumo);
    }
}