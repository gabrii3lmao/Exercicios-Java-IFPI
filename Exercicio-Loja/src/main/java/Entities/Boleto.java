package Entities;

import Entities.Interfaces.Pagamento;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * Pagamento via boleto (heranca SINGLE_TABLE na tabela "pagamentos").
 * Discriminador: tipo_pagamento = "BOLETO".
 */
@Entity
@DiscriminatorValue("BOLETO")
public class Boleto extends Pagamento {

    @Column(name = "codigo_barras", length = 100)
    private String codigoBarras;

    public Boleto() {
        super();
    }

    public Boleto(String codigoBarras) {
        super();
        this.codigoBarras = codigoBarras;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    @Override
    public boolean processarPagamento(double valor) {
        System.out.println("Processando pagamento via boleto no valor de: " + valor);
        return true;
    }
}
