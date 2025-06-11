package models;
import models.enums.Categoria;
import models.enums.Estado;
import models.enums.Marca;

import java.util.Calendar;

public abstract class Veiculo<T extends Enum<T>> {
    private double valorVenda;
    private double valorDiariaLocacao;
    private T modelo;
    private int ano;
    private Estado estado;
    private Categoria categoria;
    private Marca marca;
    private Locacao locacao;

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public double getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda(double valorVenda) {
        this.valorVenda = valorVenda;
    }

    public double getValorDiariaLocacao() {
        return valorDiariaLocacao;
    }

    public void setValorDiariaLocacao(double valorDiariaLocacao) {
        this.valorDiariaLocacao = valorDiariaLocacao;
    }

    public T getModelo() {
        return modelo;
    }

    public void setModelo(T modelo) {
        this.modelo = modelo;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Veiculo(double valorVenda, T modelo, int ano, Estado estado, double valorDiariaLocacao) {
        this.valorVenda = valorVenda;
        this.modelo = modelo;
        this.ano = ano;
        this.estado = estado;
        this.valorDiariaLocacao = valorDiariaLocacao;
    }

    public void vender() {}

    public void alugar(int dias, Calendar data, Cliente cliente) {
        if (this.estado != Estado.LOCADO) {
            this.estado = Estado.LOCADO;

            double valorLocacao = dias * this.valorDiariaLocacao;
            this.locacao = new Locacao(dias, valorLocacao, data, cliente);
        }
    }

    public void devolverAluguel() {
        if (this.estado == Estado.LOCADO) {
            this.estado = Estado.DISPONIVEL; // ou outro estado apropriado
            this.locacao = null;
        }
    }
}
