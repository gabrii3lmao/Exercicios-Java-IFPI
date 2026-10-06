package Entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

/**
 * Cliente da loja.
 *
 * Mapeamento: heranca JOINED -> herda a chave primaria de "usuarios"
 * e guarda apenas os dados proprios na tabela "clientes".
 *
 * Relacoes:
 *  - Cliente 1 - 1 Endereco: o lado inverso, aqui, com mappedBy (a chave
 *    estrangeira unica "cliente_id" fica em Endereco.cliente). O cascade faz
 *    o endereco ser persistido/atualizado junto do cliente.
 *  - Cliente 1 - N Pedido: o historico e a metade "one" da relacao
 *    (o lado "many" com a chave estrangeira fica em Pedido.cliente).
 */
@Entity
@Table(name = "clientes")
@PrimaryKeyJoinColumn(name = "id")
public class Cliente extends Usuario {

    /**
     * Lado inverso da relacao 1 - 1 (o dono e Endereco.cliente).
     * EAGER + cascade para que o endereco continue utilizavel apos o
     * EntityManager ser fechado e seja salvo junto do cliente.
     */
    @OneToOne(
        mappedBy = "cliente",
        fetch = FetchType.EAGER,
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private Endereco endereco;

    /**
     * EAGER para que a lista carregada continue utilizavel apos o
     * EntityManager ser fechado (evita LazyInitializationException no terminal).
     */
    @OneToMany(mappedBy = "cliente", fetch = FetchType.EAGER)
    private List<Pedido> historicoPedidos;

    public Cliente() {
        super();
        this.historicoPedidos = new ArrayList<>();
    }

    public Cliente(String nome, String email, String senha, Endereco endereco) {
        super(nome, email, senha);
        this.historicoPedidos = new ArrayList<>();
        setEndereco(endereco);
    }

    public Endereco getEndereco() {
        return endereco;
    }

    /** Mantem as duas pontas da relacao 1 - 1 consistentes. */
    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
        if (endereco != null) {
            endereco.setCliente(this);
        }
    }

    /** Endereco em uma unica linha; null se o cliente ainda nao tiver um. */
    public String getEnderecoDescricao() {
        return endereco == null ? null : endereco.getDescricao();
    }

    public void adicionarPedido(Pedido pedido) {
        if (historicoPedidos == null) {
            historicoPedidos = new ArrayList<>();
        }
        historicoPedidos.add(pedido);
        pedido.setCliente(this);
    }

    public List<Pedido> getHistoricoPedidos() {
        if (historicoPedidos == null) {
            historicoPedidos = new ArrayList<>();
        }
        return historicoPedidos;
    }
}
