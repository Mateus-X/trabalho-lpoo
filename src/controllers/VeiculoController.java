package controllers;

import dao.RepositorioMemoria;
import java.util.List;
import models.Veiculo;
import models.enums.Categoria;
import models.enums.Marca;
import requests.VeiculoRequest;

public class VeiculoController extends Controller {

    public VeiculoController(RepositorioMemoria repositorioMemoria) {
        super(repositorioMemoria);
    }

    public void cadastrarVeiculo(Veiculo veiculo) {
        try {
            Veiculo veiculoValidado = VeiculoRequest.validar(veiculo);

            if (repositorioMemoria.buscarVeiculoPorPlaca(veiculoValidado.getPlaca()) != null) {
                throw new IllegalArgumentException("Veiculo com a placa " + veiculoValidado.getPlaca() + " ja cadastrado.");
            }

            repositorioMemoria.adicionarVeiculo(veiculoValidado);

            System.out.println("Veiculo cadastrado com sucesso");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Veiculo> listarTodosVeiculos() {
        return repositorioMemoria.listarTodosVeiculos();
    }

    public Veiculo buscarVeiculoPorPlaca(String placa) {
        return repositorioMemoria.buscarVeiculoPorPlaca(placa);
    }

    public List<Veiculo> listarVeiculosDisponiveis() {
        return repositorioMemoria.listarVeiculosDisponiveis();
    }

    public List<Veiculo> listarVeiculosLocados() {
        return repositorioMemoria.listarVeiculosLocados();
    }

    public List<Veiculo> listarVeiculosParaVenda() {
        return repositorioMemoria.listarVeiculosParaVenda();
    }

    public List<Veiculo> filtrarVeiculos(String tipo, Marca marca, Categoria categoria) {
        return repositorioMemoria.filtrarVeiculos(tipo, marca, categoria);
    }

    public boolean removerVeiculo(String placa) {
        return repositorioMemoria.removerVeiculo(placa);
    }
}
