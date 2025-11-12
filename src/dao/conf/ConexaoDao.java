package dao.conf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexaoDao {

    private static final String URL = "jdbc:sqlite:locadora.db";
    private static Connection conexao = null;

    // Obtém a conexão (Singleton)
    public static Connection getConexao() {
        try {
            if (conexao == null || conexao.isClosed()) {
                // Carrega o driver
                Class.forName("org.sqlite.JDBC");
                // Cria a conexão
                conexao = DriverManager.getConnection(URL);
                
                // Cria as tabelas na primeira execução
                criarTabelas(conexao);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Erro ao conectar ou criar o banco: " + e.getMessage());
            throw new RuntimeException("Falha na conexao com o banco de dados.", e);
        }
        return conexao;
    }

    // Fecha a conexão
    public static void fecharConexao() {
        try {
            if (conexao != null && !conexao.isClosed()) {
                conexao.close();
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fechar conexao: " + e.getMessage());
        }
    }
    
    // Método para criar as tabelas (DDL)
    private static void criarTabelas(Connection conn) {
        try (Statement stmt = conn.createStatement()) {
            
            // Tabela Cliente
            String sqlCliente = "CREATE TABLE IF NOT EXISTS cliente ("
                              + "cpf TEXT PRIMARY KEY,"
                              + "nome TEXT NOT NULL,"
                              + "sobrenome TEXT NOT NULL,"
                              + "rg TEXT,"
                              + "endereco TEXT"
                              + ");";
            stmt.execute(sqlCliente);

            // Tabela Locacao
            String sqlLocacao = "CREATE TABLE IF NOT EXISTS locacao ("
                              + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                              + "dias INTEGER NOT NULL,"
                              + "valor REAL NOT NULL,"
                              + "data TEXT NOT NULL," // Usaremos TEXT para data (formato ISO)
                              + "cliente_cpf TEXT NOT NULL,"
                              + "FOREIGN KEY (cliente_cpf) REFERENCES cliente(cpf)"
                              + ");";
            stmt.execute(sqlLocacao);

            // Tabela Veiculo
            String sqlVeiculo = "CREATE TABLE IF NOT EXISTS veiculo ("
                              + "placa TEXT PRIMARY KEY,"
                              + "marca TEXT NOT NULL,"
                              + "estado TEXT NOT NULL,"
                              + "categoria TEXT NOT NULL,"
                              + "valor_compra REAL NOT NULL,"
                              + "ano INTEGER NOT NULL,"
                              + "tipo_veiculo TEXT NOT NULL, " // Para polimorfismo (Automovel, Van, etc)
                              + "locacao_id INTEGER," // Chave estrangeira para locacao
                              + "FOREIGN KEY (locacao_id) REFERENCES locacao(id)"
                              + ");";
            stmt.execute(sqlVeiculo);
        
            // Tabelas filhas para herança
            String sqlAutomovel = "CREATE TABLE IF NOT EXISTS automovel ("
                                + "placa TEXT PRIMARY KEY,"
                                + "modelo TEXT NOT NULL,"
                                + "FOREIGN KEY (placa) REFERENCES veiculo(placa) ON DELETE CASCADE"
                                + ");";
            stmt.execute(sqlAutomovel);
            
            String sqlMotocicleta = "CREATE TABLE IF NOT EXISTS motocicleta ("
                                  + "placa TEXT PRIMARY KEY,"
                                  + "modelo TEXT NOT NULL,"
                                  + "FOREIGN KEY (placa) REFERENCES veiculo(placa) ON DELETE CASCADE"
                                  + ");";
            stmt.execute(sqlMotocicleta);

            String sqlVan = "CREATE TABLE IF NOT EXISTS van ("
                          + "placa TEXT PRIMARY KEY,"
                          + "modelo TEXT NOT NULL,"
                          + "FOREIGN KEY (placa) REFERENCES veiculo(placa) ON DELETE CASCADE"
                          + ");";
            stmt.execute(sqlVan);
            
        } catch (SQLException e) {
            System.err.println("Erro ao criar tabelas: " + e.getMessage());
        }
    }
}