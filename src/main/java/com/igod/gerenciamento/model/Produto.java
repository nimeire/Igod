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

    @Id
    @SequenceGenerator(
            name = "produto_sequence",
            sequenceName = "seq_produto",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "produto_sequence"
    )
    private Long id;

    @Column(nullable = false, length = 100)
    private String  nome;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 100)
    private categoriaProduto categoria;

    @Column(nullable = false)
    private Boolean disponivel = true;

}
