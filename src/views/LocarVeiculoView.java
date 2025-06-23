package views;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import java.awt.*;
import java.text.NumberFormat;
import controllers.LocacaoController;
import controllers.VeiculoController;
import controllers.ClienteController;
import models.Veiculo;
import models.enums.Categoria;
import models.enums.Marca;
import views.tables.VeiculoTableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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
    private LocacaoController locacaoController;
    private VeiculoController veiculoController;
    
    public LocarVeiculoView(LocacaoController locacaoController, VeiculoController veiculoController, ClienteController clienteController) {
        super("Locacao de Veiculos");
        this.locacaoController = locacaoController;
        this.veiculoController = veiculoController;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 600);
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
    
    private JPanel createHeader() {
        JPanel header = new JPanel();
        header.add(new JLabel("Locacao de Veiculos"));
        return header;
    }
    
    private JPanel createFilterPanel() {
        JPanel filterPanel = new JPanel(new GridLayout(3, 4, 5, 5));
        
        filterPanel.add(new JLabel("Buscar Cliente por CPF:"));
        txtBuscaCliente = new JTextField();
        filterPanel.add(txtBuscaCliente);
        
        filterPanel.add(new JLabel("Tipo de Veiculo:"));
        cbTipoVeiculo = new JComboBox<>(new String[]{"Todos", "Automovel", "Motocicleta", "Van"});
        filterPanel.add(cbTipoVeiculo);
        
        filterPanel.add(new JLabel("Marca:"));
        cbMarca = new JComboBox<>(Marca.values());
        filterPanel.add(cbMarca);
        
        filterPanel.add(new JLabel("Categoria:"));
        cbCategoria = new JComboBox<>(Categoria.values());
        filterPanel.add(cbCategoria);
        
        filterPanel.add(new JLabel("Dias de Locacao:"));
        txtDiasLocacao = new JFormattedTextField(NumberFormat.getIntegerInstance());
        filterPanel.add(txtDiasLocacao);
        
        filterPanel.add(new JLabel("Data de Locacao:"));
        txtDataLocacao = new JFormattedTextField();
        try {
            MaskFormatter dateFormatter = new MaskFormatter("##/##/####");
            txtDataLocacao = new JFormattedTextField(dateFormatter);
        } catch (Exception e) {
            txtDataLocacao = new JFormattedTextField();
        }
        filterPanel.add(txtDataLocacao);
        
        btnLocar = new JButton("Locar Veiculo");
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
    
    private void addListeners() {
        btnLocar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tblVeiculos.getSelectedRow();
                if (row >= 0) {
                    String placa = (String) tblVeiculos.getValueAt(row, 0); // Supondo que placa e a 1ª coluna
                    String cpf = txtBuscaCliente.getText();
                    int dias = 1;
                    try {
                        dias = Integer.parseInt(txtDiasLocacao.getText().replaceAll("[^0-9]", ""));
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Dias de locacao invalido.");
                        return;
                    }
                    Calendar data = Calendar.getInstance();
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        data.setTime(sdf.parse(txtDataLocacao.getText()));
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Data invalida.");
                        return;
                    }
                    locacaoController.locar(cpf, data, placa, dias);
                    atualizarTabela();
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
        tableModel.setVeiculos(veiculos); // Supondo que VeiculoTableModel tem esse metodo
        tableModel.fireTableDataChanged();
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