package com.example.casa_de_mainha.Repository;

import com.example.casa_de_mainha.Entity.Hospede;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface HospedeRepository extends CrudRepository<Hospede, Long> {

    // Query Method: Busca hóspedes pelo trecho do nome (ignora maiúsculas/minúsculas)
    List<Hospede> findByNomeContainingIgnoreCase(String nome);

    // Busca exata por CPF
    Optional<Hospede> findByCpf(String cpf);

    // Verifica se já existe o CPF antes de cadastrar
    boolean existsByCpf(String cpf);
}