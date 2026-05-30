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

@ExtendWith(MockitoExtension.class) // Inicializa os testes com Mockito puro
class UsuariosServiceTest {

    @Mock
    private UsuariosRepository repository; // Dublê do banco de dados
    @InjectMocks
    private UsuariosService service; // Instancia o Service injetando o mock acima [cite: 119, 165]

    @Test
    void deveLancarValidationExceptionQuandoLoginJaExistir() {
        // ARRANGE: preparação
        UsuariosRequestDTO dto = new UsuariosRequestDTO("usuario_teste", "senha123", Perfil.ADMIN);

        // Programando o mock: quando o service perguntar se esse login existe, o mock
        // diz que SIM (true)
        when(repository.existsByLoginIgnoreCase("usuario_teste")).thenReturn(true);

        // ACT & ASSERT (Executa esperando que a exceção seja estourada)
        assertThrows(ValidationException.class, () -> {
            service.salvar(dto);
        });

        // Garante que o método do repositório foi consultado exatamente 1 vez
        verify(repository, times(1)).existsByLoginIgnoreCase("usuario_teste");
    }
}