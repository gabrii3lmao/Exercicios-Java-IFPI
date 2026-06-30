import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Entities.*;
import Entities.Interfaces.*;

public class App {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        seedData();
        int opcao;
        do {
            System.out.println("\n===== LOJA ONLINE =====");
            System.out.println("1. Entrar como Administrador");
            System.out.println("2. Entrar como Cliente");
            System.out.println("3. Sair");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> menuAdministrador();
                case 2 -> menuCliente();
                case 3 -> System.out.println("Saindo...");
                default -> System.out.println("Opcao invalida!");
            }
        } while (opcao != 3);
    }

    private static void seedData() {
        Administrador admin = new Administrador("Admin", "admin@loja.com", "123");
        admin.adicionarProduto(new ProdutoDigital("Curso Java", 49.90, "Curso completo de Java", "http://download.com/java", 500));
        admin.adicionarProduto(new ProdutoFisico("Teclado Mecanico", 199.90, "Teclado RGB mecanico", 0.8));
    }

    private static void menuAdministrador() {
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        Administrador admin = new Administrador("Admin", email, senha);

        int opcao;
        do {
            System.out.println("\n--- Menu Administrador ---");
            System.out.println("1. Adicionar Produto");
            System.out.println("2. Remover Produto");
            System.out.println("3. Listar Produtos");
            System.out.println("4. Visualizar Pedidos");
            System.out.println("5. Sair");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> adicionarProduto(admin);
                case 2 -> removerProduto(admin);
                case 3 -> listarProdutos();
                case 4 -> admin.visualizarPedidos();
                case 5 -> System.out.println("Saindo...");
                default -> System.out.println("Opcao invalida!");
            }
        } while (opcao != 5);
    }

    private static void adicionarProduto(Administrador admin) {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Preco: ");
        double preco = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Descricao: ");
        String descricao = scanner.nextLine();
        System.out.print("Tipo (1-Digital / 2-Fisico): ");
        int tipo = scanner.nextInt();
        scanner.nextLine();

        if (tipo == 1) {
            System.out.print("URL Download: ");
            String url = scanner.nextLine();
            System.out.print("Tamanho (MB): ");
            int tamanho = scanner.nextInt();
            scanner.nextLine();
            admin.adicionarProduto(new ProdutoDigital(nome, preco, descricao, url, tamanho));
        } else if (tipo == 2) {
            System.out.print("Peso (kg): ");
            double peso = scanner.nextDouble();
            scanner.nextLine();
            admin.adicionarProduto(new ProdutoFisico(nome, preco, descricao, peso));
        } else {
            System.out.println("Tipo invalido!");
        }
    }

    private static void removerProduto(Administrador admin) {
        List<Produto> catalogo = Administrador.getCatalogo();
        if (catalogo.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
            return;
        }
        for (int i = 0; i < catalogo.size(); i++) {
            Produto p = catalogo.get(i);
            System.out.println((i + 1) + ". " + p.getNome() + " - R$" + p.getPreco());
        }
        System.out.print("Numero do produto para remover: ");
        int idx = scanner.nextInt() - 1;
        scanner.nextLine();
        if (idx >= 0 && idx < catalogo.size()) {
            admin.removerProduto(catalogo.get(idx));
        } else {
            System.out.println("Indice invalido!");
        }
    }

    private static void listarProdutos() {
        List<Produto> catalogo = Administrador.getCatalogo();
        if (catalogo.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
            return;
        }
        System.out.println("--- Produtos Disponiveis ---");
        for (int i = 0; i < catalogo.size(); i++) {
            Produto p = catalogo.get(i);
            System.out.println((i + 1) + ". " + p.getNome() + " - R$" + String.format("%.2f", p.getPreco()) + " - " + p.getDescricao());
        }
    }

    private static void menuCliente() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();
        System.out.print("Endereco: ");
        String endereco = scanner.nextLine();

        Cliente cliente = new Cliente(nome, email, senha, endereco);

        int opcao;
        do {
            System.out.println("\n--- Menu Cliente ---");
            System.out.println("1. Listar Produtos");
            System.out.println("2. Fazer Pedido");
            System.out.println("3. Ver Historico de Pedidos");
            System.out.println("4. Sair");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> listarProdutos();
                case 2 -> fazerPedido(cliente);
                case 3 -> verHistorico(cliente);
                case 4 -> System.out.println("Saindo...");
                default -> System.out.println("Opcao invalida!");
            }
        } while (opcao != 4);
    }

    private static void fazerPedido(Cliente cliente) {
        List<Produto> catalogo = Administrador.getCatalogo();
        if (catalogo.isEmpty()) {
            System.out.println("Nenhum produto disponivel.");
            return;
        }

        List<ItemPedido> itens = new ArrayList<>();
        int numeroPedido = (int) (System.currentTimeMillis() % 10000);

        int opcao;
        do {
            System.out.println("\nProdutos disponiveis:");
            for (int i = 0; i < catalogo.size(); i++) {
                System.out.println((i + 1) + ". " + catalogo.get(i).getNome() + " - R$" + catalogo.get(i).getPreco());
            }
            System.out.println("0. Finalizar pedido");
            System.out.print("Escolha um produto: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            if (opcao > 0 && opcao <= catalogo.size()) {
                System.out.print("Quantidade: ");
                int qtd = scanner.nextInt();
                scanner.nextLine();
                itens.add(new ItemPedido(catalogo.get(opcao - 1), qtd));
                System.out.println("Item adicionado!");
            }
        } while (opcao != 0);

        if (itens.isEmpty()) {
            System.out.println("Pedido vazio. Cancelando.");
            return;
        }

        System.out.print("Codigo de barras do boleto: ");
        String codigoBarras = scanner.nextLine();
        Pagamento pagamento = new Boleto(codigoBarras);

        Pedido pedido = new Pedido(numeroPedido, itens, pagamento);
        pedido.processarPagamento();

        cliente.adicionarPedido(pedido);
        Administrador.registrarPedido(pedido);

        System.out.println("Pedido #" + pedido.getNumeroPedido() + " realizado com sucesso! Total: R$" + String.format("%.2f", pedido.calcularTotal()));
    }

    private static void verHistorico(Cliente cliente) {
        List<Pedido> historico = cliente.getHistoricoPedidos();
        if (historico.isEmpty()) {
            System.out.println("Nenhum pedido realizado.");
            return;
        }
        System.out.println("--- Historico de Pedidos ---");
        for (Pedido p : historico) {
            System.out.println("Pedido #" + p.getNumeroPedido() +
                    " | Data: " + p.getData() +
                    " | Status: " + p.getStatus() +
                    " | Total: R$" + String.format("%.2f", p.calcularTotal()));
        }
    }
}
