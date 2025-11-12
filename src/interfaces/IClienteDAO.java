package interfaces;

import java.sql.SQLException;
import java.util.List;
import models.Cliente;

public interface IClienteDAO {
    void adicionarCliente(Cliente cliente) throws SQLException, IllegalArgumentException;
    void atualizarCliente(Cliente cliente) throws SQLException, IllegalArgumentException;
    void excluirCliente(String cpf) throws SQLException, IllegalArgumentException;
    Cliente buscarClientePorCpf(String cpf) throws SQLException;
    List<Cliente> listarTodosClientes() throws SQLException;
}