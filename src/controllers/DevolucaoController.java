package controllers;

import dao.RepositorioMemoria;
import models.Veiculo;
import requests.VeiculoRequest;

public class DevolucaoController extends Controller{
    public DevolucaoController(RepositorioMemoria repositorioMemoria){
        super(repositorioMemoria);
    }

    public void devolver(String placa){
        try {
            VeiculoRequest.validarPlaca(placa);
            Veiculo veiculo = this.repositorioMemoria.buscarVeiculoPorPlaca(placa);

            veiculo.devolver();

            System.out.println("Devolução realizada com sucesso.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

    }

}
