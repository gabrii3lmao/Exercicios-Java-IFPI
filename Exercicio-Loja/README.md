# Loja Online — Quickstart

Programa de terminal que gerencia uma loja online (clientes, produtos, pedidos e
pagamentos), com persistência em **MySQL** via **JPA/Hibernate** e **Maven**.

Este guia mostra, do zero, como rodar o projeto na sua máquina.

---

## 1. Pré-requisitos

| Ferramenta | Versão mínima | Como verificar |
| --- | --- | --- |
| JDK | 21 | `java -version` |
| Maven | 3.8+ | `mvn -v` |
| Docker (ou Podman) | qualquer | `docker -v` |

---

## 2. Subir o banco de dados

Na pasta do projeto:

```bash
docker compose up -d          # Podman: podman-compose up -d
```

Confira se o container ficou saudável:

```bash
docker compose ps             # STATUS deve ser "healthy"
docker compose logs -f mysql  # opcional: acompanhar o boot do MySQL
```

O `docker-compose.yml` já cria o banco `loja_online`, expõe a porta `3306` e
mantém os dados no volume `mysql-data`.

---

## 3. Compilar

```bash
mvn compile
```

Na **primeira execução** o Hibernate cria todas as tabelas automaticamente a
partir das anotações JPA (`hibernate.hbm2ddl.auto=update`) — nenhum script SQL
precisa ser rodado à mão.

---

## 4. Executar

```bash
mvn exec:java
```


### Login inicial

| Perfil | Email | Senha |
| --- | --- | --- |
| Administrador | `admin@loja.com` | `123` |

O seed (administrador + catálogo inicial: *Curso Java* e *Teclado Mecânico*)
roda **apenas se o banco estiver vazio**.

Clientes não têm cadastro prévio: escolha a opção `2`, informe um email novo e
o menu pede nome, senha e endereço para cadastrar.

---

## 5. Comandos úteis

```bash
mvn compile                 # compilar
mvn exec:java               # rodar a aplicação
mvn package                 # gerar o jar em target/
mvn clean                   # limpar a compilação

docker compose up -d        # subir o MySQL
docker compose down          # parar o MySQL (mantém os dados)
docker compose down -v       # parar e APAGAR os dados do banco
```

---
