import dao.conf.ConexaoDao;
import dao.ClienteDAO;
import dao.LocacaoDAO;
import dao.VeiculoDAO;

import java.io.OutputStream;
import java.io.PrintStream;
import java.sql.Connection;

import javax.swing.JOptionPane;

import controllers.ClienteController;
import controllers.DevolucaoController;
import controllers.VeiculoController;
import controllers.VendaController;
import controllers.LocacaoController;
import views.MainFrame;

public class Main {
    public static void main(String[] args) {
        Connection connection = ConexaoDao.getConexao();
        ConexaoDao.criarTabelas(connection);

        // Instancia DAOs
        ClienteDAO clienteDAO = new ClienteDAO();
        LocacaoDAO locacaoDAO = new LocacaoDAO(clienteDAO);
        VeiculoDAO veiculoDAO = new VeiculoDAO(locacaoDAO);

        // Instancia controllers com DAOs (injeção de dependência)
        ClienteController clienteController = new ClienteController(clienteDAO);
        VeiculoController veiculoController = new VeiculoController(veiculoDAO);
        LocacaoController locacaoController = new LocacaoController(clienteDAO, veiculoDAO, locacaoDAO);
        VendaController VendaController = new VendaController(veiculoDAO);
        DevolucaoController DevolucaoController = new DevolucaoController(veiculoDAO, locacaoDAO);

        System.setOut(new PrintStream(new OutputStream() {
            private StringBuilder buffer = new StringBuilder();

            @Override
            public void write(int b) {
                if (b == '\n') {
                    JOptionPane.showMessageDialog(null, buffer.toString());
                    buffer.setLength(0);
                } else {
                    buffer.append((char) b);
                }
            }
        }, true));

        javax.swing.SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame(clienteController, veiculoController, locacaoController,
                    DevolucaoController, VendaController);
            mainFrame.setVisible(true);
        });
    }
}