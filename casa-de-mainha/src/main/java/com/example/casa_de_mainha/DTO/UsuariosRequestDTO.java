package com.example.casa_de_mainha.DTO;

import com.example.casa_de_mainha.Entity.Perfil;
import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.Size;

import jakarta.validation.constraints.*;

public record UsuariosRequestDTO(

                @NotBlank(message = "Preencha o campo de login.") @Size(max = 100) String login,

                @NotBlank(message = "Preencha o campo de senha") @Size(min = 8, message = "A senha deve conter no minimo 8 caracteres") String senha,

                @NotNull(message = "Preencha o campo obrigatório") Perfil perfil

) {
}
