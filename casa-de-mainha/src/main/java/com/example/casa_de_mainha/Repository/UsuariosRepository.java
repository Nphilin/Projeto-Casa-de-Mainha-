package com.example.casa_de_mainha.Repository;

import com.example.casa_de_mainha.Entity.Usuarios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuariosRepository extends JpaRepository<Usuarios, Long> {

    List<Usuarios> listarLoginContainingIgnoreCase(String login);

    Optional<Usuarios> findByLoginIgnoreCase(String login);

    boolean existsByLoginIgnoreCase(String login);

}