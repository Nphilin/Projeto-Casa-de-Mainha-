package com.example.casa_de_mainha.DTO;

import java.math.BigDecimal;
import jakarta.validation.constraints.*;

public record QuartoRequestDTO(
        @NotBlank(message = "O número do quarto é obrigatório.")
        @Size(max = 10, message = "O número do quarto deve ter no máximo 10 caracteres.")
        String numero,

        @NotNull(message = "O preço da diária é obrigatório.")
        @DecimalMin(value = "0.0", inclusive = false, message = "O preço da diária deve ser maior que zero.")
        BigDecimal precoDiaria,

        @NotNull(message = "O tipo de quarto é obrigatório.")
        Long tipoQuartoId
) {
}