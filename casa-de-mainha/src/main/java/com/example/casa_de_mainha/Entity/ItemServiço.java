package com.example.casa_de_mainha.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "item_serviço")
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = { "reserva", "serviços" })
@EqualsAndHashCode(of = "id")
public class ItemServiço {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O Reserva é um campo obrigatório")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reserva_id", nullable = false)
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private Reserva reserva;

    @NotNull(message = "O Serviços é um campo obrigatório")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "servico_id", nullable = false)
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private Serviços serviços;

    @NotNull(message = "a Data de uso é obrigatório")
    @Column(name = "data_uso", nullable = false)
    private LocalDateTime dataUso;

    @NotNull(message = "a quantidade é obrigatório")
    @Positive(message = "A quantidade precisa ser um numero positivo")
    @Column(nullable = false)
    private Integer quantidade;

    @NotNull(message = "O valor pago é obrigatório")
    @PositiveOrZero
    @Column(name = "valor_pago", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorPago;
}