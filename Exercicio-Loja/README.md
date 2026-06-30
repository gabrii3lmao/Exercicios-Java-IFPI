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