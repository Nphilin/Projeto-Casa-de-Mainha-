package com.example.casa_de_mainha.DTO;

import com.example.casa_de_mainha.Entity.Reserva;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.validation.constraints.*;

public record ConsumoRequestDTO(
                @NotNull(message = "Preencha o campo obrigatório") Reserva reserva,

                @NotBlank(message = "Preencha o campo de descrição") @Size(max = 200) String descrição,

                @NotNull(message = "Preencha o campo de valor.") BigDecimal valor,

                @NotNull(message = "Preencha o campo Obrigatório") LocalDateTime dataConsumo) {
}