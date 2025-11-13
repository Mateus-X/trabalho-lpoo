package dao;

import dao.conf.ConexaoDao;
import interfaces.IClienteDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import models.Cliente;

public class ClienteDAO implements IClienteDAO {

    @Override
    public void salvar(Cliente cliente) throws Exception {
        String sql = "INSERT INTO clientes (cpf, nome, sobrenome, rg, endereco) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexaoDao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, cliente.getCPF());
            ps.setString(2, cliente.getNome());
            ps.setString(3, cliente.getSobrenome());
            ps.setString(4, cliente.getRG());
            ps.setString(5, cliente.getEndereco());
            ps.executeUpdate();
        }
    }

    @Override
    public void atualizar(Cliente cliente) throws Exception {
        String sql = "UPDATE clientes SET nome = ?, sobrenome = ?, rg = ?, endereco = ? WHERE cpf = ?";
        try (Connection conn = ConexaoDao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getSobrenome());
            ps.setString(3, cliente.getRG());
            ps.setString(4, cliente.getEndereco());
            ps.setString(5, cliente.getCPF());
            ps.executeUpdate();
        }
    }

    @Override
    public void excluir(String cpf) throws Exception {
        String sql = "DELETE FROM clientes WHERE cpf = ?";
        try (Connection conn = ConexaoDao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, cpf);
            ps.executeUpdate();
        }
    }

    @Override
    public Cliente buscarPorCpf(String cpf) throws Exception {
        String sql = "SELECT * FROM clientes WHERE cpf = ?";
        try (Connection conn = ConexaoDao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, cpf);
            try (ResultSet rs = ps.executeQuery()) {
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
        return null;
    }

    @Override
    public List<Cliente> listarTodos() throws Exception {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM clientes";
        try (Connection conn = ConexaoDao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
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