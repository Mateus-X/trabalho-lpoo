package interfaces;

import java.util.List;
import models.Cliente;

public interface IClienteDAO {
    void salvar(Cliente cliente) throws Exception;
    void atualizar(Cliente cliente) throws Exception;
    void excluir(String cpf) throws Exception;
    Cliente buscarPorCpf(String cpf) throws Exception;
    List<Cliente> listarTodos() throws Exception;
}