package com.example.casa_de_mainha.Service;

import com.example.casa_de_mainha.Entity.TipoQuarto;
import com.example.casa_de_mainha.Repository.TipoQuartoRepository;
import com.example.casa_de_mainha.Exception.ResourceNotFoundException;
import com.example.casa_de_mainha.Exception.ValidationException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor

public class TipoQuartoService {
    private final TipoQuartoRepository repository;

    @Transactional(readOnly = true)
    public Iterable<TipoQuarto> findAll() {
        return repository.findAll();
    }

    @Transactional
    public TipoQuarto save(TipoQuarto tipoQuarto) {
        // Verifica duplicata antes de salvar usando Query method [cite: 92, 93]
        if (repository.existsByNomeIgnoreCase(tipoQuarto.getNome())) {
            throw new ValidationException("TipoQuarto", tipoQuarto.getNome()); // Usa a sua exceção customizada
        }
        return repository.save(tipoQuarto);
    }

    @Transactional(readOnly = true)
    public TipoQuarto findById(Long id) {
        // Lança erro 404 automaticamente se o ID do tipo de quarto não existir [cite:
        // 90, 91]
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TipoQuarto não encontrado com o id " + id));
    }

    @Transactional
    public void deletar(Long id) {
        TipoQuarto tipoQuarto = findById(id); // Verifica existência antes de deletar para lançar 404 se não existir
                                              // [cite: 251]
        repository.delete(tipoQuarto);
    }

    @Transactional
    public TipoQuarto atualizar(Long id, TipoQuarto dados) {
        // 1. Busca o tipo de quarto no banco (já lança o 404 se não achar) [cite: 269,
        // 270]
        TipoQuarto atual = findById(id);

        // 2. Atualiza apenas os campos permitidos [cite: 271, 287, 288]
        atual.setNome(dados.getNome());
        atual.setDescricao(dados.getDescricao());
        atual.setCapacidade(dados.getCapacidade());
        atual.setPrecoBase(dados.getPrecoBase());

        // 3. Salva e retorna o tipo de quarto atualizado [cite: 275, 276]
        return repository.save(atual);
    }
}