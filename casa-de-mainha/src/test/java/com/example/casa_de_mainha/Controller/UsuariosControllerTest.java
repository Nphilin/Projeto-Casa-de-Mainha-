package com.example.casa_de_mainha.Controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.casa_de_mainha.Exception.GlobalExceptionHandler;
import com.example.casa_de_mainha.Exception.ResourceNotFoundException;
import com.example.casa_de_mainha.Service.UsuariosService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest({ UsuariosController.class, GlobalExceptionHandler.class }) // Sobe apenas a camada Web isolada
class UsuariosControllerTest {

    @Autowired
    private MockMvc mvc; // Objeto que simula as requisições HTTP (GET, POST, PUT e DELETE)

    @MockitoBean
    private UsuariosService service; // Substitui o Service real por um mock

    @Test
    @WithMockUser // Evita o erro 401 simulando usuário autenticado
    void deveRetornarStatus404AoBuscarUsuarioInexistente() throws Exception {
        // ARRANGE - Preparar
        Long idInexistente = 99L;

        // Quando o controller pedir para o service buscar o ID 99, simulamos o
        // lançamento da exceção
        when(service.buscarPorId(idInexistente))
                .thenThrow(new ResourceNotFoundException("Usuário não encontrado com ID: " + idInexistente));

        // ACT & ASSERT - Executar e Validar
        // Faz o GET simulado e valida se o status retornado é 404 (Not Found)
        mvc.perform(get("/api/v1/usuarios/" + idInexistente))
                .andExpect(status().isNotFound()); // Valida o HTTP 404
    }
}