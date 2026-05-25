package com.example.casa_de_mainha.Service;

import com.example.casa_de_mainha.Entity.Perfil;
import com.example.casa_de_mainha.Exception.ResourceNotFoundException;
import com.example.casa_de_mainha.Repository.PerfilRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service                 // registra este bean no contexto Spring; pode ser injetado por @RequiredArgsConstructor
@Transactional           // todos os métodos desta classe rodam dentro de uma transação por padrão
@RequiredArgsConstructor // Lombok: gera construtor com todos os campos final → injeção sem @Autowired
public class PerfilService {

    // Spring injeta o repository aqui via construtor — final garante imutabilidade
    private final PerfilRepository repository;

    // readOnly = true: Hibernate desativa dirty-checking e flush → só leitura, mais rápido
    @Transactional(readOnly = true)
    public List<Perfil> listarTodos() {
        // CrudRepository.findAll() retorna Iterable — cast para List usado pelo controller
        return (List<Perfil>) repository.findAll();
    }

    @Transactional(readOnly = true)
    public Perfil buscarPorId(Long id) {
        // findById retorna Optional<Perfil>
        // orElseThrow: se vazio, lança ResourceNotFoundException → GlobalExceptionHandler retorna 404
        return repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(
                "Perfil não encontrado com id: " + id));
    }

    public Perfil salvar(Perfil perfil) {
        // save() faz INSERT quando id == null, UPDATE quando id != null
        return repository.save(perfil);
    }

    public Perfil atualizar(Long id, Perfil dados) {
        // 1. Busca a entidade existente — lança 404 automaticamente se não existir
        Perfil perfil = buscarPorId(id);

        // 2. Copia os campos fornecidos do body para a entidade gerenciada pelo JPA
        perfil.setNome(dados.getNome());
        
        // Exemplo de campo opcional: só atualiza se enviado (previne sobrescrever com null)
        if (dados.getDescricao() != null) {   
            perfil.setDescricao(dados.getDescricao());
        }
        
        // Adicione aqui outros sets específicos da sua entidade Perfil, ex:
        // if (dados.getAtivo() != null) { perfil.setAtivo(dados.getAtivo()); }

        // 3. Persiste e retorna — o JPA emite UPDATE na transação
        return repository.save(perfil);
    }

    public void deletar(Long id) {
        // Verifica existência antes de deletar: deleteById é silencioso se id inexistente
        buscarPorId(id); // lança 404 se não existir
        repository.deleteById(id);
    }
}