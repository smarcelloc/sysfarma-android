package br.com.sysfarma.domain;
import android.annotation.SuppressLint;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Venda {
    private Integer id;
    private Double total;
    private String dataAtual;
    private Cliente cliente = new Cliente();

    public Venda() {

    }

    public Venda(Integer id, Double total, Cliente cliente) {
        this.id = id;
        this.total = total;
        this.cliente = cliente;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getDataAtual(){
        Date data = new Date();
        SimpleDateFormat formatador = new SimpleDateFormat("dd/mm/yyyy", Locale.ENGLISH);
        return formatador.format(data);
    }

    public void setDataAtual(String dataAtual) {
        this.dataAtual = dataAtual;
    }
}
