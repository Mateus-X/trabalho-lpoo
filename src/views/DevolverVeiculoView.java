package views;

import controllers.DevolucaoController;
import controllers.VeiculoController;
import models.Veiculo;
import views.tables.DevolverVeiculoTableModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class DevolverVeiculoView extends JFrame {
    private JTable tblVeiculosLocados;
    private DevolverVeiculoTableModel tableModel;
    private JButton btnDevolver;
    private VeiculoController veiculoController;
    private DevolucaoController devolucaoController;

    public DevolverVeiculoView(DevolucaoController devolucaoController, VeiculoController veiculoController) {
        super("Devolucao de Veiculos");
        this.veiculoController = veiculoController;
        this.devolucaoController = devolucaoController;
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
                    String placa = (String) tblVeiculosLocados.getValueAt(row, 1); // Supondo que placa e a 1ª coluna
                    devolucaoController.devolver(placa);
                    atualizarTabela();
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
        header.add(new JLabel("Veiculos Locados"));
        return header;
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());

        tableModel = new DevolverVeiculoTableModel();
        tblVeiculosLocados = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tblVeiculosLocados);

        tablePanel.add(scrollPane, BorderLayout.CENTER);
        return tablePanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        btnDevolver = new JButton("Devolver Veiculo");
        buttonPanel.add(btnDevolver);
        return buttonPanel;
    }

    // Getters
    public JTable getTblVeiculosLocados() {
        return tblVeiculosLocados;
    }

    public DevolverVeiculoTableModel getTableModel() {
        return tableModel;
    }

    public JButton getBtnDevolver() {
        return btnDevolver;
    }
}