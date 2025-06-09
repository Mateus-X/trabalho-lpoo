package views;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.NumberFormat;
import java.text.ParseException;

public class AdicionarVeiculoView extends JFrame {
    private JComboBox<String> cbTipoVeiculo;
    private JComboBox<Marca> cbMarca;
    private JComboBox<Estado> cbEstado;
    private JComboBox<Categoria> cbCategoria;
    private JComboBox<?> cbModelo;
    private JFormattedTextField txtValorCompra, txtPlaca;
    private JSpinner spAno;
    private JButton btnAdicionar;
    
    public AdicionarVeiculoView() {
        super("Adicionar Veículo");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);
        
        initUI();
    }
    
    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        mainPanel.add(createHeader(), BorderLayout.NORTH);
        mainPanel.add(createFormPanel(), BorderLayout.CENTER);
        mainPanel.add(createButtonPanel(), BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private JPanel createHeader() {
        JPanel header = new JPanel();
        header.add(new JLabel("Cadastro de Veículos"));
        return header;
    }
    
    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridLayout(8, 2, 5, 5));
        
        formPanel.add(new JLabel("Tipo de Veículo:"));
        cbTipoVeiculo = new JComboBox<>(new String[]{"Automóvel", "Motocicleta", "Van"});
        formPanel.add(cbTipoVeiculo);
        
        formPanel.add(new JLabel("Marca:"));
        cbMarca = new JComboBox<>(Marca.values());
        formPanel.add(cbMarca);
        
        formPanel.add(new JLabel("Estado:"));
        cbEstado = new JComboBox<>(Estado.values());
        formPanel.add(cbEstado);
        
        formPanel.add(new JLabel("Categoria:"));
        cbCategoria = new JComboBox<>(Categoria.values());
        formPanel.add(cbCategoria);
        
        formPanel.add(new JLabel("Modelo:"));
        cbModelo = new JComboBox<>();
        formPanel.add(cbModelo);
        
        formPanel.add(new JLabel("Valor de Compra:"));
        txtValorCompra = new JFormattedTextField(NumberFormat.getCurrencyInstance());
        formPanel.add(txtValorCompra);
        
        formPanel.add(new JLabel("Placa:"));
        try {
            MaskFormatter placaFormatter = new MaskFormatter("UUU-####");
            txtPlaca = new JFormattedTextField(placaFormatter);
        } catch (ParseException e) {
            txtPlaca = new JFormattedTextField();
        }
        formPanel.add(txtPlaca);
        
        formPanel.add(new JLabel("Ano:"));
        spAno = new JSpinner(new SpinnerNumberModel(2025, 1900, 2100, 1));
        formPanel.add(spAno);
        
        return formPanel;
    }
    
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        btnAdicionar = new JButton("Adicionar Veículo");
        buttonPanel.add(btnAdicionar);
        return buttonPanel;
    }
    
    // Getters
    public JComboBox<String> getCbTipoVeiculo() { return cbTipoVeiculo; }
    public JComboBox<Marca> getCbMarca() { return cbMarca; }
    public JComboBox<Estado> getCbEstado() { return cbEstado; }
    public JComboBox<Categoria> getCbCategoria() { return cbCategoria; }
    public JComboBox<?> getCbModelo() { return cbModelo; }
    public JFormattedTextField getTxtValorCompra() { return txtValorCompra; }
    public JFormattedTextField getTxtPlaca() { return txtPlaca; }
    public JSpinner getSpAno() { return spAno; }
    public JButton getBtnAdicionar() { return btnAdicionar; }
}