import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Entities.*;
import Entities.Interfaces.*;

import dao.PedidoDao;
import dao.ProdutoDao;
import dao.UsuarioDao;
import util.JPAUtil;

public class App {

    private static Scanner scanner = new Scanner(System.in);

    private static final UsuarioDao usuarioDao = new UsuarioDao();
    private static final ProdutoDao produtoDao = new ProdutoDao();
    private static final PedidoDao pedidoDao = new PedidoDao();

    public static void main(String[] args) {
        seedData();
        try {
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
        } finally {
            JPAUtil.close();
        }
    }

    /** Carga inicial no banco, executada apenas se estiver vazia. */
    private static void seedData() {
        if (!usuarioDao.existeAdministrador()) {
            usuarioDao.salvar(new Administrador("Admin", "admin@loja.com", "123"));
            System.out.println("Administrador padrao cadastrado (admin@loja.com / 123).");
        }
        if (produtoDao.contar() == 0) {
            produtoDao.salvar(new ProdutoDigital("Curso Java", 49.90,
                    "Curso completo de Java", "http://download.com/java", 500));
            produtoDao.salvar(new ProdutoFisico("Teclado Mecanico", 199.90,
                    "Teclado RGB mecanico", 0.8));
            System.out.println("Catalogo inicial cadastrado.");
        }
    }

    private static void menuAdministrador() {
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        Usuario usuario = usuarioDao.autenticar(email, senha);
        if (!(usuario instanceof Administrador admin)) {
            System.out.println(usuario == null
                    ? "Email ou senha invalidos."
                    : "Esse usuario nao e administrador.");
            return;
        }

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
        List<Produto> catalogo = produtoDao.listarTodos();
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
        List<Produto> catalogo = produtoDao.listarTodos();
        if (catalogo.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
            return;
        }
        System.out.println("--- Produtos Disponiveis ---");
        for (int i = 0; i < catalogo.size(); i++) {
            Produto p = catalogo.get(i);
            System.out.println((i + 1) + ". " + p.getNome() + " - R$"
                    + String.format("%.2f", p.getPreco()) + " - " + p.getDescricao());
        }
    }

    private static void menuCliente() {
        System.out.print("Email: ");
        String email = scanner.nextLine();

        Usuario usuario = usuarioDao.buscarPorEmail(email);
        Cliente cliente;

        if (usuario instanceof Cliente existente) {
            System.out.print("Senha: ");
            String senha = scanner.nextLine();
            if (!existente.getSenha().equals(senha)) {
                System.out.println("Senha invalida.");
                return;
            }
            cliente = existente;
            System.out.println("Bem-vindo(a), " + cliente.getNome() + "!");
            if (cliente.getEndereco() != null) {
                System.out.println("Endereco cadastrado: " + cliente.getEnderecoDescricao());
            }
        } else if (usuario != null) {
            System.out.println("Esse email pertence a um administrador.");
            return;
        } else {
            System.out.println("Cliente nao encontrado. Faca o cadastro:");
            System.out.print("Nome: ");
            String nome = scanner.nextLine();
            System.out.print("Senha: ");
            String senha = scanner.nextLine();
            cliente = new Cliente(nome, email, senha, lerEndereco());
            usuarioDao.salvar(cliente);
            System.out.println("Cadastro realizado com sucesso!");
        }

        final Cliente clienteLogado = cliente;
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
                case 2 -> fazerPedido(clienteLogado);
                case 3 -> verHistorico(clienteLogado);
                case 4 -> System.out.println("Saindo...");
                default -> System.out.println("Opcao invalida!");
            }
        } while (opcao != 4);
    }

    /** Le os campos do endereco e monta o objeto Endereco (relacao 1 - 1). */
    private static Endereco lerEndereco() {
        System.out.print("Logradouro (rua/avenida): ");
        String logradouro = scanner.nextLine();
        System.out.print("Numero: ");
        String numero = scanner.nextLine();
        System.out.print("Complemento (vazio se nao houver): ");
        String complemento = scanner.nextLine();
        System.out.print("Bairro: ");
        String bairro = scanner.nextLine();
        System.out.print("Cidade: ");
        String cidade = scanner.nextLine();
        System.out.print("UF: ");
        String uf = scanner.nextLine();
        System.out.print("CEP: ");
        String cep = scanner.nextLine();

        Endereco endereco = new Endereco(logradouro, numero, bairro, cidade, uf, cep);
        endereco.setComplemento(complemento);
        return endereco;
    }

    private static void fazerPedido(Cliente cliente) {
        List<Produto> catalogo = produtoDao.listarTodos();
        if (catalogo.isEmpty()) {
            System.out.println("Nenhum produto disponivel.");
            return;
        }

        List<ItemPedido> itens = new ArrayList<>();

        int opcao;
        do {
            System.out.println("\nProdutos disponiveis:");
            for (int i = 0; i < catalogo.size(); i++) {
                System.out.println((i + 1) + ". " + catalogo.get(i).getNome()
                        + " - R$" + catalogo.get(i).getPreco());
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

        // numero 0 -> o proximo numero do pedido e atribuido pelo banco (JPQL)
        Pedido pedido = new Pedido(0, itens, pagamento);
        pedido.processarPagamento();
        cliente.adicionarPedido(pedido);
        pedidoDao.salvar(pedido);

        System.out.println("Pedido #" + pedido.getNumeroPedido()
                + " realizado com sucesso! Total: R$"
                + String.format("%.2f", pedido.calcularTotal()));
    }

    private static void verHistorico(Cliente cliente) {
        List<Pedido> historico = pedidoDao.listarPorCliente(cliente.getId());
        if (historico.isEmpty()) {
            System.out.println("Nenhum pedido realizado.");
            return;
        }
        System.out.println("--- Historico de Pedidos ---");
        for (Pedido p : historico) {
            System.out.println("Pedido #" + p.getNumeroPedido() +
                    " | Data: " + p.getData() +
                    " | Status: " + p.getStatus().getDescricao() +
                    " | Total: R$" + String.format("%.2f", p.calcularTotal()));
        }
    }
}
