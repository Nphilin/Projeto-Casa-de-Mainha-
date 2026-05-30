package com.example.casa_de_mainha.Repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.casa_de_mainha.Entity.Usuarios;
import com.example.casa_de_mainha.Entity.Perfil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest // Sobe apenas o JPA e o banco H2 em memória 
class UsuariosRepositoryTest {

    @Autowired
    private TestEntityManager em; // Usado para persistir dados fictícios de teste 

    @Autowired
    private UsuariosRepository repository;

    @Test
    void deveRetornarVerdadeiroQuandoLoginExistirIgnorandoCase() {
        // ARRANGE (Preparar o ambiente) 
        Usuarios usuario = new Usuarios();
        usuario.setLogin("AdminCasaMainha");
        usuario.setSenha("123456");
        usuario.setPerfil(Perfil.ADMIN); // Ajuste para o  Enum real se necessário Perfil
        
        em.persist(usuario); // Salva temporariamente no H2 
        em.flush();

        // ACT (Executar a ação) 
        boolean existe = repository.existsByLoginIgnoreCase("admincasamainha");

        // ASSERT (Verificar o resultado) 
        assertThat(existe).isTrue(); 
    }
}