package com.example.casa_de_mainha.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "reservas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString // Como não há relações OneToMany aqui, o ToString padrão é seguro
@EqualsAndHashCode(of = "id") // Boa prática: usar apenas o ID para equals em entidades JPA
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospede_id", nullable = false)
    private Hospede hospede;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quarto_id", nullable = false)
    private Quarto quarto;

    @NotNull
    @FutureOrPresent
    @Column(nullable = false)
    private LocalDate dataCheckin;

    @NotNull
    @Future // Checkout deve ser sempre após o Checkin
    @Column(nullable = false)
    private LocalDate dataCheckout;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer = 10, fraction = 2)
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal valorTotal;

    @NotNull
    @Enumerated(EnumType.STRING) // Salva o nome da constante no banco (mais legível)
    @Column(nullable = false)
    private StatusReserva statusReserva;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private LocalDateTime dataCriacao;

    // Enum para controle de status
    public enum StatusReserva {
        CONFIRMADA,
        CANCELADA,
        FINALIZADA,
        CHECK_IN_REALIZADO
    }

    @JsonIgnore
    @OneToMany(mappedBy = "reserva", cascade = CascadeType.ALL)
    private List<ItemServiço> itemServiço = new ArrayList<>();
}