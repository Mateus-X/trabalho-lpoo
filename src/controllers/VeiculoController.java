package controllers;

import interfaces.IVeiculoDAO;
import java.util.List;
import models.Veiculo;
import models.enums.Categoria;
import models.enums.Marca;
import requests.VeiculoRequest;

public class VeiculoController extends Controller {

    private IVeiculoDAO veiculoDAO;

    public VeiculoController(IVeiculoDAO veiculoDAO) {
        super(null, veiculoDAO, null);
        this.veiculoDAO = veiculoDAO;
    }

    public void cadastrarVeiculo(Veiculo veiculo) {
        try {
            Veiculo veiculoValidado = VeiculoRequest.validar(veiculo);

            if (veiculoDAO.buscarPorPlaca(veiculoValidado.getPlaca()) != null) {
                throw new IllegalArgumentException("Veiculo com a placa " + veiculoValidado.getPlaca() + " ja cadastrado.");
            }

            veiculoDAO.salvar(veiculoValidado);

            System.out.println("Veiculo cadastrado com sucesso");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Veiculo> listarTodosVeiculos() {
        try {
            return veiculoDAO.listarTodos();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return java.util.Collections.emptyList();
        }
    }

    public Veiculo buscarVeiculoPorPlaca(String placa) {
        try {
            return veiculoDAO.buscarPorPlaca(placa);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<Veiculo> listarVeiculosDisponiveis() {
        try {
            return veiculoDAO.listarPorEstado(models.enums.Estado.DISPONIVEL);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return java.util.Collections.emptyList();
        }
    }

    public List<Veiculo> listarVeiculosLocados() {
        try {
            return veiculoDAO.listarPorEstado(models.enums.Estado.LOCADO);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return java.util.Collections.emptyList();
        }
    }

    public List<Veiculo> listarVeiculosParaVenda() {
        return listarVeiculosDisponiveis();
    }

    public List<Veiculo> filtrarVeiculos(String tipo, Marca marca, Categoria categoria) {
        try {
            return veiculoDAO.filtrar(tipo, marca, categoria);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return java.util.Collections.emptyList();
        }
    }

    public void removerVeiculo(String placa) {
        try {
            Veiculo v = veiculoDAO.buscarPorPlaca(placa);
            if (v == null) throw new IllegalArgumentException("Veiculo nao encontrado.");
            if (v.getEstado() != models.enums.Estado.VENDIDO) {
                throw new IllegalArgumentException("Veiculo nao pode ser removido, pois nao esta vendido.");
            }
            System.out.println("Veiculo removido. Placa: " + placa);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
