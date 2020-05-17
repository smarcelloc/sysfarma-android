package br.com.sysfarma.factory;

public class ScriptCreateTable {


    private static final String VENDA_FK_CLIENTE = "FOREIGN KEY(cliente_id) REFERENCES Cliente(id)";
    private  static final String MEDICAMENTO_FK_FORNECEDOR = "FOREIGN KEY(fornecedor_id) REFERENCES Fornecedor(id)";
    private  static final String MEDICAMENTO_FK_TIPO_MEDICAMENTO = "FOREIGN KEY(tipomedicamento_id) REFERENCES TipoMedicamento(id)";


    protected static final String CLIENTE = "CREATE TABLE Cliente(id INTEGER PRIMARY KEY, nome TEXT, endereco TEXT, numero_endereco TEXT)";
    protected static final String FUNCIONARIO = "CREATE TABLE Funcionario(id INTEGER PRIMARY KEY, nome TEXT, email TEXT)";
    protected static final String FORNECEDOR = "CREATE TABLE Fornecedor(id INTEGER PRIMARY KEY, nome TEXT, cnpj TEXT)";
    protected static final String TIPO_MEDICAMENTO = "CREATE TABLE TipoMedicamento(id INTEGER PRIMARY KEY, nome TEXT)";
    protected static final String VENDA = "CREATE TABLE Venda(id INTEGER PRIMARY KEY, total DOUBLE, data_atual TEXT, cliente_id INTEGER, "+ VENDA_FK_CLIENTE +")";
    protected static final String MEDICAMENTO = "CREATE TABLE Medicamento(id INTEGER PRIMARY KEY, nome TEXT, preco DOUBLE, fornecedor_id INTEGER, tipomedicamento_id INTEGER, " + MEDICAMENTO_FK_FORNECEDOR +", "+ MEDICAMENTO_FK_TIPO_MEDICAMENTO +")";

}
