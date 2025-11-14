package controllers;

import interfaces.IClienteDAO;
import interfaces.IVeiculoDAO;
import interfaces.ILocacaoDAO;

public abstract class Controller {
    protected IClienteDAO clienteDAO;
    protected IVeiculoDAO veiculoDAO;
    protected ILocacaoDAO locacaoDAO;

    public Controller(IClienteDAO clienteDAO, IVeiculoDAO veiculoDAO, ILocacaoDAO locacaoDAO) {
        this.clienteDAO = clienteDAO;
        this.veiculoDAO = veiculoDAO;
        this.locacaoDAO = locacaoDAO;
    }
}
