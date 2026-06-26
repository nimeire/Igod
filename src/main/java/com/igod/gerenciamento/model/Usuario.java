package com.igod.gerenciamento.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

    public class Usuario {
    public enum PerfilUsuario{
        ADMIN,
        CAIXA,
        CANTINA
    }

    @Id
    @SequenceGenerator(
            name = "usuario_sequence",
            sequenceName = "seq_usuario",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE ,
            generator = "usuario_sequence"
    )
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 100)
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 100)
    private  PerfilUsuario perfilUsuario;


}
