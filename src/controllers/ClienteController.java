package controllers;

import java.util.List;

import interfaces.IClienteDAO;
import models.Cliente;
import requests.ClienteRequest;

public class ClienteController extends Controller {

    private IClienteDAO clienteDAO;

    public ClienteController(IClienteDAO clienteDAO) {
        super(clienteDAO, null, null);
        this.clienteDAO = clienteDAO;
    }

    // Cadastrar Cliente com validacao
    public void cadastrarCliente(String nome, String sobrenome, String RG, String CPF, String endereco) {
        try {
            Cliente cliente = new Cliente(nome, sobrenome, RG, CPF, endereco);

            ClienteRequest.validar(cliente);

            Cliente existente = clienteDAO.buscarPorCpf(CPF);
            if (existente != null) {
                System.out.println("Cliente com este CPF ja existe.");
                return;
            }

            clienteDAO.salvar(cliente);
            System.out.println("Cliente cadastrado com sucesso.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Cliente buscarCliente(String CPF) {
        try {
            return clienteDAO.buscarPorCpf(CPF);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void atualizarCliente(String nome, String sobrenome, String RG, String CPF, String endereco) {
        try {
            Cliente cliente = new Cliente(nome, sobrenome, RG, CPF, endereco);

            ClienteRequest.validar(cliente);
            clienteDAO.atualizar(cliente);

            System.out.println("Cliente atualizado com sucesso.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void excluirCliente(String CPF) {
        try {
            clienteDAO.excluir(CPF);
            System.out.println("Cliente excluido com sucesso.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Cliente> listarTodosClientes() {
        try {
            return clienteDAO.listarTodos();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return java.util.Collections.emptyList();
        }
    }
}
