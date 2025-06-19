package models;

import models.enums.Categoria;
import models.enums.Marca;
import models.enums.ModeloVan;

public class Van extends Veiculo {

    private ModeloVan modelo;

    // Construtor
    public Van(Marca marca, Categoria categoria, double valorDeCompra, String placa, int ano, ModeloVan modelo) {
        super(marca, categoria, valorDeCompra, placa, ano);
        this.modelo = modelo;
    }

    // Método getModelo() específico para Van
    public ModeloVan getModelo() {
        return modelo;
    }

    @Override
    public double getValorDiariaLocacao() { // Implementa o método abstrato de Veiculo
        // Implementação conforme tabela do enunciado (Requisito 2.e.iv)
        switch (this.getCategoria()) {
            case POPULAR:
                return 200.00;
            case INTERMEDIARIO:
                return 400.00;
            case LUXO:
                return 600.00;
            default:
                return 0.0;
        }
    }
}