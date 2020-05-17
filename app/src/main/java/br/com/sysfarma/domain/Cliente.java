package br.com.sysfarma.domain;

public class Cliente {
    private Integer id;
    private String nome;
    private String endereco;
    private String numeroEndereco;

    public Cliente() {
    }

    public Cliente(Integer id, String nome, String endereco, String numeroEndereco) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.numeroEndereco = numeroEndereco;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getNumeroEndereco() {
        return numeroEndereco;
    }

    public void setNumeroEndereco(String numeroEndereco) {
        this.numeroEndereco = numeroEndereco;
    }
}
