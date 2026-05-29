package com.example.casa_de_mainha.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "consumos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "reserva") // Evita recursividade no log se Reserva tiver lista de consumos
@EqualsAndHashCode(of = "id")
public class Consumo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reserva_id", nullable = false)
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private Reserva reserva;

    @NotBlank
    @Size(max = 200)
    @Column(nullable = false, length = 200)
    private String descricao; // Ex: "Frigobar - Água", "Serviço de Quarto"

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal valor;

    @CreationTimestamp // Define a data/hora automaticamente no momento da inserção
    @Column(nullable = false, updatable = false)
    private LocalDateTime dataConsumo;
}