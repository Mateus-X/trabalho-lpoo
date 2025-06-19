package models;

import models.enums.Categoria;
import models.enums.Marca;
import models.enums.ModeloMotocicleta;

public class Motocicleta extends Veiculo {

    private ModeloMotocicleta modelo;

    // Construtor ajustado
    public Motocicleta(Marca marca, Categoria categoria, double valorDeCompra, String placa, int ano, ModeloMotocicleta modelo) {
        super(marca, categoria, valorDeCompra, placa, ano);
        this.modelo = modelo;
    }

    // Método getModelo() específico para Motocicleta
    public ModeloMotocicleta getModelo() {
        return modelo;
    }

    @Override
    public double getValorDiariaLocacao() { // Implementa o método abstrato de Veiculo
        switch (this.getCategoria()) {
            case POPULAR:
                return 70.00; 
            case INTERMEDIARIO:
                return 200.00;
            case LUXO:
                return 350.00;
            default:
                return 0.0;
        }
    }
}