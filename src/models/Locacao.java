package models;

import java.util.Calendar;

public class Locacao {
    private int dias;
    private double valor;
    private Calendar data;
    private Cliente cliente;

    public Locacao(int dias, double valor, Calendar data, Cliente cliente){
        this.dias = dias;
        this.valor = valor;
        this.data = data;
        this.cliente = cliente;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Calendar getData() {
        return data;
    }

    public double getValor() {
        return valor;
    }
}
