package Entities;

import Entities.Interfaces.Produto;

public class ProdutoFisico extends Produto{
    private double peso;

    public ProdutoFisico(String nome, double preco, String descricao, double peso) {
        super(nome, preco, descricao);
        this.peso = peso;
    }

    public double getPeso() {
        return peso;
    }
}
