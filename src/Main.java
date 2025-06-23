import dao.RepositorioMemoria;

import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JOptionPane;

import controllers.ClienteController;
import controllers.VeiculoController;
import controllers.LocacaoController;
import views.MainFrame;

public class Main {
    public static void main(String[] args) {
        RepositorioMemoria repositorio = new RepositorioMemoria();
        ClienteController clienteController = new ClienteController(repositorio);
        VeiculoController veiculoController = new VeiculoController(repositorio);
        LocacaoController locacaoController = new LocacaoController(repositorio);

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
            MainFrame mainFrame = new MainFrame(clienteController, veiculoController, locacaoController);
            mainFrame.setVisible(true);
        });
    }
}