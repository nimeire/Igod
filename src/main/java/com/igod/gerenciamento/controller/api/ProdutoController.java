package com.igod.gerenciamento.controller.api;

import com.igod.gerenciamento.model.Produto;
import com.igod.gerenciamento.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor

public class ProdutoController {
    private final ProdutoService produtoService;

    @GetMapping
    public List<Produto> listarProdutos(){

        return produtoService.listarProdutos();
    }

    @PostMapping
    public Produto salvar(@RequestBody Produto produto){

        return produtoService.salvar(produto);
    }
    @PatchMapping("/{id}/inativar")
    public Produto inativarProduto(@PathVariable Long id){

        return produtoService.inativarProduto(id);
    }
    @PutMapping("/{id}")
    public Produto atualizar (@PathVariable Long id, @RequestBody Produto produto){
        return produtoService.atualizar(id, produto);
    }
}
