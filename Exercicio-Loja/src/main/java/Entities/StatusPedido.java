package Entities;

/**
 * Situacao atual de um pedido.
 *
 * Mapeado com @Enumerated(EnumType.STRING): o banco guarda o nome da
 * constante ("PENDENTE", "PAGO", "CANCELADO") em vez de um codigo numerico.
 */
public enum StatusPedido {

    PENDENTE("Pendente"),
    PAGO("Pago"),
    CANCELADO("Cancelado");

    private final String descricao;

    StatusPedido(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
