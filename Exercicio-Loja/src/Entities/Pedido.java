package Entities;

import Entities.Interfaces.Pagamento;
import java.time.LocalDate;
import java.util.List;

public class Pedido {

    private int numeroPedido;
    private String status;
    private LocalDate data;
    private List<ItemPedido> itens;
    private Pagamento pagamento;

    public Pedido(int numeroPedido, List<ItemPedido> itens, Pagamento pagamento) {
        this.numeroPedido = numeroPedido;
        this.itens = itens;
        this.pagamento = pagamento;
        this.status = "Pendente";
        this.data = LocalDate.now();
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
            this.status = "Pago";
        }
    }

    public int getNumeroPedido() { return numeroPedido; }
    public String getStatus() { return status; }
    public LocalDate getData() { return data; }
    public List<ItemPedido> getItens() { return itens; }
    public Pagamento getPagamento() { return pagamento; }
}
