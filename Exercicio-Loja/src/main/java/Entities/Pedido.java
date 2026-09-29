package Entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Entities.Interfaces.Pagamento;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

/**
 * Pedido da loja.
 *
 * Mapeamento: tabela "pedidos"
 *  - numero_pedido: numero de negocio, unico (atribuido pelo banco)
 *  - status: enumeracao gravada como texto
 *  - data_pedido: data do pedido
 *  - cliente_id: chave estrangeira para clientes.id (lado "many" de Cliente)
 *  - pagamento_id: chave estrangeira para pagamentos.id
 *  - itens: colecao dona da relacao com "itens_pedido" (cascade remove em cascata)
 */
@Entity
@Table(
    name = "pedidos",
    uniqueConstraints = @UniqueConstraint(name = "uk_pedido_numero", columnNames = "numero_pedido")
)
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_pedido", nullable = false, unique = true)
    private int numeroPedido;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private StatusPedido status;

    @Column(name = "data_pedido", nullable = false)
    private LocalDate data;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPedido> itens;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "pagamento_id", nullable = false)
    private Pagamento pagamento;

    /** Construtor sem argumentos exigido pelo JPA. */
    public Pedido() {
        this.itens = new ArrayList<>();
        this.status = StatusPedido.PENDENTE;
        this.data = LocalDate.now();
    }

    /**
     * @param numeroPedido numero do pedido; use 0 para que o proprio banco
     *                     atribua o proximo numero disponivel (feito pelo
     *                     {@link dao.PedidoDao} antes de persistir).
     */
    public Pedido(int numeroPedido, List<ItemPedido> itens, Pagamento pagamento) {
        this.numeroPedido = numeroPedido;
        this.itens = new ArrayList<>(itens);
        this.pagamento = pagamento;
        this.status = StatusPedido.PENDENTE;
        this.data = LocalDate.now();
        // mantem a outra ponta da relacao consistente antes do persist
        for (ItemPedido item : this.itens) {
            item.setPedido(this);
        }
    }

    public Long getId() {
        return id;
    }

    public double calcularTotal() {
        double total = 0;
        for (ItemPedido item : itens) {
            total += item.getSubtotal();
        }
        return total;
    }

    public void processarPagamento() {
        boolean pago = pagamento.processarPagamento(calcularTotal());
        if (pago) {
            this.status = StatusPedido.PAGO;
        }
    }

    public int getNumeroPedido() {
        return numeroPedido;
    }

    public void setNumeroPedido(int numeroPedido) {
        this.numeroPedido = numeroPedido;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public List<ItemPedido> getItens() {
        if (itens == null) {
            itens = new ArrayList<>();
        }
        return itens;
    }

    public void setItens(List<ItemPedido> itens) {
        this.itens = itens;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }
}
