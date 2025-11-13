package dao;

import dao.conf.ConexaoDao;
import interfaces.IClienteDAO;
import interfaces.ILocacaoDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import models.Cliente;
import models.Locacao;

// LocacaoDAO precisa do ClienteDAO para construir o objeto Locacao completo
public class LocacaoDAO implements ILocacaoDAO {

    private IClienteDAO clienteDAO;

    public LocacaoDAO(IClienteDAO clienteDAO) {
        this.clienteDAO = clienteDAO;
    }

    @Override
    public void salvar(Locacao locacao, String placaVeiculo) throws Exception {
        // Assume uma tabela 'locacoes' com 'concluida' = false para ativas
        String sql = "INSERT INTO locacoes (veiculo_placa, cliente_cpf, data_locacao, dias, valor, concluida) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexaoDao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, placaVeiculo);
            ps.setString(2, locacao.getCliente().getCPF());
            ps.setDate(3, new java.sql.Date(locacao.getData().getTimeInMillis()));
            ps.setInt(4, locacao.getdias());
            ps.setDouble(5, locacao.getValor());
            ps.setBoolean(6, false); // False = Ativa
            ps.executeUpdate();
        }
    }

    @Override
    public void concluir(String placaVeiculo) throws Exception {
        // Marca a locação ativa como concluída
        String sql = "UPDATE locacoes SET concluida = ? WHERE veiculo_placa = ? AND concluida = ?";
        try (Connection conn = ConexaoDao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setBoolean(1, true); // Concluída
            ps.setString(2, placaVeiculo);
            ps.setBoolean(3, false); // Onde estava ativa
            ps.executeUpdate();
        }
    }

    @Override
    public Locacao buscarLocacaoAtivaPorVeiculo(String placaVeiculo) throws Exception {
        String sql = "SELECT * FROM locacoes WHERE veiculo_placa = ? AND concluida = ?";
        return buscar(sql, placaVeiculo);
    }
    
    @Override
    public Locacao buscarLocacaoAtivaPorCliente(String cpfCliente) throws Exception {
        String sql = "SELECT * FROM locacoes WHERE cliente_cpf = ? AND concluida = ?";
        return buscar(sql, cpfCliente);
    }

    private Locacao buscar(String sql, String parametro) throws Exception {
        try (Connection conn = ConexaoDao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, parametro);
            ps.setBoolean(2, false); // Apenas ativas

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String clienteCpf = rs.getString("cliente_cpf");
                    Cliente cliente = clienteDAO.buscarPorCpf(clienteCpf); // Usa o ClienteDAO
                    
                    if (cliente == null) {
                        throw new Exception("Cliente da locação não encontrado: " + clienteCpf);
                    }
                    
                    Calendar data = Calendar.getInstance();
                    data.setTime(rs.getDate("data_locacao"));
                    
                    return new Locacao(
                        rs.getInt("dias"),
                        rs.getDouble("valor"),
                        data,
                        cliente
                    );
                }
            }
        }
        return null;
    }
}