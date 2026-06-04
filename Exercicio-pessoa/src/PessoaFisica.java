public class PessoaFisica extends Pessoa{
    private String cpf;
    private char sexo;

    public String getCpf() {
        return cpf;
    }
    
    public char getSexo() {
        return sexo;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }
}
