package com.example.casa_de_mainha.Service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;    
import lombok.RequiredArgsConstructor;
import com.example.casa_de_mainha.Repository.ItemServiçoRepository;
import com.example.casa_de_mainha.Entity.ItemServiço;
import com.example.casa_de_mainha.Exception.ResourceNotFoundException;


import java.util.List;

@Service
@RequiredArgsConstructor // Lombok gera o construtor com final fields (DI)

public class ItemServiçoService {

    private final ItemServiçoRepository itemServiçoRepository;

    @Transactional(readOnly = true) // Otimiza leituras: sem lock, sem flush [cite: 86, 87]
    public Iterable<ItemServiço> findAll() {
        return itemServiçoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public ItemServiço findById(Long id) {
        // Lança exceção se não encontrado, nunca retorna null [cite: 90, 91]
        return itemServiçoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("ItemServiço not found with id " + id));
    }

    @Transactional(readOnly = true)
    public List<ItemServiço> findByReservaId(Long reservaId) {
        return itemServiçoRepository.findByReservaId(reservaId);
    }

    @Transactional // Atomicidade: rollback automático em exceção [cite: 88, 89]
    public ItemServiço save(ItemServiço itemServiço) {
        return itemServiçoRepository.save(itemServiço);
    }

    @Transactional // Atomicidade: rollback automático em exceção [cite: 88, 89]
    public void deletar(Long id) {
        // Verifica existência antes de deletar para lançar 404 se não existir [cite: 251]
        findById(id); // Se não existir, lança ResourceNotFoundException
        itemServiçoRepository.deleteById(id);
    }

    // A lógica do PUT veio para cá [cite: 262, 267]
    @Transactional
    public ItemServiço atualizar(Long id, ItemServiço dados) {
        // 1. Busca (lança 404 se não existir graças ao método acima) [cite: 269]
        ItemServiço atual = findById(id);
        
        // 2. Atualiza só os campos permitidos. Nunca faça atual = dados. [cite: 271, 287, 288]
        atual.setDataUso(dados.getDataUso());
        atual.setReserva(dados.getReserva());
        atual.setQuantidade(dados.getQuantidade());
        atual.setValorPago(dados.getValorPago());
        atual.setServiços(dados.getServiços());
        
        // 3. Salva e retorna atualizado [cite: 275, 276]
        return itemServiçoRepository.save(atual);
    }
}