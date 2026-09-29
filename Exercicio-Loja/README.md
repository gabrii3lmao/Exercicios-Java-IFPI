# Trabalho Loja Online

Implementar um programa no terminal que vai gerenciar uma loja online.

## Entidades a implementar

### [X] Usuário

```text
Atributos
- nome: string
- email: string
- senha: string

Métodos
- Usuario(nome: string, email: string, senha: string)
- getNome(): string
- getEmail(): string
- getSenha(): string
```

---

### [X] Administrador > Usuário

```text
Atributos
(nenhum)

Métodos
- Administrador(nome: string, email: string, senha: string)
- adicionarProduto(produto: Produto): void
- removerProduto(produto: Produto): void
- visualizarPedidos(): void
```

---

### [X] Cliente > Usuário

```text
Atributos
- endereco: string
- historicoPedidos: List<Pedido>

Métodos
- Cliente(nome: string, email: string, senha: string, endereco: string)
- getEndereco(): string
- adicionarPedido(pedido: Pedido): void
- getHistoricoPedidos(): List<Pedido>
```

---

### [X] Produto (abstrata)

```text
Atributos
- nome: string
- preco: double
- descricao: string

Métodos
- getNome(): string
- getPreco(): double
- getDescricao(): string
```

---

### [X] Produto Digital > Produto

```text
Atributos
- urlDownload: string
- tamanhoArquivoMB: int

Métodos
- ProdutoDigital(
    nome: string,
    preco: double,
    descricao: string,
    urlDownload: string,
    tamanhoArquivoMB: int
  )

- getNome(): string
- getPreco(): double
- getDescricao(): string
- getUrlDownload(): string
- getTamanhoArquivoMB(): int
```

---

### [X] Produto Físico > Produto

```text
Atributos
- peso: double

Métodos
- ProdutoFisico(
    nome: string,
    preco: double,
    descricao: string,
    peso: double
  )

- getNome(): string
- getPreco(): double
- getDescricao(): string
- getPeso(): double
```

---

### [X] ItemPedido

```text
Atributos
- quantidade: int

Métodos
- ItemPedido(produto: Produto, quantidade: int)
- getSubtotal(): double
- getQuantidade(): int
- getProduto(): Produto
```

---

### [X]Pedido

```text
Atributos
- numeroPedido: int
- status: string
- data: Date
- itens: List<ItemPedido>
- pagamento: Pagamento

Métodos
- Pedido(
    numeroPedido: int,
    itens: List<ItemPedido>,
    pagamento: Pagamento
  )

- calcularTotal(): double
- processarPagamento(): void
- getNumeroPedido(): int
- getData(): Date
- getStatus(): string
- getItens(): List<ItemPedido>
- getPagamento(): Pagamento
```

---

### [X] Pagamento (interface)

```text
Métodos
- processarPagamento(valor: double): boolean
```

---

### [X] Boleto > Pagamento

```text
Atributos
- codigoBarras: string

Métodos
- Boleto(codigoBarras: string)
- processarPagamento(valor: double): boolean
```

---

## Relacionamentos

- `Administrador` herda de `Usuário`.
- `Cliente` herda de `Usuário`.
- `ProdutoDigital` herda de `Produto`.
- `ProdutoFisico` herda de `Produto`.
- Um `Cliente` possui um histórico de `Pedido`.
- Um `Pedido` é composto por vários `ItemPedido`.
- Cada `ItemPedido` referencia exatamente um `Produto`.
- Um `Pedido` possui uma estratégia de `Pagamento`.
- `Boleto` implementa a interface `Pagamento`.
- O `Administrador` é responsável por cadastrar e remover produtos e visualizar os pedidos do sistema.

---

## Persistência — JPA + Hibernate + MySQL

O programa agora guarda tudo em um banco **MySQL** (roda em container Docker/Podman),
usando **JPA (Jakarta Persistence)** com o provedor **Hibernate**.

- **Nenhum SQL puro no projeto**: o schema é criado e atualizado pelo Hibernate a
  partir das anotações JPA (`hibernate.hbm2ddl.auto=update`) e todas as consultas
  são **JPQL**, feitas pelos DAOs em `src/main/java/dao/`.
- Documentação completa do mapeamento (diagrama ER, tabelas, colunas e
  cardinalidades): [`docs/mapeamento-banco.md`](docs/mapeamento-banco.md).
- Única mudança de modelagem: `Pagamento`, que era `interface`, virou **classe
  abstrata `@Entity`** para poder ser persistida (o contrato
  `processarPagamento(valor)` continua igual).

### Estrutura

```text
src/main/java/
├── App.java                  # menu do terminal (usa os DAOs)
├── Entities/                 # entidades JPA (mapeadas)
├── dao/                      # acesso a dados (JPQL, sem SQL)
└── util/JPAUtil.java         # EntityManagerFactory
src/main/resources/
├── META-INF/persistence.xml  # conexao + propriedades do Hibernate
docs/mapeamento-banco.md      # mapeamento banco <-> classes
```

### Como executar

```bash
# 1) banco MySQL (imagem docker.io/library/mysql:8.4)
docker compose up -d          # ou: podman-compose up -d

# 2) compilar (o Hibernate cria as tabelas na 1a execução)
mvn compile

# 3) rodar
mvn exec:java
```

> O Maven pode ser instalado com `sudo dnf install maven`.

**Login inicial do administrador:** `admin@loja.com` / `123`
(o seed roda só se o banco estiver vazio). Clientes se cadastram pelo próprio menu.