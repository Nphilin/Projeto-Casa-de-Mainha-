package com.example.casa_de_mainha.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.casa_de_mainha.DTO.ConsumoRequestDTO;
import com.example.casa_de_mainha.DTO.ConsumoResponseDTO;
import com.example.casa_de_mainha.Entity.Consumo;
import com.example.casa_de_mainha.Exception.ResourceNotFoundException;
import com.example.casa_de_mainha.Exception.ValidationException;
import com.example.casa_de_mainha.Repository.ConsumoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConsumoService {

    private final ConsumoRepository consumoRepository;

    @Transactional(readOnly = true)
    public List<ConsumoResponseDTO> listar() {
        return StreamSupport.stream(consumoRepository.findAll().spliterator(), false)
                .map(ConsumoResponseDTO::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ConsumoResponseDTO findById(Long id) {
        Consumo consumo = consumoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consumo não encontrado com o ID: " + id));

        return ConsumoResponseDTO.from(consumo);
    }

    @Transactional(readOnly = true)
    public List<ConsumoResponseDTO> findByReservaId(Long reservaId) {
        List<Consumo> consumos = consumoRepository.findByReservaId(reservaId);
        return consumos.stream()
                .map(ConsumoResponseDTO::from)
                .toList();
    }

    @Transactional
    public ConsumoResponseDTO save(ConsumoRequestDTO dto) {
        for (Consumo existente : consumoRepository.findAll()) {
            if (existente.getDescricao() != null && existente.getDescricao().equalsIgnoreCase(dto.descrição())) {
                throw new ValidationException("Consumo", dto.descrição());
            }
        }

        Consumo consumo = new Consumo();
        consumo.setReserva(dto.reserva());
        consumo.setDescricao(dto.descrição());
        consumo.setValor(dto.valor());
        consumo.setDataConsumo(dto.dataConsumo());

        Consumo consumoSalvo = consumoRepository.save(consumo);
        return ConsumoResponseDTO.from(consumoSalvo);
    }

    @Transactional
    public ConsumoResponseDTO atualizar(Long id, ConsumoRequestDTO dto) {
        Consumo atual = consumoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consumo não encontrado com o ID: " + id));

        atual.setDescricao(dto.descrição());
        atual.setValor(dto.valor());
        atual.setReserva(dto.reserva());
        atual.setDataConsumo(dto.dataConsumo());

        Consumo consumoAtualizado = consumoRepository.save(atual);
        return ConsumoResponseDTO.from(consumoAtualizado);
    }

    @Transactional
    public void deletar(Long id) {
        Consumo consumo = consumoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consumo não encontrado com o ID: " + id));
        consumoRepository.delete(consumo);
    }
}