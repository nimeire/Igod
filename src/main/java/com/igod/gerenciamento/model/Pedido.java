package com.igod.gerenciamento.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pedidos")
    public class Pedido {
    public enum StatusPedido{
        AGUARDANDO,
        EM_PREPARO,
        PRONTO,
        ENTREGUE
    }
    public enum TipoPedido{
        RETIRADA,
        ENCOMENDA
    }
    public enum FormaPagamento{
        DEBITO,
        CREDITO,
        PIX,
        DINHEIRO

    }
    @JsonIgnore
    @OneToMany(mappedBy = "pedido")
    private List<ItemPedido> itens;

    @ManyToOne
    @JoinColumn(name = "estacao_id")
    private Estacao estacao;

    @ManyToOne
    @JoinColumn(name = "culto_id")
    private Culto culto;

    @Id
    @SequenceGenerator(
            name = "pedido_sequence",
            sequenceName = "seq_pedido",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE ,
            generator = "pedido_sequence"
    )
    private Long id;

    @Column(nullable = false, length = 100)
    private String nomeResponsavel;

    @Column
    private Integer senha;

    @Column(length = 500)
    private String observacoes;

    @Enumerated(EnumType.STRING)
    private StatusPedido  status = StatusPedido.AGUARDANDO;

    @Enumerated(EnumType.STRING)
    private TipoPedido tipo;

    @Enumerated(EnumType.STRING)
    private FormaPagamento pagamento;

    @Column( precision = 10, scale = 2)
    private BigDecimal total;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy  HH:mm:ss", timezone = "America/Sao_Paulo")
    private LocalDateTime dataPedido = LocalDateTime.now();



}

