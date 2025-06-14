package models; // Pacote ajustado

import interfaces.VeiculoI; // Import ajustado
import models.enums.Categoria;
import models.enums.Estado;
import models.enums.Marca;

import java.util.Calendar;

public abstract class Veiculo implements VeiculoI {

    protected Marca marca;
    protected Estado estado;
    protected Locacao locacao;
    protected Categoria categoria;
    protected double valorDeCompra;
    protected String placa;
    protected int ano;

    public Veiculo(Marca marca, Categoria categoria, double valorDeCompra, String placa, int ano) {
        this.marca = marca;
        this.categoria = categoria;
        this.valorDeCompra = valorDeCompra;
        this.placa = placa;
        this.ano = ano;
        this.estado = Estado.DISPONIVEL; // Ou Estado.NOVO, dependendo do estado inicial quando comprado.
        this.locacao = null;
    }

    @Override
    public void locar(int dias, Calendar data, Cliente cliente) {
        if (this.estado == Estado.DISPONIVEL) {
            this.estado = Estado.LOCADO;
            double valorLocacao = dias * this.getValorDiariaLocacao();
            this.locacao = new Locacao(dias, valorLocacao, data, cliente);
        } else {
            System.out.println("Não foi possível locar o veículo. Estado atual: " + this.estado);
        }
    }

    @Override
    public void vender() {
        if (this.estado == Estado.DISPONIVEL) {
            this.estado = Estado.VENDIDO;
            this.locacao = null;
        } else {
            System.out.println("Não foi possível vender o veículo. Estado atual: " + this.estado);
        }
    }

    @Override
    public void devolver() {
        if (this.estado == Estado.LOCADO) {
            this.estado = Estado.DISPONIVEL;
            this.locacao = null;
        } else {
            System.out.println("Não foi possível devolver o veículo. Estado atual: " + this.estado);
        }
    }

    @Override
    public Estado getEstado() {
        return estado;
    }

    @Override
    public Marca getMarca() {
        return marca;
    }

    @Override
    public Categoria getCategoria() {
        return categoria;
    }

    @Override
    public Locacao getLocacao() {
        return locacao;
    }

    @Override
    public String getPlaca() {
        return placa;
    }

    @Override
    public int getano() {
        return ano;
    }

    @Override
    public double getValorParaVenda() {
        int anoAtual = Calendar.getInstance().get(Calendar.YEAR);
        int idadeVeiculoEmAnos = anoAtual - this.ano;

        double valorCalculado = this.valorDeCompra - (idadeVeiculoEmAnos * 0.15 * this.valorDeCompra);

        double dezPorCentoDoValorDeCompra = this.valorDeCompra * 0.10;
        if (valorCalculado < dezPorCentoDoValorDeCompra) {
            return dezPorCentoDoValorDeCompra;
        }

        return valorCalculado;
    }

    @Override
    public abstract double getValorDiariaLocacao();
}