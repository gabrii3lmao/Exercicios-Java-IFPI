package Entities;

import Entities.Interfaces.Produto;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * Produto fisico (heranca SINGLE_TABLE na tabela "produtos").
 * Discriminador: tipo = "FISICO".
 */
@Entity
@DiscriminatorValue("FISICO")
public class ProdutoFisico extends Produto {

    @Column(name = "peso")
    private double peso;

    public ProdutoFisico() {
        super();
    }

    public ProdutoFisico(String nome, double preco, String descricao, double peso) {
        super(nome, preco, descricao);
        this.peso = peso;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }
}
