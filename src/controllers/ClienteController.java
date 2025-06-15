package controllers;

import dao.RepositorioMemoria;
import models.Cliente;
import requests.ClienteRequest;

public class ClienteController {
    private RepositorioMemoria repositorioMemoria;

    public ClienteController(RepositorioMemoria repositorioMemoria) {
        this.repositorioMemoria = repositorioMemoria;
    }

    public void cadastrarCliente(String nome, String sobrenome, String RG, String CPF, String endereco) {
        try {
            Cliente cliente = new Cliente(nome, sobrenome, RG, CPF, endereco);

            Cliente clienteValidado = ClienteRequest.Validar(cliente);

            repositorioMemoria.adicionarCliente(clienteValidado);
        } catch (IllegalArgumentException e) { }
    }
}
