package com.example.casa_de_mainha.Repository;

import com.example.casa_de_mainha.Entity.Consumo;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface ConsumoRepository extends CrudRepository<Consumo, Long> {

    // Busca todos os gastos extras de uma reserva específica
    List<Consumo> findByReservaId(Long reservaId);
}