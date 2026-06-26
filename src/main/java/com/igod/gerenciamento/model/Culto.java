package com.igod.gerenciamento.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

    public class Culto {
    public enum StatusCulto{
        ATIVO,
        FINALIZADO
    }

    @Id
    @SequenceGenerator(
            name = "culto_sequence",
            sequenceName = "seq_culto",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE ,
            generator = "culto_sequence"
    )
    private Long id;

    @OneToMany(mappedBy = "culto")
    private List<Pedido> pedidos;

    @Column(nullable = false, length = 100)
    private String nome;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy  HH:mm:ss", timezone = "America/Sao_Paulo")
    private LocalDateTime dataCulto = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 100)
    private StatusCulto status = StatusCulto.ATIVO;
}
