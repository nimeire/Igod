package com.igod.gerenciamento.service;

import com.igod.gerenciamento.model.Produto;
import com.igod.gerenciamento.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
    public class ProdutoService {
    private final ProdutoRepository produtoRepository;

    public List<Produto> listarProdutos(){
        return produtoRepository.findAll();
    }

    public Produto salvar(Produto produto) {

        System.out.println("========== CHEGUEI NO SERVICE ==========");
        System.out.println("Disponível antes: " + produto.getDisponivel());

        if (produto.getDisponivel() == null) {
            produto.setDisponivel(true);
        }

        System.out.println("Disponível depois: " + produto.getDisponivel());

        return produtoRepository.save(produto);
    }

    public Optional<Produto> buscarPorID(Long id) {
        return produtoRepository.findById(id);
    }

    public Produto inativarProduto(Long id){
       Produto produto = buscarPorID(id).get();
       produto.setDisponivel(false);
       return salvar(produto);
    }


}
