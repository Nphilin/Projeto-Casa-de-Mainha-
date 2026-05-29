package com.example.casa_de_mainha.Service;

import com.example.casa_de_mainha.Entity.Quarto;
import com.example.casa_de_mainha.Repository.QuartoRepository;
import com.example.casa_de_mainha.Exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service // Marca a classe como um componente Spring [cite: 82, 83]
@RequiredArgsConstructor // Injeta o repository pelo construtor automaticamente [cite: 84, 85]
public class QuartoService {

    private final QuartoRepository repository; // Obrigatório ser private final [cite: 103, 115]

    @Transactional(readOnly = true) // Otimiza a consulta no banco [cite: 86, 87]
    public Iterable<Quarto> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Quarto findById(Long id) {
        // Lança erro 404 automaticamente se o ID do quarto não existir [cite: 90, 91]
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quarto não encontrado com o id " + id));
    }

    @Transactional // Garante que a operação de escrita é segura [cite: 88, 89]
    public Quarto save(Quarto quarto) {
        // DICA: Se o seu Quarto tiver um atributo "numero", e você não quiser
        // dois quartos com o mesmo número, crie o existsByNumero no Repository e ative
        // isso:
        /*
         * if (repository.existsByNumero(quarto.getNumero())) {
         * throw new ValidationException("Quarto", quarto.getNumero().toString());
         * }
         */
        return repository.save(quarto);
    }

    @Transactional
    public Quarto atualizar(Long id, Quarto dados) {
        // 1. Busca o quarto no banco (já lança o 404 se não achar) [cite: 269, 270]
        Quarto atual = findById(id);

        atual.setNumero(dados.getNumero());
        atual.setTipoQuarto(dados.getTipoQuarto());
        atual.setStatus(dados.getStatus());

        // 3. Salva e retorna o quarto atualizado [cite: 275, 276]
        return repository.save(atual);
    }

    @Transactional
    public void deletar(Long id) {
        // Busca a entidade primeiro para garantir que o erro 404 dispare se não existir
        // [cite: 281, 282, 289, 290]
        Quarto quarto = findById(id);
        repository.delete(quarto);
    }
}