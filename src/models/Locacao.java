package models;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Locacao {
    private Integer id; // id na base (pode ser null em objetos ainda nao persistidos)
    private int dias;
    private double valor;
    private Calendar data;
    private Cliente cliente;

    public Locacao(int dias, double valor, Calendar data, Cliente cliente) {
        this(null, dias, valor, data, cliente);
    }

    public Locacao(Integer id, int dias, double valor, Calendar data, Cliente cliente) {
        this.id = id;
        this.dias = dias;
        this.valor = valor;
        this.data = data;
        this.cliente = cliente;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getdias() {
        return dias;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Calendar getData() {
        return data;
    }

    public String getDataFormatada() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(data.getTime());
    }

    public double getValor() {
        return valor;
    }
}
