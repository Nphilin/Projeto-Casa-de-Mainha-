package com.example.casa_de_mainha.Repository;

import com.example.casa_de_mainha.Entity.Quarto;
import com.example.casa_de_mainha.Entity.StatusQuarto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface QuartoRepository extends CrudRepository<Quarto, Long> {

    // Busca todos os quartos com um status específico (ex: DISPONIVEL)
    List<Quarto> findByStatus(StatusQuarto status);

    // Busca quartos de uma categoria específica
    List<Quarto> findByTipoQuartoId(Long tipoQuartoId);

    // Verifica se um número de quarto já existe
    boolean existsByNumero(String numero);
}