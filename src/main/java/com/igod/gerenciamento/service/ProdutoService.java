package com.igod.gerenciamento.service;

import com.igod.gerenciamento.model.Produto;
import com.igod.gerenciamento.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@RequiredArgsConstructor
@Service
    public class ProdutoService {
    private final ProdutoRepository produtoRepository;

    public List<Produto> listarProdutos(){
        return produtoRepository.findAll();
    }

    public Produto salvar(Produto produto){
        return  produtoRepository.save(produto);
    }
}
