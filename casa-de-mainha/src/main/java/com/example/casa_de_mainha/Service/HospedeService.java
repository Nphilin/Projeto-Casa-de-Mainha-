package com.example.casa_de_mainha.Service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import com.example.casa_de_mainha.Repository.HospedeRepository;
import com.example.casa_de_mainha.Entity.Hospede;
import com.example.casa_de_mainha.Exception.ResourceNotFoundException;
import com.example.casa_de_mainha.Exception.ValidationException;

import java.util.List;

@Service
@RequiredArgsConstructor // Lombok gera o construtor com final fields (DI)

public class HospedeService {

    private final HospedeRepository hospedeRepository;

    @Transactional(readOnly = true) // Otimiza leituras: sem lock, sem flush [cite: 86, 87]
    public Iterable<Hospede> findAll() {
        return hospedeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Hospede> findByNomeContainingIgnoreCase(String nome) {
        return hospedeRepository.findByNomeContainingIgnoreCase(nome);
    }

    @Transactional(readOnly = true)
    public Hospede findById(Long id) {
        // Lança exceção se não encontrado, nunca retorna null [cite: 90, 91]
        return hospedeRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Hospede not found with id " + id));
    }

     @Transactional // Atomicidade: rollback automático em exceção [cite: 88, 89]
     public Hospede save(Hospede hospede) {
        for (Hospede existente : hospedeRepository.findAll()) {
            if (existente.getNome() != null && existente.getNome().equalsIgnoreCase(hospede.getNome())) {
                throw new ValidationException("Hospede", hospede.getNome());
            }
        }
        return hospedeRepository.save(hospede);
    }

    @Transactional
    public Hospede atualizar (Long id, Hospede dados) {
        // 1. Busca (lança 404 se não existir graças ao método acima) [cite: 269]
        Hospede atual = findById(id);
        
        // 2. Atualiza só os campos permitidos. Nunca faça atual = dados. [cite: 271, 287, 288]
        atual.setNome(dados.getNome());
        atual.setCpf(dados.getCpf());
        atual.setTelefone(dados.getTelefone());
        atual.setEmail(dados.getEmail());
        // reserva updated elsewhere or not handled here
        
        // 3. Salva e retorna atualizado [cite: 275, 276]
        return hospedeRepository.save(atual);
    }

    @Transactional
    public void deletar(Long id) {
        // Verifica existência antes de deletar para lançar 404 se não existir [cite: 90, 91]
        Hospede  hospede = findById(id);
        hospedeRepository.delete(hospede);
    }
}