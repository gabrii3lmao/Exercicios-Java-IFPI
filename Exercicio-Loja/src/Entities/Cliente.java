package Entities;

import java.util.List;
import java.util.ArrayList;

public class Cliente extends Usuario {
    private String endereco;
    private List<Pedido> historicoPedidos;

    public Cliente(String nome, String email, String senha, String endereco) {
        super(nome, email, senha);
        this.endereco = endereco;
        this.historicoPedidos = new ArrayList<>();
    }

    public String getEndereco() { return endereco; }

    public void adicionarPedido(Pedido pedido) {
        historicoPedidos.add(pedido);
    }

    public List<Pedido> getHistoricoPedidos() {
        return historicoPedidos;
    }
}
