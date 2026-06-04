package com.example.casa_de_mainha.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.casa_de_mainha.DTO.UsuariosRequestDTO;
import com.example.casa_de_mainha.DTO.UsuariosResponseDTO;
import com.example.casa_de_mainha.Entity.Usuarios;
import com.example.casa_de_mainha.Exception.ResourceNotFoundException;
import com.example.casa_de_mainha.Exception.ValidationException;
import com.example.casa_de_mainha.Repository.UsuariosRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuariosService {

    private final UsuariosRepository repository;

    // 1. LISTAR (Retorna uma Lista de DTOs)
    @Transactional(readOnly = true)
    public List<UsuariosResponseDTO> listar() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(UsuariosResponseDTO::from) // Converte Entity para DTO
                .collect(Collectors.toList());
    }

    // 2. BUSCAR POR ID (Retorna DTO para o Controller)
    @Transactional(readOnly = true)
    public UsuariosResponseDTO buscarPorId(Long id) {
        Usuarios usuario = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com ID: " + id));

        return UsuariosResponseDTO.from(usuario); // Retorna como DTO
    }

    // 3. SALVAR / CRIAR (Recebe RequestDTO e retorna ResponseDTO)
    @Transactional
    public UsuariosResponseDTO salvar(UsuariosRequestDTO dto) {
        // Validação eficiente usando o Repository em vez de carregar tudo com laço
        // 'for'
        if (repository.existsByLoginIgnoreCase(dto.login())) {
            throw new ValidationException("Usuário", dto.login() + " (Você já tem um usuário com esse login!)");
        }

        Usuarios novoUsuario = new Usuarios();
        novoUsuario.setLogin(dto.login());
        novoUsuario.setSenha(dto.senha());
        novoUsuario.setPerfil(dto.perfil());

        Usuarios salvo = repository.save(novoUsuario);
        return UsuariosResponseDTO.from(salvo);
    }

    // 4. ATUALIZAR (Recebe RequestDTO e retorna ResponseDTO)
    @Transactional
    public UsuariosResponseDTO atualizar(Long id, UsuariosRequestDTO dto) {
        // Busca a entidade bruta para manipulação interna
        Usuarios atual = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com ID: " + id));

        // Atualiza os campos a partir do DTO
        atual.setLogin(dto.login());
        atual.setSenha(dto.senha());
        atual.setPerfil(dto.perfil());

        Usuarios atualizado = repository.save(atual);
        return UsuariosResponseDTO.from(atualizado);
    }

    // 5. DELETAR
    @Transactional
    public void deletar(Long id) {
        // 1. Busca a ENTIDADE (Usuario) no banco, e não o DTO
        Usuarios usuario = repository.findById(id)
                // Caso não encontrado lança a exception not found.
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com ID: " + id));

        // 2. Se encontrado, ele deleta a entidade.
        repository.delete(usuario);
    }
}
