package br.com.sysfarma.domain;

public class Medicamento {
    private Integer id;
    private String nome;
    private Double preco;
    private Fornecedor fornecedor = new Fornecedor();
    private TipoMedicamento tipoMedicamento = new TipoMedicamento();

    public Medicamento() {
    }

    public Medicamento(Integer id, String nome, Double preco, Fornecedor fornecedor, TipoMedicamento tipoMedicamento) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.fornecedor = fornecedor;
        this.tipoMedicamento = tipoMedicamento;
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

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public TipoMedicamento getTipoMedicamento() {
        return tipoMedicamento;
    }

    public void setTipoMedicamento(TipoMedicamento tipoMedicamento) {
        this.tipoMedicamento = tipoMedicamento;
    }
}
