package views;

import controllers.VeiculoController;
import models.Veiculo;
import models.enums.Marca;
import views.tables.VeiculoTableModel;
import models.enums.Categoria;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class VenderVeiculoView extends JFrame {
    private JComboBox<String> cbTipoVeiculo;
    private JComboBox<Marca> cbMarca;
    private JComboBox<Categoria> cbCategoria;
    private JTable tblVeiculos;
    private VeiculoTableModel tableModel;
    private JButton btnVender;
    private VeiculoController veiculoController;

    public VenderVeiculoView(VeiculoController veiculoController) {
        super("Venda de Veiculos");
        this.veiculoController = veiculoController;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 500);
        setLocationRelativeTo(null);

        initUI();
        addListeners();
        atualizarTabela();
    }

    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        mainPanel.add(createHeader(), BorderLayout.NORTH);
        mainPanel.add(createFilterPanel(), BorderLayout.CENTER);
        mainPanel.add(createTablePanel(), BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void addListeners() {
        btnVender.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tblVeiculos.getSelectedRow();
                if (row >= 0) {
                    String placa = (String) tblVeiculos.getValueAt(row, 0); 
                    Veiculo v = veiculoController.buscarVeiculoPorPlaca(placa);
                    if (v != null) {
                        v.vender();
                        veiculoController.removerVeiculo(placa);
                        atualizarTabela();
                        JOptionPane.showMessageDialog(null, "Veiculo vendido!");
                    }
                }
            }
        });
        cbTipoVeiculo.addActionListener(e -> atualizarTabela());
        cbMarca.addActionListener(e -> atualizarTabela());
        cbCategoria.addActionListener(e -> atualizarTabela());
    }

    private void atualizarTabela() {
        String tipo = (String) cbTipoVeiculo.getSelectedItem();
        if (tipo != null && tipo.equals("Todos")) tipo = null;
        Marca marca = (Marca) cbMarca.getSelectedItem();
        Categoria categoria = (Categoria) cbCategoria.getSelectedItem();
        List<Veiculo> veiculos = veiculoController.filtrarVeiculos(tipo, marca, categoria);
        tableModel.setVeiculos(veiculos);
        tableModel.fireTableDataChanged();
    }

    private JPanel createHeader() {
        JPanel header = new JPanel();
        header.add(new JLabel("Venda de Veiculos"));
        return header;
    }

    private JPanel createFilterPanel() {
        JPanel filterPanel = new JPanel(new GridLayout(1, 6, 5, 5));

        filterPanel.add(new JLabel("Tipo de Veiculo:"));
        cbTipoVeiculo = new JComboBox<>(new String[]{"Todos", "Automovel", "Motocicleta", "Van"});
        filterPanel.add(cbTipoVeiculo);

        filterPanel.add(new JLabel("Marca:"));
        cbMarca = new JComboBox<>(Marca.values());
        filterPanel.add(cbMarca);

        filterPanel.add(new JLabel("Categoria:"));
        cbCategoria = new JComboBox<>(Categoria.values());
        filterPanel.add(cbCategoria);

        btnVender = new JButton("Vender Veiculo");
        filterPanel.add(btnVender);

        return filterPanel;
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());

        tableModel = new VeiculoTableModel();
        tblVeiculos = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tblVeiculos);

        tablePanel.add(scrollPane, BorderLayout.CENTER);
        return tablePanel;
    }

    public JComboBox<String> getCbTipoVeiculo() { return cbTipoVeiculo; }
    public JComboBox<Marca> getCbMarca() { return cbMarca; }
    public JComboBox<Categoria> getCbCategoria() { return cbCategoria; }
    public JTable getTblVeiculos() { return tblVeiculos; }
    public VeiculoTableModel getTableModel() { return tableModel; }
    public JButton getBtnVender() { return btnVender; }
}