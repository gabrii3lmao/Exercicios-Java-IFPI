package Entities;

import Entities.Interfaces.Produto;

public class ProdutoDigital extends Produto {
    private String urlDownload;
    private int tamanhoArquivoMB;
    
public ProdutoDigital(String nome, double preco, String descricao, String urlDownload, int tamanhoArquivoMB) {
        super(nome, preco, descricao);
        this.urlDownload = urlDownload;
        this.tamanhoArquivoMB = tamanhoArquivoMB;
    }

    public String getUrlDownload() {
        return urlDownload;
    }

    public int getTamanhoArquivoMB() {
        return tamanhoArquivoMB;
    }
}
