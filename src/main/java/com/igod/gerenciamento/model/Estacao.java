package com.igod.gerenciamento.model;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

    public class Estacao {
    public enum StatusEstacao{
        LIVRE,
        OCUPADA
    }
    @Id
    @SequenceGenerator(
            name = "estacao_sequence",
            sequenceName = "seq_estacao",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE ,
            generator = "estacao_sequence"
    )
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 100)
    private StatusEstacao status;

    private LocalDateTime ultimaLiberacao;



}
