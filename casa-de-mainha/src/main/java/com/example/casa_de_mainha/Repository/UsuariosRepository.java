package com.example.casa_de_mainha.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.casa_de_mainha.Entity.Usuarios;

@Repository
public interface UsuariosRepository extends JpaRepository<Usuarios, Long> {

    List<Usuarios> findByLoginContainingIgnoreCase(String login);

    Optional<Usuarios> findByLoginIgnoreCase(String login);

    boolean existsByLoginIgnoreCase(String login);

}