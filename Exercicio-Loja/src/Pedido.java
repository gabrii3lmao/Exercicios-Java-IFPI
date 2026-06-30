import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Pedido{


    private int numeroPedido;
    private String status;
    private LocalDate data;
    private List<ItemPedido> itens; 
    private Pagamento pagamento;

    public Pedido(int numeroPedido, List<ItemPedido> itens, Pagamento pagamento){
         
        this.numeroPedido = numeroPedido;
        this.itens = itens;
        this.pagamento = pagamento;
        this.status = "Pedente";
        this.data = LocalDate.now();
    }
}

    public double  calculaValorTotal(){

        double total = 0;

        for(ItemPedido item : itens){
            total += item.getSubTotal();
        }

        return total;
    }

    public boolean processarPagamento() {

        return  pagamento.processarPagamento( calculaValorTotal());
    }
    
    public int getNumeroPedido() {
        return numeroPedido;
    }

    public String getStatus() {
        return status;
    }

    public LocalDate getData() {
        return data;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }



