package com.example.casa_de_mainha.Service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.casa_de_mainha.DTO.ConsumoRequestDTO;
import com.example.casa_de_mainha.DTO.ConsumoResponseDTO;
import com.example.casa_de_mainha.Entity.Consumo;
import com.example.casa_de_mainha.Entity.Reserva;
import com.example.casa_de_mainha.Exception.ResourceNotFoundException;
import com.example.casa_de_mainha.Exception.ValidationException;
import com.example.casa_de_mainha.Repository.ConsumoRepository;

@ExtendWith(MockitoExtension.class)
class ConsumoServiceTest {

    @Mock
    private ConsumoRepository consumoRepository;

    @InjectMocks
    private ConsumoService consumoService;

    @Test
    void deveListarTodosComSucesso() {
        Consumo consumo = new Consumo();
        consumo.setId(1L);
        consumo.setDescricao("Agua");

        when(consumoRepository.findAll()).thenReturn(List.of(consumo));

        List<ConsumoResponseDTO> resultado = consumoService.listar();

        assertThat(resultado).isNotEmpty().hasSize(1);
        verify(consumoRepository, times(1)).findAll();
    }

    @Test
    void deveBuscarPorIdComSucesso() {
        Consumo consumo = new Consumo();
        consumo.setId(1L);
        consumo.setDescricao("Lanche");

        when(consumoRepository.findById(1L)).thenReturn(Optional.of(consumo));

        ConsumoResponseDTO response = consumoService.findById(1L);

        assertThat(response).isNotNull();
        verify(consumoRepository, times(1)).findById(1L);
    }

    @Test
    void deveLancarExceptionQuandoIdNaoExistir() {
        when(consumoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> consumoService.findById(1L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void deveBuscarPorReservaIdComSucesso() {
        Consumo consumo = new Consumo();
        consumo.setId(1L);
        consumo.setDescricao("Refri");

        when(consumoRepository.findByReservaId(1L)).thenReturn(List.of(consumo));

        List<ConsumoResponseDTO> resultado = consumoService.findByReservaId(1L);

        assertThat(resultado).isNotEmpty().hasSize(1);
        verify(consumoRepository, times(1)).findByReservaId(1L);
    }

    @Test
    void deveSalvarConsumoComSucesso() {
        Reserva reserva = new Reserva();
        ConsumoRequestDTO request = new ConsumoRequestDTO(reserva, "Agua", new BigDecimal("5.00"), LocalDateTime.now());

        Consumo consumoSalvo = new Consumo();
        consumoSalvo.setId(1L);
        consumoSalvo.setDescricao("Agua");

        when(consumoRepository.findAll()).thenReturn(List.of());
        when(consumoRepository.save(any(Consumo.class))).thenReturn(consumoSalvo);

        ConsumoResponseDTO response = consumoService.save(request);

        assertThat(response).isNotNull();
        verify(consumoRepository, times(1)).save(any(Consumo.class));
    }

    @Test
    void deveLancarExceptionQuandoDescricaoJaExistir() {
        Reserva reserva = new Reserva();
        ConsumoRequestDTO request = new ConsumoRequestDTO(reserva, "Agua", new BigDecimal("5.00"), LocalDateTime.now());

        Consumo existente = new Consumo();
        existente.setDescricao("Agua");

        when(consumoRepository.findAll()).thenReturn(List.of(existente));

        assertThatThrownBy(() -> consumoService.save(request))
                .isInstanceOf(ValidationException.class);

        verify(consumoRepository, never()).save(any(Consumo.class));
    }

    @Test
    void deveAtualizarConsumoComSucesso() {
        Reserva reserva = new Reserva();
        ConsumoRequestDTO request = new ConsumoRequestDTO(reserva, "Suco", new BigDecimal("7.00"), LocalDateTime.now());

        Consumo atual = new Consumo();
        atual.setId(1L);
        atual.setDescricao("Agua");

        Consumo atualizado = new Consumo();
        atualizado.setId(1L);
        atualizado.setDescricao("Suco");

        when(consumoRepository.findById(1L)).thenReturn(Optional.of(atual));
        when(consumoRepository.save(any(Consumo.class))).thenReturn(atualizado);

        ConsumoResponseDTO response = consumoService.atualizar(1L, request);

        assertThat(response).isNotNull();
        verify(consumoRepository, times(1)).save(any(Consumo.class));
    }

    @Test
    void deveLancarExceptionAoAtualizarComIdInexistente() {
        Reserva reserva = new Reserva();
        ConsumoRequestDTO request = new ConsumoRequestDTO(reserva, "Suco", new BigDecimal("7.00"), LocalDateTime.now());

        when(consumoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> consumoService.atualizar(1L, request))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(consumoRepository, never()).save(any(Consumo.class));
    }

    @Test
    void deveDeletarConsumoComSucesso() {
        Consumo consumo = new Consumo();
        consumo.setId(1L);

        when(consumoRepository.findById(1L)).thenReturn(Optional.of(consumo));
        doNothing().when(consumoRepository).delete(consumo);

        consumoService.deletar(1L);

        verify(consumoRepository, times(1)).delete(consumo);
    }

    @Test
    void deveLancarExceptionAoDeletarComIdInexistente() {
        when(consumoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> consumoService.deletar(1L))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(consumoRepository, never()).delete(any(Consumo.class));
    }
}