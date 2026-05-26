package com.example.casa_de_mainha.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Evita erros de serialização com Lazy Loading
@Entity
@Table(name = "quartos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "tipoQuarto")
@EqualsAndHashCode(exclude = "tipoQuarto")
public class Quarto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O número do quarto é obrigatório")
    @Column(nullable = false, unique = true, length = 10)
    private String numero;

    // Relacionamento Muitos-para-Um: Muitos quartos pertencem a um TipoQuarto
    @ManyToOne
    @JoinColumn(name = "tipo_quarto_id", nullable = false)
    private TipoQuarto tipoQuarto;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusQuarto status = StatusQuarto.DISPONIVEL;

    public enum StatusQuarto {
        DISPONIVEL, OCUPADO, MANUTENCAO, LIMPEZA
    }
}