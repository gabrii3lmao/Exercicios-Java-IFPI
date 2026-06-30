package Entities;

import Entities.Interfaces.Produto;

public class ItemPedido {

    private int quantidade;
    private Produto produto;

    public ItemPedido(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;

    }

    public double getSubtotal() {
        return produto.getPreco() * quantidade;

    }

    public int getQuantidade() {
        return quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

}
