public class ItemPedido {
 
    private int quantidade;
    private Produto produto;

    public ItemPedido (Produto produto, int quantidade){
    this.produto = produto;
    this.quantidade = quantidade;
    
}
    
    public double getSubTotal(){
        return produto.getPreco() *  quantidade;

    }

     public int getQuantidade(){
    return quantidade;
}

public Produto getProduto(){
    return produto;
}

}

https://github.com/KAIQUEELOI/Exercicios-Java-IFPI.git