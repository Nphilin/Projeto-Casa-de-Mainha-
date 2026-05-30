package com.example.casa_de_mainha.Controller;

import com.example.casa_de_mainha.DTO.UsuariosRequestDTO;
import com.example.casa_de_mainha.DTO.UsuariosResponseDTO;
import com.example.casa_de_mainha.Service.UsuariosService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Usuários", description = "Gerenciamento de usuários e permissões do hotel Casa de Mainha")
@RequestMapping("/api/v1/usuarios")
public class UsuariosController {

    // Injeção de dependência do Service (Padrão recomendado)
    private final UsuariosService service;

    // 0. LISTAR TODOS OS USUARIOS CADASTRADOS
    @GetMapping
    @Operation(summary = "Lista todos os usuários cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    public ResponseEntity<List<UsuariosResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    // 1. BUSCAR POR ID (Cenário GET)
    @GetMapping("/{id}")
    @Operation(summary = "Busca um usuário pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado no sistema") })
    public ResponseEntity<UsuariosResponseDTO> buscarPorId(@PathVariable("id") Long id) {
        // O Service já busca no banco e faz a conversão para ResponseDTO
        UsuariosResponseDTO response = service.buscarPorId(id);

        // Retorna HTTP 200 OK com o JSON do ResponseDTO
        return ResponseEntity.ok(response);
    }

    // 2. CRIAR NOVA RESERVA (Cenário POST)
    @PostMapping
    @Operation(summary = "Cadastra um novo usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de cadastro inválidos ou login duplicado") })
    public ResponseEntity<UsuariosResponseDTO> criar(@RequestBody @Valid UsuariosRequestDTO req) {
        // O @Valid garante que o Spring valide o nome, as datas, etc., antes de entrar
        // aqui
        UsuariosResponseDTO response = service.salvar(req);
        // Retorna HTTP 201 Created com o objeto criado no corpo
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 3.ATUALIZAR USUÁRIO (PUT) - Retorna 200 OK com os dados atualizados
    @PutMapping("/{id}")
    @Operation(summary = "Atualiza os dados de um usuário existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado") })
    public ResponseEntity<UsuariosResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid UsuariosRequestDTO req) {
        // O service processa a alteração e devolve o ResponseDTO
        UsuariosResponseDTO response = service.atualizar(id, req);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui um usuário do sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado") })
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        // O controller só chama o service!
        service.deletar(id);
        // Retorna o status 204 No Content exigido na tabela de cenários
        return ResponseEntity.noContent().build();
    }
}
