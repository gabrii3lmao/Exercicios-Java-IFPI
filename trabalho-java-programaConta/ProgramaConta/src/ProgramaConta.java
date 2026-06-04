public class ProgramaConta {

    public static void main(String[] args) {

        Diretor diretor = new Diretor();
        diretor.setSenha(1234);

        Gerente gerente = new Gerente();
        gerente.setSenha(5678);

        Cliente cliente = new Cliente();
        cliente.setSenha(9999);

        System.out.println("Diretor: "
                + diretor.autenticar(1234));

        System.out.println("Diretor: "
                + diretor.autenticar(1111));

        System.out.println("Gerente: "
                + gerente.autenticar(5678));

        System.out.println("Cliente: "
                + cliente.autenticar(9999));
    }
}