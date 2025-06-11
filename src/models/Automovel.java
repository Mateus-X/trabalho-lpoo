package models;

import models.enums.Estado;
import models.enums.ModeloAutomovel;

public class Automovel extends Veiculo<ModeloAutomovel> {
    public Automovel(double valorVenda, ModeloAutomovel modelo, int ano, Estado estado, double valorDiariaLocacao) {
        super(valorVenda, modelo, ano, estado, valorDiariaLocacao);
    }
}
