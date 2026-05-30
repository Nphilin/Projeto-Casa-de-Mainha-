// src/main/java/com/example/demo/entity/Categoria.java
package com.example.casa_de_mainha.Entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "senha")
@EqualsAndHashCode(exclude = "senha")
public class Usuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Preencha o campo de login.")
    @Size(max = 100) // validação de negócio: nome curto e legível
    @Column(unique = true, nullable = false)
    private String login;

    @NotBlank(message = "Preencha o campo obrigatório")
    @Size(min = 8, message = "A senha deve conter no minimo 8 caracteres")
    @Column(nullable = false)
    private String senha;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Preencha o campo obrigatório")
    private Perfil perfil;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime dataCriacao;

}
