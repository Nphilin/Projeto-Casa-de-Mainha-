package com.example.casa_de_mainha.Service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.example.casa_de_mainha.DTO.UsuariosRequestDTO;
import com.example.casa_de_mainha.Entity.Perfil;
import com.example.casa_de_mainha.Exception.ValidationException;
import com.example.casa_de_mainha.Repository.UsuariosRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UsuariosServiceTest {

    @Mock
    private UsuariosRepository repository;

    @InjectMocks
    private UsuariosService service;

    @Test
    void deveLancarValidationExceptionQuandoLoginJaExistir() {
        // ARRANGE
        // ATENÇÃO: a ordem dos argumentos deve bater com a declaração do seu record:
        // record UsuariosRequestDTO(String login, String senha, Perfil perfil)
        UsuariosRequestDTO dto = new UsuariosRequestDTO("usuario_teste", "senha123", Perfil.ADMIN);

        // Mock: quando o repositório for consultado com esse login, retorna true (já
        // existe)
        when(repository.existsByLoginIgnoreCase("usuario_teste")).thenReturn(true);

        // ACT & ASSERT: garante que a exceção correta é lançada
        assertThrows(ValidationException.class, () -> service.salvar(dto));

        // VERIFY: confirma que o repositório foi consultado exatamente 1 vez
        verify(repository, times(1)).existsByLoginIgnoreCase("usuario_teste");
    }
}