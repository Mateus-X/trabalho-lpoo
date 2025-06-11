package models;

import models.enums.Estado;
import models.enums.ModeloVan;

public class Van extends Veiculo<ModeloVan> {
    public Van(double valorVenda, ModeloVan modelo, int ano, Estado estado, double valorDiariaLocacao) {
        super(valorVenda, modelo, ano, estado, valorDiariaLocacao);
    }
}
