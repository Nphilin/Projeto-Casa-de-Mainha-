package com.example.casa_de_mainha.Repository;

import com.example.casa_de_mainha.Entity.TipoQuarto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TipoQuartoRepository extends CrudRepository<TipoQuarto, Long> {

    // Busca categorias pelo nome (ex: buscar todas que tenham "Luxo")
    List<TipoQuarto> findByNomeContainingIgnoreCase(String nome);

    // Busca categorias que suportem X pessoas ou mais
    List<TipoQuarto> findByCapacidadeGreaterThanEqual(Integer capacidade);
}