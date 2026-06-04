import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        Biblioteca biblioteca = new Biblioteca();

        List<Livro> listaLivros = new ArrayList<>();

        // livro 1
        Livro livro1 = new Livro();
        Autor autor1 = new Autor();

        autor1.setNome("George Orwell");

        livro1.setNome("1984");
        livro1.setAutor(autor1);

        listaLivros.add(livro1);

        // livro 2
        Livro livro2 = new Livro();

        Autor autor2 = new Autor();
        autor2.setNome("Júlio Verne");

        livro2.setNome("Viagem ao Centro da Terra");
        livro2.setAutor(autor1);

        listaLivros.add(livro2);

        biblioteca.setLivros(listaLivros);
        System.out.println(biblioteca.getLivros());
    }
}
