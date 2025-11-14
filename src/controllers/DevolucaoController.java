package controllers;

import interfaces.IVeiculoDAO;
import interfaces.ILocacaoDAO;
import models.Veiculo;
import requests.VeiculoRequest;

public class DevolucaoController extends Controller{
    private IVeiculoDAO veiculoDAO;

    public DevolucaoController(IVeiculoDAO veiculoDAO, ILocacaoDAO locacaoDAO){
        super(null, veiculoDAO, locacaoDAO);
        this.veiculoDAO = veiculoDAO;
        this.locacaoDAO = locacaoDAO;
    }

    public void devolver(String placa){
        try {
            VeiculoRequest.validarPlaca(placa);
            Veiculo veiculo = this.veiculoDAO.buscarPorPlaca(placa);

            if (veiculo == null) throw new IllegalArgumentException("Veiculo nao encontrado.");

            veiculo.devolver();
            // persiste alteração e conclui locacao
            veiculoDAO.atualizar(veiculo);

            System.out.println("Devolucao realizada com sucesso.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}
