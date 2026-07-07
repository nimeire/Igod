package com.igod.gerenciamento.service;

import com.igod.gerenciamento.model.Culto;
import com.igod.gerenciamento.repository.CultoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CultoService {

    private final CultoRepository cultoRepository;

    public Culto buscarCultoAtivo() {
        return cultoRepository.findByStatus(Culto.StatusCulto.ATIVO)
                .orElseThrow(() -> new RuntimeException("Nenhum culto ativo encontrado"));
    }
}