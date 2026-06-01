package com.example.casa_de_mainha.Repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.casa_de_mainha.Entity.Usuarios;
import com.example.casa_de_mainha.Entity.Perfil;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

// Sobe apenas o contexto JPA + banco H2 em memória, sem segurança
@DataJpaTest
class UsuariosRepositoryTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private UsuariosRepository repository;

    @Test
    void deveRetornarVerdadeiroQuandoLoginExistirIgnorandoCase() {
        // ARRANGE
        Usuarios usuario = new Usuarios();
        usuario.setLogin("AdminCasaMainha");
        usuario.setSenha("123456");     // preencha todos os campos @Column(nullable=false)
        usuario.setPerfil(Perfil.ADMIN);

        em.persist(usuario);
        em.flush(); // força o INSERT no H2 agora

        // ACT
        boolean existe = repository.existsByLoginIgnoreCase("admincasamainha");

        // ASSERT
        assertThat(existe).isTrue();
    }
}