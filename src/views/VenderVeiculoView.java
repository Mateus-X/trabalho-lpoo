package views;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class VenderVeiculoView extends JFrame {
    private JComboBox<String> cbTipoVeiculo;
    private JComboBox<Marca> cbMarca;
    private JComboBox<Categoria> cbCategoria;
    private JTable tblVeiculos;
    private VeiculoVendaTableModel tableModel;
    private JButton btnVender;
    
    public VenderVeiculoView() {
        super("Venda de Veículos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 500);
        setLocationRelativeTo(null);
        
        initUI();
    }
    
    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        mainPanel.add(createHeader(), BorderLayout.NORTH);
        mainPanel.add(createFilterPanel(), BorderLayout.CENTER);
        mainPanel.add(createTablePanel(), BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private JPanel createHeader() {
        JPanel header = new JPanel();
        header.add(new JLabel("Venda de Veículos"));
        return header;
    }
    
    private JPanel createFilterPanel() {
        JPanel filterPanel = new JPanel(new GridLayout(1, 6, 5, 5));
        
        filterPanel.add(new JLabel("Tipo de Veículo:"));
        cbTipoVeiculo = new JComboBox<>(new String[]{"Todos", "Automóvel", "Motocicleta", "Van"});
        filterPanel.add(cbTipoVeiculo);
        
        filterPanel.add(new JLabel("Marca:"));
        cbMarca = new JComboBox<>(Marca.values());
        filterPanel.add(cbMarca);
        
        filterPanel.add(new JLabel("Categoria:"));
        cbCategoria = new JComboBox<>(Categoria.values());
        filterPanel.add(cbCategoria);
        
        btnVender = new JButton("Vender Veículo");
        filterPanel.add(btnVender);
        
        return filterPanel;
    }
    
    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        
        tableModel = new VeiculoVendaTableModel();
        tblVeiculos = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tblVeiculos);
        
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        return tablePanel;
    }
    
    // Getters
    public JComboBox<String> getCbTipoVeiculo() { return cbTipoVeiculo; }
    public JComboBox<Marca> getCbMarca() { return cbMarca; }
    public JComboBox<Categoria> getCbCategoria() { return cbCategoria; }
    public JTable getTblVeiculos() { return tblVeiculos; }
    public VeiculoVendaTableModel getTableModel() { return tableModel; }
    public JButton getBtnVender() { return btnVender; }
}