public class ProgramaConta {
    public static void main(String[] args) throws Exception {
        Conta conta = new Conta();
        conta.setTitular("Gabriel");
        conta.setSaldo(1000);
        conta.setNumero(1);
        System.out.println(conta.getSaldo());
        conta.depositar(300);
        conta.sacar(300);
    }
}
