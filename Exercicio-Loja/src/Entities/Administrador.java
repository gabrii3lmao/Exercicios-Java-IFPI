package Entities;

import Entities.Interfaces.Produto;
import java.util.ArrayList;
import java.util.List;

public class Administrador extends Usuario {

    private static List<Produto> catalogo = new ArrayList<>();
    private static List<Pedido> todosPedidos = new ArrayList<>();

    public Administrador(String nome, String email, String senha) {
        super(nome, email, senha);
    }

    public void adicionarProduto(Produto produto) {
        catalogo.add(produto);
        System.out.println("Produto \"" + produto.getNome() + "\" adicionado com sucesso!");
    }

    public void removerProduto(Produto produto) {
        if (catalogo.remove(produto)) {
            System.out.println("Produto \"" + produto.getNome() + "\" removido!");
        } else {
            System.out.println("Produto não encontrado.");
        }
    }

    public void visualizarPedidos() {
        if (todosPedidos.isEmpty()) {
            System.out.println("Nenhum pedido realizado no sistema.");
            return;
        }
        System.out.println("--- Pedidos do Sistema ---");
        for (Pedido p : todosPedidos) {
            System.out.println("Pedido #" + p.getNumeroPedido() +
                    " | Cliente: " + p.getNumeroPedido() +
                    " | Status: " + p.getStatus() +
                    " | Total: R$" + String.format("%.2f", p.calcularTotal()));
        }
    }

    public static List<Produto> getCatalogo() {
        return catalogo;
    }

    public static void registrarPedido(Pedido pedido) {
        todosPedidos.add(pedido);
    }
}
