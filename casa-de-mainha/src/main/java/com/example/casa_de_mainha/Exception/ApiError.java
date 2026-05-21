package com.example.casa_de_mainha.Exception;

import java.time.LocalDateTime;
import java.util.List;

// record: classe imutável gerada pelo Java (Java 16+) — gera construtor, getters e toString automaticamente
// Será serializado pelo Jackson como JSON: { "status": 404, "message": "...", "timestamp": "...", "errors": null }
public record ApiError(
        int status, // código HTTP (ex.: 404, 400, 500)
        String message, // mensagem principal (ex.: "Categoria não encontrada com id: 1")
        LocalDateTime timestamp, // momento do erro — preenchido automaticamente
        List<String> errors // null para erros simples; lista "campo: motivo" para validação (@Valid)
) {
    // Construtor conveniente para erros simples (404, 500): timestamp gerado aqui
    public ApiError(int status, String message) {
        this(status, message, LocalDateTime.now(), null);
    }

    // Construtor conveniente para erros de validação (400): inclui a lista de
    // campos inválidos
    public ApiError(int status, String message, List<String> errors) {
        this(status, message, LocalDateTime.now(), errors);
    }
}