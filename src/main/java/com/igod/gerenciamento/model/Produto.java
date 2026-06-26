package com.igod.gerenciamento.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "produtos")

    public class Produto {
    public enum categoriaProduto{
        SALGADO,
        DOCE,
        BEBIDA,
        OUTROS
    }



    @Column(nullable = false, length = 100)
    private String  nome;

    @Column(precision = 10, scale = 2)
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 100)
    private categoriaProduto categoria;

    @Column(nullable = false)
    private Boolean disponivel = true;

}
