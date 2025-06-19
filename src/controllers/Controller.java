package controllers;

import dao.RepositorioMemoria;

public abstract class Controller {
    protected RepositorioMemoria repositorioMemoria;

    public Controller(RepositorioMemoria repositorioMemoria) {
        this.repositorioMemoria = repositorioMemoria;
    }
}
