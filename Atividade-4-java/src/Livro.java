public class Livro {
    private String nome;
    private Autor autor;

    public String getNome() {
        return nome;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Livro: " + nome + "| Autor: " + autor.getNome();
    }
}
