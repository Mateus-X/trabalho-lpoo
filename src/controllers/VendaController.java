package controllers;

import interfaces.IVeiculoDAO;
import models.Veiculo;

public class VendaController {
    private IVeiculoDAO veiculoDAO;

    public VendaController(IVeiculoDAO veiculoDAO) {
        this.veiculoDAO = veiculoDAO;
    }

    public void venderVeiculo(String placa) {
        try {
            Veiculo veiculo = veiculoDAO.buscarPorPlaca(placa);
            if (veiculo == null) throw new IllegalArgumentException("Veiculo nao encontrado.");
            veiculo.vender();
            veiculoDAO.atualizar(veiculo);
            System.out.println("Venda realizada com sucesso.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
