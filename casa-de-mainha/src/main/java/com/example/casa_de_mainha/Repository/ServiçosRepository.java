package com.example.casa_de_mainha.Repository;

import com.example.casa_de_mainha.Entity.Serviços;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiçosRepository extends CrudRepository<Serviços, Long> {

    List<Serviços> findByNomeContainingIgnoreCase(String nome);

    Optional<Serviços> findByNomeIgnoreCase(String nome);

    boolean existsByNomeIgnoreCase(String nome);
}
