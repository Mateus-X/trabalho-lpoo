package controllers;

import dao.RepositorioMemoria;
import models.Veiculo;

public class VendaController {
    private RepositorioMemoria repositorioMemoria;

    public VendaController(RepositorioMemoria repositorioMemoria) {
        this.repositorioMemoria = repositorioMemoria;
    }

    public void venderVeiculo(String placa) {
        try {
            Veiculo veiculo = repositorioMemoria.buscarVeiculoPorPlaca(placa);
            veiculo.vender();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
