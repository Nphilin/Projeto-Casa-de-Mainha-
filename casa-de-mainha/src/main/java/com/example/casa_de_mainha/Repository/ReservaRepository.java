package com.example.casa_de_mainha.Repository;

import com.example.casa_de_mainha.Entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    // Busca todas as reservas de um hóspede específico
    List<Reserva> findByHospedeId(Long hospedeId);

    // Busca reservas por status (ex: todas as CANCELADAS)
    List<Reserva> findByStatusReserva(Reserva.StatusReserva status);

    // Busca reservas que fazem check-in em uma data específica
    List<Reserva> findByDataCheckin(LocalDate data);

    // Busca reservas ativas dentro de um período (útil para relatórios)
    List<Reserva> findByDataCheckinBetween(LocalDate inicio, LocalDate fim);
}