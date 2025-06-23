package views;

import javax.swing.*;
import java.awt.*;
import controllers.ClienteController;
import controllers.DevolucaoController;
import controllers.VeiculoController;
import controllers.VendaController;
import controllers.LocacaoController;

public class MainFrame extends JFrame {
    private JButton btnClientes;
    private JButton btnAdicionarVeiculo;
    private JButton btnLocarVeiculo;
    private JButton btnDevolverVeiculo;
    private JButton btnVenderVeiculo;
    private ClienteController clienteController;
    private VeiculoController veiculoController;
    private LocacaoController locacaoController;
    private DevolucaoController devolucaoController;
    private VendaController vendaController;

    public MainFrame(ClienteController clienteController, VeiculoController veiculoController,
            LocacaoController locacaoController, DevolucaoController devolucaoController,
            VendaController vendaController) {
        super("Sistema de Locacao de Veiculos");
        this.clienteController = clienteController;
        this.veiculoController = veiculoController;
        this.locacaoController = locacaoController;
        this.devolucaoController = devolucaoController;
        this.vendaController = vendaController;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        initUI();
        addListeners();
    }

    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        mainPanel.add(createHeader(), BorderLayout.NORTH);
        mainPanel.add(createContent(), BorderLayout.CENTER);
        mainPanel.add(createFooter(), BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createHeader() {
        JPanel header = new JPanel();
        header.add(new JLabel("Sistema de Locacao de Veiculos", SwingConstants.CENTER));
        header.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return header;
    }

    private JPanel createContent() {
        JPanel content = new JPanel(new GridLayout(5, 1, 10, 10));
        content.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        btnClientes = new JButton("Gerenciar Clientes");
        btnAdicionarVeiculo = new JButton("Adicionar Veiculo");
        btnLocarVeiculo = new JButton("Locar Veiculo");
        btnDevolverVeiculo = new JButton("Devolver Veiculo");
        btnVenderVeiculo = new JButton("Vender Veiculo");

        content.add(btnClientes);
        content.add(btnAdicionarVeiculo);
        content.add(btnLocarVeiculo);
        content.add(btnDevolverVeiculo);
        content.add(btnVenderVeiculo);

        return content;
    }

    private JPanel createFooter() {
        JPanel footer = new JPanel();
        footer.add(new JLabel("© 2025 - Sistema de Locacao de Veiculos"));
        footer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return footer;
    }

    private void addListeners() {
        btnClientes.addActionListener(e -> {
            ClienteView clienteView = new ClienteView(clienteController);
            clienteView.setVisible(true);
        });
        btnAdicionarVeiculo.addActionListener(e -> {
            AdicionarVeiculoView adicionarVeiculoView = new AdicionarVeiculoView(veiculoController);
            adicionarVeiculoView.setVisible(true);
        });
        btnLocarVeiculo.addActionListener(e -> {
            LocarVeiculoView locarVeiculoView = new LocarVeiculoView(locacaoController, veiculoController,
                    clienteController);
            locarVeiculoView.setVisible(true);
        });
        btnDevolverVeiculo.addActionListener(e -> {
            DevolverVeiculoView devolverVeiculoView = new DevolverVeiculoView(devolucaoController, veiculoController);
            devolverVeiculoView.setVisible(true);
        });
        btnVenderVeiculo.addActionListener(e -> {
            VenderVeiculoView venderVeiculoView = new VenderVeiculoView(veiculoController, vendaController);
            venderVeiculoView.setVisible(true);
        });
    }

    // Getters para os botoes
    public JButton getBtnClientes() {
        return btnClientes;
    }

    public JButton getBtnAdicionarVeiculo() {
        return btnAdicionarVeiculo;
    }

    public JButton getBtnLocarVeiculo() {
        return btnLocarVeiculo;
    }

    public JButton getBtnDevolverVeiculo() {
        return btnDevolverVeiculo;
    }

    public JButton getBtnVenderVeiculo() {
        return btnVenderVeiculo;
    }
}