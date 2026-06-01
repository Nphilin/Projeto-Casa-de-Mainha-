package com.example.casa_de_mainha.DTO;

import com.example.casa_de_mainha.Entity.Hospede; // ajuste o import se necessário

public record HospedeResponseDTO(Long id, String nome, String cpf, String email) {
    
    // Construtor que transforma a Entidade em DTO
    public HospedeResponseDTO(Hospede hospede) {
        this(hospede.getId(), hospede.getNome(), hospede.getCpf(), hospede.getEmail());
    }
}