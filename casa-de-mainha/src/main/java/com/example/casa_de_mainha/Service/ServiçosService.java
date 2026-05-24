package com.example.casa_de_mainha.Service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import com.example.casa_de_mainha.Entity.Serviços;
import com.example.casa_de_mainha.Repository.ServiçosRepository;
import com.example.casa_de_mainha.Exception.ResourceNotFoundException;
import com.example.casa_de_mainha.Exception.ValidationException;

@Service
@RequiredArgsConstructor // Lombok gera o construtor com final fields (DI)

public class ServiçosService {

    private final ServiçosRepository serviçosRepository;

    @Transactional(readOnly = true) // Otimiza leituras: sem lock, sem flush [cite: 86, 87]
    public Iterable<Serviços> findAll() {
        return serviçosRepository.findAll();
    }

   @Transactional // Atomicidade: rollback automático em exceção [cite: 88, 89]
    public Serviços save (Serviços serviços) {
        // Verifica duplicata antes de salvar usando Query method [cite: 92, 93]
        if (serviçosRepository.existsByNomeIgnoreCase(serviços.getNome())) {
            throw new ValidationException("Serviço", serviços.getNome()); // Usa a sua exceção customizada
        }
        return serviçosRepository.save(serviços); 
    }


    @Transactional(readOnly = true)
    public Serviços findById(Long id) {
        // Lança exceção se não encontrado, nunca retorna null [cite: 90, 91]
        return serviçosRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Serviços not found with id " + id));
    }


@Transactional // Atomicidade: rollback automático em exceção [cite: 88, 89]
    public void deletar(Long id) {
        
        Serviços serviços = findById(id); // Verifica existência antes de deletar para lançar 404 se não existir [cite: 251]
        
        serviçosRepository.delete(serviços);
    }

    // A lógica do PUT veio para cá [cite: 262, 267]
    @Transactional
    public Serviços atualizar(Long id, Serviços dados) {
        // 1. Busca (lança 404 se não existir graças ao método acima) [cite: 269]
        Serviços atual = findById(id);
        
        // 2. Atualiza só os campos permitidos. Nunca faça atual = dados. [cite: 271, 287, 288]
       atual.setNome(dados.getNome());
       atual.setDescricao(dados.getDescricao());
       atual.setPreço(dados.getPreço());
       atual.setItemServiço(dados.getItemServiço());
        
        // 3. Salva e retorna atualizado [cite: 275, 276]
        return serviçosRepository.save(atual);
    }


}