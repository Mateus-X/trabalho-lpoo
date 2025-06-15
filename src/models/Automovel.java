package models;

import models.enums.Categoria;
import models.enums.Marca;
import models.enums.ModeloAutomovel;

public class Automovel extends Veiculo {

    private ModeloAutomovel modelo;

    public Automovel(Marca marca, Categoria categoria, double valorDeCompra, String placa, int ano, ModeloAutomovel modelo) {
        super(marca, categoria, valorDeCompra, placa, ano);
        this.modelo = modelo;
    }

    public ModeloAutomovel getModelo() {
        return modelo;
    }

    @Override
    public double getValorDiariaLocacao() {
        switch (this.getCategoria()) {
            case POPULAR:
                return 100.00;
            case INTERMEDIARIO:
                return 300.00;
            case LUXO:
                return 450.00;
            default:
                return 0.0;
        }
    }
}