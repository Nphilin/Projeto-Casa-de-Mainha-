package com.example.casa_de_mainha.Repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.example.casa_de_mainha.Entity.Consumo;
import com.example.casa_de_mainha.Entity.Reserva;

@DataJpaTest
class ConsumoRepositoryTest {

    @Autowired
    private ConsumoRepository consumoRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void deveBuscarConsumosPorReservaIdComSucesso() {
        Reserva reserva = new Reserva();
        reserva = entityManager.persistAndFlush(reserva);

        Consumo consumo = new Consumo();
        consumo.setDescricao("Item Teste");
        consumo.setValor(new BigDecimal("10.00"));
        consumo.setDataConsumo(LocalDateTime.now());
        consumo.setReserva(reserva);

        consumoRepository.save(consumo);

        List<Consumo> resultado = consumoRepository.findByReservaId(reserva.getId());

        assertThat(resultado).isNotEmpty().hasSize(1);
        assertThat(resultado.get(0).getDescricao()).isEqualTo("Item Teste");
        assertThat(resultado.get(0).getReserva().getId()).isEqualTo(reserva.getId());
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHouverConsumosParaOReservaId() {
        List<Consumo> resultado = consumoRepository.findByReservaId(999L);

        assertThat(resultado).isEmpty();
    }
}