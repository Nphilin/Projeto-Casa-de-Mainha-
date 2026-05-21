package com.example.casa_de_mainha.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

// @RestControllerAdvice = @ControllerAdvice + @ResponseBody
// Intercepta exceções de TODOS os controllers e devolve JSON (não HTML do Tomcat)
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Chamado quando qualquer Service lançar ResourceNotFoundException
    // Fluxo: Controller → Service → lança ResourceNotFoundException → este método →
    // 404 JSON
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiError(404, ex.getMessage())); // ex.getMessage() = mensagem do construtor
    }

    // Chamado quando @Valid detectar campo inválido no RequestBody
    // Fluxo: Controller recebe POST/PUT → @Valid falha →
    // MethodArgumentNotValidException → este método → 400 JSON
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex) {
        // Extrai cada campo com erro: "nome: não deve estar em branco", "preco: deve
        // ser maior que 0"
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage()) // "campo: mensagem da anotação"
                .toList();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiError(400, "Erro de validação", errors));
    }

    // Fallback: captura qualquer exceção não tratada pelos métodos acima
    // Evita que o Spring exponha stack trace ou HTML de erro ao cliente
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiError(500, "Erro interno: " + ex.getMessage()));
    }
}