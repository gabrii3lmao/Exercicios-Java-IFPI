package Entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

/**
 * Cliente da loja.
 *
 * Mapeamento: heranca JOINED -> herda a chave primaria de "usuarios"
 * e guarda o endereco na tabela "clientes".
 *
 * O historico de pedidos e a metade "one" da relacao Cliente 1 - N Pedido
 * (o lado "many" com a chave estrangeira fica em Pedido.cliente).
 */
@Entity
@Table(name = "clientes")
@PrimaryKeyJoinColumn(name = "id")
public class Cliente extends Usuario {

    @Column(name = "endereco", length = 200)
    private String endereco;

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

    public Cliente(String nome, String email, String senha, String endereco) {
        super(nome, email, senha);
        this.endereco = endereco;
        this.historicoPedidos = new ArrayList<>();
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
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
