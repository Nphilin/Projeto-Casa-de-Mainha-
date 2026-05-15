package com.example.casa_de_mainha.Repository;

import com.example.casa_de_mainha.Entity.Consumo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ConsumoRepository extends JpaRepository<Consumo, Long> {
    
    // Busca todos os gastos extras de uma reserva específica
    List<Consumo> findByReservaId(Long reservaId);
}