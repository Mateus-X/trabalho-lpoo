package views;

import controllers.LocacaoController;
import controllers.VeiculoController;
import models.Veiculo;
import views.tables.VeiculoTableModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class DevolverVeiculoView extends JFrame {
    private JTable tblVeiculosLocados;
    private VeiculoTableModel tableModel;
    private JButton btnDevolver;
    private VeiculoController veiculoController;

    public DevolverVeiculoView(LocacaoController locacaoController, VeiculoController veiculoController) {
        super("Devolução de Veículos");
        this.veiculoController = veiculoController;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 500);
        setLocationRelativeTo(null);
        initUI();
        addListeners();
        atualizarTabela();
    }

    private void addListeners() {
        btnDevolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tblVeiculosLocados.getSelectedRow();
                if (row >= 0) {
                    String placa = (String) tblVeiculosLocados.getValueAt(row, 0); // Supondo que placa é a 1ª coluna
                    Veiculo v = veiculoController.buscarVeiculoPorPlaca(placa);
                    if (v != null) {
                        v.devolver();
                        atualizarTabela();
                        JOptionPane.showMessageDialog(null, "Veículo devolvido!");
                    }
                }
            }
        });
    }

    private void atualizarTabela() {
        List<Veiculo> veiculosLocados = veiculoController.listarVeiculosLocados();
        tableModel.setVeiculos(veiculosLocados);
        tableModel.fireTableDataChanged();
    }

    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        mainPanel.add(createHeader(), BorderLayout.NORTH);
        mainPanel.add(createTablePanel(), BorderLayout.CENTER);
        mainPanel.add(createButtonPanel(), BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createHeader() {
        JPanel header = new JPanel();
        header.add(new JLabel("Veículos Locados"));
        return header;
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());

        tableModel = new VeiculoTableModel();
        tblVeiculosLocados = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tblVeiculosLocados);

        tablePanel.add(scrollPane, BorderLayout.CENTER);
        return tablePanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        btnDevolver = new JButton("Devolver Veículo");
        buttonPanel.add(btnDevolver);
        return buttonPanel;
    }

    // Getters
    public JTable getTblVeiculosLocados() { return tblVeiculosLocados; }
    public VeiculoTableModel getTableModel() { return tableModel; }
    public JButton getBtnDevolver() { return btnDevolver; }
}