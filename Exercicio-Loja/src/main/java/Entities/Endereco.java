package Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * Endereco de entrega/cobranca de um Cliente.
 *
 * Mapeamento: tabela "enderecos" ligada a "clientes" por uma chave estrangeira
 * unica (cliente_id). Essa coluna e o lado "dono" da relacao Cliente 1 - 1
 * Endereco (o lado inverso, em Cliente.endereco, usa mappedBy).
 *
 * Consequencia da cardinalidade 1 - 1: no maximo um endereco por cliente e
 * um cliente por endereco (constraint UNIQUE em cliente_id).
 */
@Entity
@Table(name = "enderecos")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "logradouro", nullable = false, length = 150)
    private String logradouro;

    @Column(name = "numero", length = 20)
    private String numero;

    @Column(name = "complemento", length = 100)
    private String complemento;

    @Column(name = "bairro", length = 80)
    private String bairro;

    @Column(name = "cidade", nullable = false, length = 80)
    private String cidade;

    @Column(name = "uf", length = 2)
    private String uf;

    @Column(name = "cep", length = 10)
    private String cep;

    /** Lado dono da relacao 1 - 1: a FK unica fica na tabela "enderecos". */
    @OneToOne
    @JoinColumn(name = "cliente_id", nullable = false, unique = true)
    private Cliente cliente;

    /** Construtor sem argumentos exigido pelo JPA. */
    public Endereco() {
    }

    public Endereco(String logradouro, String numero, String bairro,
                    String cidade, String uf, String cep) {
        this.logradouro = logradouro;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.uf = uf;
        this.cep = cep;
    }

    public Long getId() {
        return id;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /** Endereco em uma unica linha (rua, numero - bairro, cidade/uf - CEP). */
    public String getDescricao() {
        StringBuilder sb = new StringBuilder(logradouro == null ? "" : logradouro);
        if (numero != null && !numero.isBlank()) {
            sb.append(", ").append(numero);
        }
        if (complemento != null && !complemento.isBlank()) {
            sb.append(" (").append(complemento).append(")");
        }
        if (bairro != null && !bairro.isBlank()) {
            sb.append(" - ").append(bairro);
        }
        if (cidade != null && !cidade.isBlank()) {
            sb.append(" - ").append(cidade);
        }
        if (uf != null && !uf.isBlank()) {
            sb.append("/").append(uf);
        }
        if (cep != null && !cep.isBlank()) {
            sb.append(" - CEP ").append(cep);
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return getDescricao();
    }
}
