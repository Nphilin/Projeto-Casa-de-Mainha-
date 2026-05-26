package com.example.casa_de_mainha.Service;

import com.example.casa_de_mainha.Entity.Usuarios;
import com.example.casa_de_mainha.Repository.UsuariosRepository;
import com.example.casa_de_mainha.Exception.ResourceNotFoundException;
import com.example.casa_de_mainha.Exception.ValidationException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service // Marca como bean Spring gerenciado [cite: 83]
@RequiredArgsConstructor // Lombok gera construtor com final fields (DI) [cite: 85]
public class UsuariosService {

    private final UsuariosRepository repository;

    @Transactional(readOnly = true) // Otimiza leituras: sem lock, sem flush [cite: 87]
    public Iterable<Usuarios> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Usuarios findById(Long id) {
        // Lança exceção se não encontrado, nunca retorna null [cite: 90, 91]
        return repository.findById(id).orElseThrow(() -> 
            new ResourceNotFoundException ("Usuário não encontrado com ID: " + id));
    }

    @Transactional // Atomicidade: rollback automático em exceção
    public Usuarios save(Usuarios usuario) {
        // Verifica se o login já existe no banco antes de tentar salvar
        for (Usuarios existente : repository.findAll()) {
            if (existente.getLogin().equals(usuario.getLogin())) {
                throw new ValidationException("Usuário", usuario.getLogin() + " (Você já tem um usuário com esse login!)");
            }
        }
        return repository.save(usuario);
    }

    @Transactional
    public Usuarios atualizar(Long id, Usuarios dados) {
        // 1. Busca (lança 404 se não existir) [cite: 269]
        Usuarios atual = findById(id);

        // 2. Atualiza só os campos permitidos. Nunca faça atual = dados [cite: 288]
        // ATENÇÃO: Substitua pelos campos REAIS da sua entidade Usuarios
       atual.setLogin(dados.getLogin());
        atual.setSenha(dados.getSenha());
        atual.setPerfil(dados.getPerfil());

        // 3. Salva e retorna atualizado [cite: 275, 276]
        return repository.save(atual);
    }

    @Transactional
    public void deletar(Long id) {
        // Garante 404 antes de tentar deletar [cite: 281]
        Usuarios usuario = findById(id);
        
        // repository.delete(obj) confirma a existência antes [cite: 289, 290]
        repository.delete(usuario);
    }
}