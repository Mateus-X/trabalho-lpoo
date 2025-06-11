package models;

import models.enums.Estado;
import models.enums.ModeloMotocicleta;

public class Motocicleta extends Veiculo<ModeloMotocicleta> {
    public Motocicleta(double valorVenda, ModeloMotocicleta modelo, int ano, Estado estado, double valorDiariaLocacao) {
        super(valorVenda, modelo, ano, estado, valorDiariaLocacao);
    }
}
