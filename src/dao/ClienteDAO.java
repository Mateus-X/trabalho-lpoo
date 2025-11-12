package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import interfaces.IClienteDAO;
import models.Cliente;

public class ClienteDAO implements IClienteDAO {

    private Connection conexao;

    // Recebe a conexão no construtor
    public ClienteDAO(Connection conexao) {
        this.conexao = conexao;
    }

    @Override
    public void adicionarCliente(Cliente cliente) throws SQLException, IllegalArgumentException {
        if (buscarClientePorCpf(cliente.getCPF()) != null) {
            throw new IllegalArgumentException("Cliente ja cadastrado.");
        }
        
        String sql = "INSERT INTO cliente (cpf, nome, sobrenome, rg, endereco) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, cliente.getCPF());
            stmt.setString(2, cliente.getNome());
            stmt.setString(3, cliente.getSobrenome());
            stmt.setString(4, cliente.getRG());
            stmt.setString(5, cliente.getEndereco());
            stmt.executeUpdate();
        }
    }

    @Override
    public void atualizarCliente(Cliente cliente) throws SQLException, IllegalArgumentException {
        String sql = "UPDATE cliente SET nome = ?, sobrenome = ?, rg = ?, endereco = ? WHERE cpf = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getSobrenome());
            stmt.setString(3, cliente.getRG());
            stmt.setString(4, cliente.getEndereco());
            stmt.setString(5, cliente.getCPF());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                 throw new IllegalArgumentException("Cliente nao encontrado");
            }
        }
    }

    @Override
    public void excluirCliente(String cpf) throws SQLException, IllegalArgumentException {
        // [cite: 13, 14] Verifica se o cliente possui veículos locados
        String checkSql = "SELECT COUNT(v.placa) "
                        + "FROM veiculo v "
                        + "JOIN locacao l ON v.locacao_id = l.id "
                        + "WHERE l.cliente_cpf = ? AND v.estado = 'LOCADO'"; // Verifica se ESTÁ LOCADO
        
        try (PreparedStatement checkStmt = conexao.prepareStatement(checkSql)) {
            checkStmt.setString(1, cpf);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    throw new IllegalArgumentException("Cliente possui veiculos locados.");
                }
            }
        }
        
        // Se não tiver, exclui
        String deleteSql = "DELETE FROM cliente WHERE cpf = ?";
        try (PreparedStatement deleteStmt = conexao.prepareStatement(deleteSql)) {
            deleteStmt.setString(1, cpf);
            int affectedRows = deleteStmt.executeUpdate();
            if (affectedRows == 0) {
                 throw new IllegalArgumentException("Cliente nao encontrado");
            }
        }
    }

    @Override
    public Cliente buscarClientePorCpf(String cpf) throws SQLException {
        String sql = "SELECT * FROM cliente WHERE cpf = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Cliente(
                        rs.getString("nome"),
                        rs.getString("sobrenome"),
                        rs.getString("rg"),
                        rs.getString("cpf"),
                        rs.getString("endereco")
                    );
                }
            }
        }
        return null; // Não encontrado
    }

    @Override
    public List<Cliente> listarTodosClientes() throws SQLException {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM cliente";
        try (PreparedStatement stmt = conexao.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                clientes.add(new Cliente(
                    rs.getString("nome"),
                    rs.getString("sobrenome"),
                    rs.getString("rg"),
                    rs.getString("cpf"),
                    rs.getString("endereco")
                ));
            }
        }
        return clientes;
    }
}