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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

// Sobe apenas a camada Web + o GlobalExceptionHandler, SEM o SecurityConfig real
@WebMvcTest(controllers = { UsuariosController.class, GlobalExceptionHandler.class })
@Import(UsuariosControllerTest.TestSecurityConfig.class) // Substitui o SecurityConfig real
class UsuariosControllerTest {

    // Configuração de segurança mínima para testes:
    // desabilita CSRF e libera todos os endpoints, evitando
    // que o Spring tente carregar o JwtDecoder do SecurityConfig real
    @Configuration
    static class TestSecurityConfig {
        @Bean
        SecurityFilterChain testFilterChain(HttpSecurity http) throws Exception {
            http
                    .csrf(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
            return http.build();
        }
    }

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private UsuariosService service;

    @Test
    @WithMockUser // Garante usuário autenticado no contexto do Spring Security
    void deveRetornarStatus404AoBuscarUsuarioInexistente() throws Exception {
        // ARRANGE
        Long idInexistente = 99L;

        when(service.buscarPorId(idInexistente))
                .thenThrow(new ResourceNotFoundException("Usuário não encontrado com ID: " + idInexistente));

        // ACT & ASSERT
        mvc.perform(get("/api/v1/usuarios/" + idInexistente))
                .andExpect(status().isNotFound());
    }
}