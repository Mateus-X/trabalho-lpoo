package controllers;

import java.util.Calendar;
import java.util.List;

import dao.RepositorioMemoria;
import models.Cliente;
import models.Veiculo;
import requests.ClienteRequest;
import requests.VeiculoRequest;

public class LocacaoController extends Controller {

    public LocacaoController(RepositorioMemoria repositorioMemoria) {
        super(repositorioMemoria);
    }


    public void locar(String cpf, Calendar data, String placa, int dias) {
        try {
            VeiculoRequest.validarPlaca(placa);
            Veiculo veiculo = this.repositorioMemoria.buscarVeiculoPorPlaca(placa);

            ClienteRequest.validarCpf(cpf);
            Cliente cliente = this.repositorioMemoria.buscarClientePorCpf(cpf);

            List<Veiculo> veiculosLocados = this.repositorioMemoria.listarVeiculosLocados();
            
            for (Veiculo v : veiculosLocados) {
                if (v.getLocacao().getCliente().getCPF() == cliente.getCPF()) {
                    throw new IllegalArgumentException("Cliente ja tem veiculos locados.");
                }
            }

            veiculo.locar(dias, data, cliente);

            System.out.println("Locacao realizada com sucesso.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        
    }
}
