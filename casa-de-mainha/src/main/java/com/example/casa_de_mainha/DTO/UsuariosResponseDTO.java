package com.example.casa_de_mainha.DTO;

import com.example.casa_de_mainha.Entity.Usuarios;
import com.example.casa_de_mainha.Entity.Perfil;

public record UsuariosResponseDTO(
        Long id,
        String login,
        Perfil perfil) {

    // Método estático (Factory) para converter Entity -> DTO
    public static UsuariosResponseDTO from(Usuarios usuarios) {
        return new UsuariosResponseDTO(
                usuarios.getId(),
                usuarios.getLogin(),
                usuarios.getPerfil());
    }
}