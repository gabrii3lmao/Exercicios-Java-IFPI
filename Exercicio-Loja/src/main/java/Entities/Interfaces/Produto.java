package Entities.Interfaces;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

/**
 * Produto abstrato da loja.
 *
 * Mapeamento: heranca SINGLE_TABLE -> ProdutoDigital e ProdutoFisico
 * compartilham a tabela "produtos"; a coluna discriminadora "tipo"
 * ("DIGITAL" ou "FISICO") identifica a subclasse de cada linha.
 * As colunas especificas de cada subclasse ficam NULL nas demais linhas.
 */
@Entity
@Table(name = "produtos")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
    name = "tipo",
    discriminatorType = DiscriminatorType.STRING,
    length = 20
)
public abstract class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false, length = 150)
    protected String nome;

    @Column(name = "preco", nullable = false)
    protected double preco;

    @Column(name = "descricao", length = 500)
    protected String descricao;

    /** Construtor sem argumentos exigido pelo JPA. */
    protected Produto() {
    }

    public Produto(String nome, double preco, String descricao) {
        this.nome = nome;
        this.preco = preco;
        this.descricao = descricao;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
