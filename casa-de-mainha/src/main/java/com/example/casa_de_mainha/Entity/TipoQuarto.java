package com.example.casa_de_mainha.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Evita erros de serialização com Lazy Loading
@Entity
@Table(name = "tipos_quarto")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString(exclude = "quartos")
@EqualsAndHashCode(exclude = "quartos")
public class TipoQuarto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do tipo de quarto é obrigatório")
    @Column(nullable = false, length = 100)
    private String nome; // Ex: Luxo, Standard, Presidencial

    @Size(max = 300)
    private String descricao;

    @NotNull
    @Min(1)
    private Integer capacidade; // Quantidade de pessoas

    @NotNull
    @DecimalMin("0.00")
    @Column(precision = 10, scale = 2)
    private BigDecimal precoBase;

    // Relacionamento Um-para-Muitos: Um tipo pode ter vários quartos físicos
    @JsonIgnore
    @OneToMany(mappedBy = "tipoQuarto", cascade = CascadeType.ALL)
    private List<Quarto> quartos = new ArrayList<>();
}