package com.example.casa_de_mainha.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "item_serviço")
@Getter
@Setter
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
    private Reserva reserva;

    @NotNull(message = "O Serviços é um campo obrigatório")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "servico_id", nullable = false)
    private Serviços serviços;

    @NotNull(message = "a Data de uso é obrigatório")
    @Column(name = "data_uso", nullable = false)
    private LocalDateTime dataUso;

    @Positive(message = "A quantidade precisa ser um numero positivo")
    @Column(nullable = false)
    private Integer quantidade;

    @DecimalMin("0,00")
    @Column(name = "valor_pago", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorPago;
}