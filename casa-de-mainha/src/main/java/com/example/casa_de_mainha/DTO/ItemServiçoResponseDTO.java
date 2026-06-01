package com.example.casa_de_mainha.DTO;

import com.example.casa_de_mainha.Entity.ItemServiço;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ItemServiçoResponseDTO(
        Long id,
        Long reservaId,
        Long servicoId,
        LocalDateTime dataUso,
        Integer quantidade,
        BigDecimal valorPago
) {
    // Método Factory exigido na Semana 4 para converter Entity -> DTO
    public static ItemServiçoResponseDTO from(ItemServiço entity) {
        return new ItemServiçoResponseDTO(
                entity.getId(),
                // Puxando apenas os IDs das entidades relacionadas para não vazar o objeto inteiro
                entity.getReserva() != null ? entity.getReserva().getId() : null,
                entity.getServiços() != null ? entity.getServiços().getId() : null,
                entity.getDataUso(),
                entity.getQuantidade(),
                entity.getValorPago()
        );
    }
}