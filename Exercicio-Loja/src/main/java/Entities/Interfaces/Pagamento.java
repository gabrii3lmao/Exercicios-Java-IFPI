package Entities.Interfaces;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

/**
 * Estrategia de pagamento.
 *
 * Era uma interface; para ser persistida virou uma entidade abstrata com
 * heranca SINGLE_TABLE: todas as estrategias ficam na tabela "pagamentos"
 * e a coluna discriminadora "tipo_pagamento" identifica cada implementacao
 * (por exemplo: "BOLETO").
 *
 * O contrato continua o mesmo: processarPagamento(valor).
 */
@Entity
@Table(name = "pagamentos")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
    name = "tipo_pagamento",
    discriminatorType = DiscriminatorType.STRING,
    length = 20
)
public abstract class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Construtor sem argumentos exigido pelo JPA. */
    protected Pagamento() {
    }

    public Long getId() {
        return id;
    }

    public abstract boolean processarPagamento(double valor);
}
