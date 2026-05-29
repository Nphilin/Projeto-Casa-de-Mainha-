package com.example.casa_de_mainha.Repository;

import com.example.casa_de_mainha.Entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {


    @Query("SELECT r FROM Reserva r WHERE r.quarto.id = :quartoId " +
           "AND :dataCheckin < r.dataCheckout AND :dataCheckout > r.dataCheckin")
    Optional<Reserva> findSobreposicao(@Param("quartoId") Long quartoId, 
                                       @Param("dataCheckin") LocalDate dataCheckin, 
                                       @Param("dataCheckout") LocalDate dataCheckout);


    // Busca todas as reservas de um hóspede específico
    List<Reserva> findByHospedeId(Long hospedeId);

    // Busca reservas por status (ex: todas as CANCELADAS)
    List<Reserva> findByStatusReserva(Reserva.StatusReserva status);

    // Busca reservas que fazem check-in em uma data específica
    List<Reserva> findByDataCheckin(LocalDate data);

    // Busca reservas ativas dentro de um período (útil para relatórios)
    List<Reserva> findByDataCheckinBetween(LocalDate inicio, LocalDate fim);
}