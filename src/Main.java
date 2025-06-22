import dao.RepositorioMemoria;
import controllers.ClienteController;
import controllers.VeiculoController;
import controllers.LocacaoController;
import views.MainFrame;

// TODO : Trocar tudo que tem acento e c por caracteres sem acento

// TODO : Validar fluxos

public class Main {
    public static void main(String[] args) {
        RepositorioMemoria repositorio = new RepositorioMemoria();
        ClienteController clienteController = new ClienteController(repositorio);
        VeiculoController veiculoController = new VeiculoController(repositorio);
        LocacaoController locacaoController = new LocacaoController(repositorio);

        javax.swing.SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame(clienteController, veiculoController, locacaoController);
            mainFrame.setVisible(true);
        });
    }
}