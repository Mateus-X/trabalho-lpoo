package models;

public class Cliente {
    private String nome;
    private String sobrenome;
    private String RG;
    private String CPF;
    private String endereco;

    public Cliente(String nome, String sobrenome, String RG, String CPF, String endereco) {
        this.nome = nome.trim();
        this.sobrenome = sobrenome.trim();
        this.RG = RG.trim();
        this.CPF = CPF.trim();
        this.endereco = endereco.trim();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getRG() {
        return RG;
    }

    public void setRG(String RG) {
        this.RG = RG;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}
