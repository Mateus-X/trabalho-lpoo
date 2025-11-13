package dao;

import dao.conf.ConexaoDao;
import interfaces.ILocacaoDAO;
import interfaces.IVeiculoDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import models.*;
import models.enums.*;

public class VeiculoDAO implements IVeiculoDAO {

    private ILocacaoDAO locacaoDAO;

    public VeiculoDAO(ILocacaoDAO locacaoDAO) {
        this.locacaoDAO = locacaoDAO;
    }

    /**
     * Tabela Única:
     * Tabela 'veiculos' com colunas:
     * placa (PK), marca, categoria, valor_compra, ano, estado,
     * tipo_veiculo (CHAR: 'A', 'M', 'V'),
     * modelo_automovel (NULLABLE), modelo_motocicleta (NULLABLE), modelo_van (NULLABLE)
     */
    @Override
    public void salvar(Veiculo veiculo) throws Exception {
        String sql = "INSERT INTO veiculos (placa, marca, categoria, valor_compra, ano, estado, " +
                     "tipo_veiculo, modelo_automovel, modelo_motocicleta, modelo_van) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = ConexaoDao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, veiculo.getPlaca());
            ps.setString(2, veiculo.getMarca().name());
            ps.setString(3, veiculo.getCategoria().name());
            ps.setDouble(4, veiculo.getValorParaVenda()); // Assumindo valorDeCompra
            ps.setInt(5, veiculo.getano());
            ps.setString(6, veiculo.getEstado().name());

            if (veiculo instanceof Automovel) {
                ps.setString(7, "A");
                ps.setString(8, ((Automovel) veiculo).getModelo().name());
                ps.setNull(9, java.sql.Types.VARCHAR);
                ps.setNull(10, java.sql.Types.VARCHAR);
            } else if (veiculo instanceof Motocicleta) {
                ps.setString(7, "M");
                ps.setNull(8, java.sql.Types.VARCHAR);
                ps.setString(9, ((Motocicleta) veiculo).getModelo().name());
                ps.setNull(10, java.sql.Types.VARCHAR);
            } else if (veiculo instanceof Van) {
                ps.setString(7, "V");
                ps.setNull(8, java.sql.Types.VARCHAR);
                ps.setNull(9, java.sql.Types.VARCHAR);
                ps.setString(10, ((Van) veiculo).getModelo().name());
            }
            ps.executeUpdate();
        }
    }

    /**
     * Atualiza apenas o estado e a locação (se aplicável).
     * Não atualiza dados imutáveis (placa, modelo, etc).
     */
    @Override
    public void atualizar(Veiculo veiculo) throws Exception {
        String sql = "UPDATE veiculos SET estado = ? WHERE placa = ?";
        try (Connection conn = ConexaoDao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, veiculo.getEstado().name());
            ps.setString(2, veiculo.getPlaca());
            ps.executeUpdate();
            
            // Se o veículo foi locado, salva a locação
            if (veiculo.getEstado() == Estado.LOCADO && veiculo.getLocacao() != null) {
                // Remove locações ativas antigas (segurança) e salva a nova
                locacaoDAO.concluir(veiculo.getPlaca()); 
                locacaoDAO.salvar(veiculo.getLocacao(), veiculo.getPlaca());
            }
            // Se foi devolvido ou vendido, conclui a locação
            else if (veiculo.getEstado() == Estado.DISPONIVEL || veiculo.getEstado() == Estado.VENDIDO) {
                locacaoDAO.concluir(veiculo.getPlaca());
            }
        }
    }

    @Override
    public Veiculo buscarPorPlaca(String placa) throws Exception {
        String sql = "SELECT * FROM veiculos WHERE placa = ?";
        try (Connection conn = ConexaoDao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, placa);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return instanciarVeiculo(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Veiculo> listarPorEstado(Estado estado) throws Exception {
        List<Veiculo> veiculos = new ArrayList<>();
        String sql = "SELECT * FROM veiculos WHERE estado = ?";
        try (Connection conn = ConexaoDao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, estado.name());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    veiculos.add(instanciarVeiculo(rs));
                }
            }
        }
        return veiculos;
    }
    
    @Override
    public List<Veiculo> listarTodos() throws Exception {
        List<Veiculo> veiculos = new ArrayList<>();
        String sql = "SELECT * FROM veiculos";
        try (Connection conn = ConexaoDao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                veiculos.add(instanciarVeiculo(rs));
            }
        }
        return veiculos;
    }

    @Override
    public List<Veiculo> filtrar(String tipo, Marca marca, Categoria categoria) throws Exception {
        List<Veiculo> todos = listarTodos();
        List<Veiculo> resultado = new ArrayList<>();

        for (Veiculo veiculo : todos) {
            boolean tipoMatch = true;
            if (tipo != null && !tipo.trim().isEmpty()) {
                if (tipo.equalsIgnoreCase("Automovel")) {
                    tipoMatch = veiculo instanceof Automovel;
                } else if (tipo.equalsIgnoreCase("Motocicleta")) {
                    tipoMatch = veiculo instanceof Motocicleta;
                } else if (tipo.equalsIgnoreCase("Van")) {
                    tipoMatch = veiculo instanceof Van;
                } else {
                    tipoMatch = false;
                }
            }

            boolean marcaMatch = (marca == null || veiculo.getMarca() == marca);
            boolean categoriaMatch = (categoria == null || veiculo.getCategoria() == categoria);

            if (tipoMatch && marcaMatch && categoriaMatch) {
                resultado.add(veiculo);
            }
        }

        return resultado;
    }


    private Veiculo instanciarVeiculo(ResultSet rs) throws Exception {
        Marca marca = Marca.valueOf(rs.getString("marca"));
        Categoria cat = Categoria.valueOf(rs.getString("categoria"));
        double valor = rs.getDouble("valor_compra");
        String placa = rs.getString("placa");
        int ano = rs.getInt("ano");
        Estado estado = Estado.valueOf(rs.getString("estado"));
        String tipo = rs.getString("tipo_veiculo");

        Veiculo veiculo = null;

        if ("A".equals(tipo)) {
            ModeloAutomovel modelo = ModeloAutomovel.valueOf(rs.getString("modelo_automovel"));
            veiculo = new Automovel(marca, cat, valor, placa, ano, modelo);
        } else if ("M".equals(tipo)) {
            ModeloMotocicleta modelo = ModeloMotocicleta.valueOf(rs.getString("modelo_motocicleta"));
            veiculo = new Motocicleta(marca, cat, valor, placa, ano, modelo);
        } else if ("V".equals(tipo)) {
            ModeloVan modelo = ModeloVan.valueOf(rs.getString("modelo_van"));
            veiculo = new Van(marca, cat, valor, placa, ano, modelo);
        } else {
            throw new Exception("Tipo de veículo desconhecido no banco: " + tipo);
        }

        if (estado == Estado.LOCADO) {
            Locacao loc = locacaoDAO.buscarLocacaoAtivaPorVeiculo(placa);
            veiculo.setLocacao(loc); 
        }
        
        return veiculo;
    }
}