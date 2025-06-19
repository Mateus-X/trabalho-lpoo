package controllers;

import dao.RepositorioMemoria;
import java.util.List;
import models.Cliente;
import requests.ClienteRequest;

public class ClienteController {
    private RepositorioMemoria repositorioMemoria;

    public ClienteController(RepositorioMemoria repositorioMemoria) {
        this.repositorioMemoria = repositorioMemoria;
    }

    // Cadastrar Cliente com validação
    public void cadastrarCliente(String nome, String sobrenome, String RG, String CPF, String endereco) {
        try {
            Cliente cliente = new Cliente(nome, sobrenome, RG, CPF, endereco);
            Cliente clienteValidado = ClienteRequest.Validar(cliente);
            repositorioMemoria.adicionarCliente(clienteValidado);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao cadastrar cliente: " + e.getMessage());
        }
    }

    // Listar todos os clientes
    public List<Cliente> listarTodosClientes() {
        return repositorioMemoria.listarTodosClientes();
    }

    // Buscar cliente por CPF
    public Cliente buscarClientePorCpf(String cpf) {
        return repositorioMemoria.buscarClientePorCpf(cpf);
    }

    // Atualizar cliente
    public boolean atualizarCliente(Cliente clienteAtualizado) {
        try {
            Cliente clienteValidado = ClienteRequest.Validar(clienteAtualizado);
            return repositorioMemoria.atualizarCliente(clienteValidado);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao atualizar cliente: " + e.getMessage());
            return false;
        }
    }

    // Excluir cliente
    public boolean excluirCliente(String cpf) {
        return repositorioMemoria.excluirCliente(cpf);
    }
}
