package com.example.casa_de_mainha.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ItemServiçoRequestDTO(
        
        @NotNull(message = "O ID da Reserva é obrigatório")
        Long reservaId,

        @NotNull(message = "O ID do Serviço é obrigatório")
        Long servicoId,

        @NotNull(message = "A Data de uso é obrigatória")
        LocalDateTime dataUso,

        @NotNull(message = "A quantidade é obrigatória")
        @Positive(message = "A quantidade precisa ser um numero positivo")
        Integer quantidade,

        @NotNull(message = "O valor pago é obrigatório")
        @PositiveOrZero(message = "O valor pago não pode ser negativo")
        BigDecimal valorPago
) {
}