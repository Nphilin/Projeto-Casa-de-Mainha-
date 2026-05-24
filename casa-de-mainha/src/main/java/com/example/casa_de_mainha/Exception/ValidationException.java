package com.example.casa_de_mainha.Exception;

// Exceção customizada para regras de negócio (ex: não aceitar nome duplicado)
public class ValidationException extends RuntimeException {
    
    public ValidationException(String recurso, String nome) {
        super("Erro de validação em " + recurso + ": o item '" + nome + "' já existe ou é inválido.");
    }

    public ValidationException(String mensagem) {
        super(mensagem);
    }
}