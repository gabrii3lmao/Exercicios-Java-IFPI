package Entities;

import java.util.List;

import Entities.Interfaces.Produto;
import dao.PedidoDao;
import dao.ProdutoDao;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

/**
 * Administrador da loja.
 *
 * Mapeamento: heranca JOINED -> herda a chave primaria de "usuarios"
 * e guarda seus dados na tabela "administradores".
 *
 * Observacao: as listas estaticas de catalogo e de pedidos foram removidas;
 * agora a persistencia e feita pelos DAOs (JPA/Hibernate).
 */
@Entity
@Table(name = "administradores")
@PrimaryKeyJoinColumn(name = "id")
public class Administrador extends Usuario {

    public Administrador() {
        super();
    }

    public Administrador(String nome, String email, String senha) {
        super(nome, email, senha);
    }

    public void adicionarProduto(Produto produto) {
        new ProdutoDao().salvar(produto);
        System.out.println("Produto \"" + produto.getNome() + "\" adicionado com sucesso!");
    }

    public void removerProduto(Produto produto) {
        boolean removido = new ProdutoDao().remover(produto);
        if (removido) {
            System.out.println("Produto \"" + produto.getNome() + "\" removido!");
        } else {
            System.out.println("Produto não encontrado ou em uso por algum pedido.");
        }
    }

    public void visualizarPedidos() {
        List<Pedido> pedidos = new PedidoDao().listarTodos();
        if (pedidos.isEmpty()) {
            System.out.println("Nenhum pedido realizado no sistema.");
            return;
        }
        System.out.println("--- Pedidos do Sistema ---");
        for (Pedido p : pedidos) {
            System.out.println("Pedido #" + p.getNumeroPedido() +
                    " | Cliente: " + (p.getCliente() != null ? p.getCliente().getNome() : "N/D") +
                    " | Status: " + p.getStatus().getDescricao() +
                    " | Total: R$" + String.format("%.2f", p.calcularTotal()));
        }
    }
}
