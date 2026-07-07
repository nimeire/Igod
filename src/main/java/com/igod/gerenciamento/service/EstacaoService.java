package com.igod.gerenciamento.service;
import com.igod.gerenciamento.model.Estacao;
import com.igod.gerenciamento.repository.EstacaoRepository;
import lombok.RequiredArgsConstructor;
import java.util.Optional;
import org.springframework.stereotype.Service;


import java.util.Comparator;
import java.util.List;
@RequiredArgsConstructor
@Service
public class EstacaoService {
    private final EstacaoRepository estacaoRepository;

    public List<Estacao> buscarEstacoesLivres() {
        return estacaoRepository.findByStatus(Estacao.StatusEstacao.LIVRE);
    }

    public Optional<Estacao> buscarMelhorEstacaoLivre(){
        List<Estacao> estacoesLivres = buscarEstacoesLivres();

        return estacoesLivres.stream()
                .min(Comparator.comparing(Estacao::getUltimaLiberacao));
    }

    public Estacao salvar(Estacao estacao) {
        return estacaoRepository.save(estacao);
    }
}
