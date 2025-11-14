package controllers;

import java.util.Calendar;

import interfaces.IClienteDAO;
import interfaces.IVeiculoDAO;
import interfaces.ILocacaoDAO;
import models.Cliente;
import models.Veiculo;
import requests.ClienteRequest;
import requests.VeiculoRequest;

public class LocacaoController extends Controller {

    private IClienteDAO clienteDAO;
    private IVeiculoDAO veiculoDAO;
    private ILocacaoDAO locacaoDAO;

    public LocacaoController(IClienteDAO clienteDAO, IVeiculoDAO veiculoDAO, ILocacaoDAO locacaoDAO) {
        super(clienteDAO, veiculoDAO, locacaoDAO);
        this.clienteDAO = clienteDAO;
        this.veiculoDAO = veiculoDAO;
        this.locacaoDAO = locacaoDAO;
    }


    public void locar(String cpf, Calendar data, String placa, int dias) {
        try {
            VeiculoRequest.validarPlaca(placa);
            Veiculo veiculo = veiculoDAO.buscarPorPlaca(placa);

            ClienteRequest.validarCpf(cpf);
            Cliente cliente = clienteDAO.buscarPorCpf(cpf);

            if (cliente == null) throw new IllegalArgumentException("Cliente nao encontrado.");
            if (veiculo == null) throw new IllegalArgumentException("Veiculo nao encontrado.");

            // Verifica se cliente ja tem locacao ativa
            if (locacaoDAO.buscarLocacaoAtivaPorCliente(cpf) != null) {
                throw new IllegalArgumentException("Cliente ja tem veiculos locados.");
            }

            veiculo.locar(dias, data, cliente);

            // Persiste a alteração de estado e locacao via DAO
            veiculoDAO.atualizar(veiculo);

            System.out.println("Locacao realizada com sucesso.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
    }
}
