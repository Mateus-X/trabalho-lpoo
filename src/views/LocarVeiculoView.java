package views;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.NumberFormat;

public class LocarVeiculoView extends JFrame {
    private JTextField txtBuscaCliente;
    private JComboBox<String> cbTipoVeiculo;
    private JComboBox<Marca> cbMarca;
    private JComboBox<Categoria> cbCategoria;
    private JTable tblVeiculos;
    private VeiculoTableModel tableModel;
    private JFormattedTextField txtDiasLocacao;
    private JFormattedTextField txtDataLocacao;
    private JButton btnLocar;
    
    public LocarVeiculoView() {
        super("Locação de Veículos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 600);
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
        header.add(new JLabel("Locação de Veículos"));
        return header;
    }
    
    private JPanel createFilterPanel() {
        JPanel filterPanel = new JPanel(new GridLayout(3, 4, 5, 5));
        
        filterPanel.add(new JLabel("Buscar Cliente:"));
        txtBuscaCliente = new JTextField();
        filterPanel.add(txtBuscaCliente);
        
        filterPanel.add(new JLabel("Tipo de Veículo:"));
        cbTipoVeiculo = new JComboBox<>(new String[]{"Todos", "Automóvel", "Motocicleta", "Van"});
        filterPanel.add(cbTipoVeiculo);
        
        filterPanel.add(new JLabel("Marca:"));
        cbMarca = new JComboBox<>(Marca.values());
        filterPanel.add(cbMarca);
        
        filterPanel.add(new JLabel("Categoria:"));
        cbCategoria = new JComboBox<>(Categoria.values());
        filterPanel.add(cbCategoria);
        
        filterPanel.add(new JLabel("Dias de Locação:"));
        txtDiasLocacao = new JFormattedTextField(NumberFormat.getIntegerInstance());
        filterPanel.add(txtDiasLocacao);
        
        filterPanel.add(new JLabel("Data de Locação:"));
        txtDataLocacao = new JFormattedTextField();
        try {
            MaskFormatter dateFormatter = new MaskFormatter("##/##/####");
            txtDataLocacao = new JFormattedTextField(dateFormatter);
        } catch (Exception e) {
            txtDataLocacao = new JFormattedTextField();
        }
        filterPanel.add(txtDataLocacao);
        
        btnLocar = new JButton("Locar Veículo");
        filterPanel.add(btnLocar);
        
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
    
    // Getters
    public JTextField getTxtBuscaCliente() { return txtBuscaCliente; }
    public JComboBox<String> getCbTipoVeiculo() { return cbTipoVeiculo; }
    public JComboBox<Marca> getCbMarca() { return cbMarca; }
    public JComboBox<Categoria> getCbCategoria() { return cbCategoria; }
    public JTable getTblVeiculos() { return tblVeiculos; }
    public VeiculoTableModel getTableModel() { return tableModel; }
    public JFormattedTextField getTxtDiasLocacao() { return txtDiasLocacao; }
    public JFormattedTextField getTxtDataLocacao() { return txtDataLocacao; }
    public JButton getBtnLocar() { return btnLocar; }
}