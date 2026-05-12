package com.example.casa_de_mainha.Controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. Desabilita CSRF (necessário para o H2 processar comandos)
                .csrf(csrf -> csrf.disable())

                // 2. Libera os Frames (o H2 usa frames na interface)
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))

                // 3. Libera o acesso a todas as rotas (bom para fase de desenvolvimento)
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())

                // 4. Mantém o login básico ativo caso precise
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults());

        return http.build();
    }
}