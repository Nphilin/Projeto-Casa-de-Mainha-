package com.example.casa_de_mainha.Repository;

import com.example.casa_de_mainha.Entity.Usuarios;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuariosRepository extends CrudRepository<Usuarios, Long> {

    List<Usuarios> findByLoginContainingIgnoreCase(String login);

    Optional<Usuarios> findByLoginIgnoreCase(String login);

    boolean existsByLoginIgnoreCase(String login);

}