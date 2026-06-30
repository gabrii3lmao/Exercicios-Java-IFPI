package Entities;

public class Boleto implements Entities.Interfaces.Pagamento {
    private String codigoBarras;

    public Boleto(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    @Override
    public boolean processarPagamento(double valor) {
        System.out.println("Processando pagamento via boleto no valor de: " + valor);
        return true;
    }
    
}
