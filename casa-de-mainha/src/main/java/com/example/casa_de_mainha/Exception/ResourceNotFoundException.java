package com.example.casa_de_mainha.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// @ResponseStatus: quando essa exceção escapar de um controller sem handler, o Spring
// usa NOT_FOUND (404) automaticamente. Com GlobalExceptionHandler, funciona como documentação.
@ResponseStatus(HttpStatus.NOT_FOUND)
// Estende RuntimeException: não precisa de try/catch no chamador — o
// GlobalExceptionHandler captura
public class ResourceNotFoundException extends RuntimeException {

    // Recebe a mensagem descrevendo o que não foi encontrado
    // ex.: "Categoria não encontrada com id: 5"
    public ResourceNotFoundException(String message) {
        super(message); // repassa para RuntimeException → ex.getMessage() devolve essa string
    }
}