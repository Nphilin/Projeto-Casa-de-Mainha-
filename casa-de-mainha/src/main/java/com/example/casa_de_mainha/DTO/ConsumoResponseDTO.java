package com.example.casa_de_mainha.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.casa_de_mainha.Entity.Consumo;
import com.example.casa_de_mainha.Entity.Reserva;

public record ConsumoResponseDTO(
        Long id,
        Reserva reserva,
        String descrição,
        BigDecimal valor,
        LocalDateTime dataConsumo) {

    // Método estático (Factory) para converter Entity -> DTO
    public static ConsumoResponseDTO from(Consumo consumo) {
        return new ConsumoResponseDTO(
                consumo.getId(),
                consumo.getReserva(),
                consumo.getDescricao(),
                consumo.getValor(),
                consumo.getDataConsumo());
    }
}