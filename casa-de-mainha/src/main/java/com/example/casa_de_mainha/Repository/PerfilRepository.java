package com.example.casa_de_mainha.Repository;

import com.example.casa_de_mainha.Entity.Perfil;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerfilRepository extends CrudRepository<Perfil, Long> {
    
    // Método para verificar se já existe um perfil com o mesmo nome (ex: "ADMIN")
    boolean existsByNomeIgnoreCase(String nome);
}