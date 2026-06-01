package com.example.casa_de_mainha.DTO;

import jakarta.validation.constraints.*;

public record HospedeRequestDTO(
        @NotBlank(message = "O nome é obrigatório.") 
        @Size(max = 100) 
        String nome,

        @NotBlank(message = "O CPF é obrigatório.") 
        String cpf,

        @NotBlank(message = "O e-mail é obrigatório.") 
        @Email(message = "E-mail inválido.") 
        String email
) {
}