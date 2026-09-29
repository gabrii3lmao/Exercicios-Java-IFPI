package Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

/**
 * Usuario base da loja.
 *
 * Mapeamento: heranca JOINED -> a tabela "usuarios" guarda os dados comuns
 * (nome, email, senha) e cada subclasse possui a propria tabela
 * ("administradores" e "clientes") ligada por chave primaria compartilhada.
 */
@Entity
@Table(
    name = "usuarios",
    uniqueConstraints = @UniqueConstraint(name = "uk_usuario_email", columnNames = "email")
)
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false, length = 120)
    private String nome;

    @Column(name = "email", nullable = false, length = 120)
    private String email;

    @Column(name = "senha", nullable = false, length = 60)
    private String senha;

    /** Construtor sem argumentos exigido pelo JPA. */
    protected Usuario() {
    }

    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
