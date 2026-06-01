package com.example.casa_de_mainha.Controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.casa_de_mainha.DTO.ConsumoRequestDTO;
import com.example.casa_de_mainha.DTO.ConsumoResponseDTO;
import com.example.casa_de_mainha.Entity.Reserva;
import com.example.casa_de_mainha.Service.ConsumoService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ConsumoController.class)
class ConsumoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ConsumoService consumoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveCriarConsumoERetornar201() throws Exception {
        Reserva reserva = new Reserva();
        ConsumoRequestDTO requestDTO = new ConsumoRequestDTO(reserva, "Coca Cola", new BigDecimal("8.50"),
                LocalDateTime.now());
        ConsumoResponseDTO responseDTO = new ConsumoResponseDTO(1L, null, "Coca Cola", new BigDecimal("8.50"),
                LocalDateTime.now());

        when(consumoService.save(any(ConsumoRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/api/v1/consumos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.descrição").value("Coca Cola"));
    }

    @Test
    void deveRetornar400QuandoRequestDTOInvalido() throws Exception {
        ConsumoRequestDTO requestInvalido = new ConsumoRequestDTO(null, "", null, null);

        mockMvc.perform(post("/api/v1/consumos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestInvalido)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveListarTodosRetornando200() throws Exception {
        ConsumoResponseDTO responseDTO = new ConsumoResponseDTO(1L, null, "Item", new BigDecimal("10.0"),
                LocalDateTime.now());
        when(consumoService.listar()).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/api/v1/consumos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    void deveListarPorReservaIdRetornando200() throws Exception {
        ConsumoResponseDTO responseDTO = new ConsumoResponseDTO(1L, null, "Item Reserva", new BigDecimal("15.0"),
                LocalDateTime.now());
        when(consumoService.findByReservaId(1L)).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/api/v1/consumos/reserva/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].descrição").value("Item Reserva"));
    }

    @Test
    void deveBuscarPorIdRetornando200() throws Exception {
        ConsumoResponseDTO responseDTO = new ConsumoResponseDTO(1L, null, "Almoço", new BigDecimal("30.0"),
                LocalDateTime.now());
        when(consumoService.findById(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/api/v1/consumos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.descrição").value("Almoço"));
    }

    @Test
    void deveAtualizarRetornando200() throws Exception {
        Reserva reserva = new Reserva();
        ConsumoRequestDTO requestDTO = new ConsumoRequestDTO(reserva, "Almoço Executivo", new BigDecimal("35.00"),
                LocalDateTime.now());
        ConsumoResponseDTO responseDTO = new ConsumoResponseDTO(1L, null, "Almoço Executivo", new BigDecimal("35.00"),
                LocalDateTime.now());

        when(consumoService.atualizar(eq(1L), any(ConsumoRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(put("/api/v1/consumos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descrição").value("Almoço Executivo"));
    }

    @Test
    void deveDeletarRetornando204() throws Exception {
        doNothing().when(consumoService).deletar(1L);

        mockMvc.perform(delete("/api/v1/consumos/1"))
                .andExpect(status().isNoContent());
    }
}