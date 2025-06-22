package controllers;

import java.util.List;

import dao.RepositorioMemoria;
import models.Cliente;
import requests.ClienteRequest;

public class ClienteController extends Controller {

    public ClienteController(RepositorioMemoria repositorioMemoria) {
        super(repositorioMemoria);
    }
    
    // Cadastrar Cliente com validacao
    public void cadastrarCliente(String nome, String sobrenome, String RG, String CPF, String endereco) {
        try {
            Cliente cliente = new Cliente(nome, sobrenome, RG, CPF, endereco);

            ClienteRequest.validar(cliente);

            this.repositorioMemoria.adicionarCliente(cliente);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public Cliente buscarCliente(String CPF) {
        try {
            return this.repositorioMemoria.buscarClientePorCpf(CPF);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void atualizarCliente(String nome, String sobrenome, String RG, String CPF, String endereco) {
        try {
            Cliente cliente = new Cliente(nome, sobrenome, RG, CPF, endereco);

            ClienteRequest.validar(cliente);
            this.repositorioMemoria.atualizarCliente(cliente);

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void excluirCliente(String CPF) {
        try {
            boolean result = this.repositorioMemoria.excluirCliente(CPF);

            if (result) {
                System.out.println("Cliente excluido com sucesso.");
            } else {
                System.out.println("Erro interno do servidor.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Cliente> listarTodosClientes() {
        return this.repositorioMemoria.listarTodosClientes();
    }
}
