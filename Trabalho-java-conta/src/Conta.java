public class Conta {
    private int numero;
    private double saldo;
    private String titular;

    public String getTitular() {
        return titular;
    }

    public int getNumero() {
        return numero;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public void sacar(double valor) {
        if (valor <= this.saldo) {
            this.saldo -= valor;
            System.out.println("Valor sacado. Seu novo saldo será de: " + getSaldo());
        } else {
            System.out.println("Saque não realizado");
        }
    }

    public void depositar(double valor) {
        if (valor > 0) {
            this.saldo += valor;
            System.out.println("Valor depositado. Seu novo saldo será de: " + getSaldo());
        } else {
            System.out.println("Deposito não realizado");
        }
    }
}
