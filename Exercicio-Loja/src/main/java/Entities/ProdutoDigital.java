package Entities;

import Entities.Interfaces.Produto;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * Produto digital (heranca SINGLE_TABLE na tabela "produtos").
 * Discriminador: tipo = "DIGITAL".
 */
@Entity
@DiscriminatorValue("DIGITAL")
public class ProdutoDigital extends Produto {

    @Column(name = "url_download", length = 300)
    private String urlDownload;

    @Column(name = "tamanho_arquivo_mb")
    private int tamanhoArquivoMB;

    public ProdutoDigital() {
        super();
    }

    public ProdutoDigital(String nome, double preco, String descricao, String urlDownload, int tamanhoArquivoMB) {
        super(nome, preco, descricao);
        this.urlDownload = urlDownload;
        this.tamanhoArquivoMB = tamanhoArquivoMB;
    }

    public String getUrlDownload() {
        return urlDownload;
    }

    public void setUrlDownload(String urlDownload) {
        this.urlDownload = urlDownload;
    }

    public int getTamanhoArquivoMB() {
        return tamanhoArquivoMB;
    }

    public void setTamanhoArquivoMB(int tamanhoArquivoMB) {
        this.tamanhoArquivoMB = tamanhoArquivoMB;
    }
}
