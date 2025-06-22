package controllers;

import dao.RepositorioMemoria;
import models.Veiculo;

public class VendaController {
    private RepositorioMemoria repositorioMemoria;

    public VendaController(RepositorioMemoria repositorioMemoria) {
        this.repositorioMemoria = repositorioMemoria;
    }

    public boolean venderVeiculo(String placa) {
        try {
            Veiculo veiculo = repositorioMemoria.buscarVeiculoPorPlaca(placa);
            veiculo.vender(); // muda o estado do veículo para VENDIDO
            return repositorioMemoria.removerVeiculo(placa); // remove da lista
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao vender veículo: " + e.getMessage());
            return false;
        }
    }
}
