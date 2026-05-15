package com.example.casa_de_mainha.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "serviços")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")
public class Serviços {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O Nome é um campo obrigatório")
    @Size(max = 100, message = "O nome deve ter um maximo de {max} dígitos")
    @Column(unique = true, nullable = false, length = 100)
    private String nome;

    @NotBlank(message = "A Descrição é um campo obrigatório")
    @Size(max = 200, message = "A Descrição deve ter um maximo de {max} dígitos")
    @Column(nullable = false, length = 200)
    private String descricao;

    @NotBlank(message = "O Preço é um campo obrigatório")
    @Column(nullable = false)
    private BigDecimal preço;
}
