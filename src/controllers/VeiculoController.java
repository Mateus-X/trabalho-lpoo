package controllers;

import dao.RepositorioMemoria;
import models.Veiculo;
import requests.VeiculoRequest;

public class VeiculoController {
    private RepositorioMemoria repositorioMemoria;

    public VeiculoController(RepositorioMemoria repositorioMemoria) {
        this.repositorioMemoria = repositorioMemoria;
    }

    public void cadastrarVeiculo(Veiculo veiculo) {
        try {
            Veiculo veiculoValidado = VeiculoRequest.validar(veiculo);
            repositorioMemoria.adicionarVeiculo(veiculoValidado);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao cadastrar veículo: " + e.getMessage());
        }
    }
}