package views;

import controllers.VeiculoController;
import models.Automovel;
import models.Motocicleta;
import models.Van;
import models.enums.Marca;
import models.enums.Estado;
import models.enums.Categoria;
import models.enums.ModeloAutomovel;
import models.enums.ModeloMotocicleta;
import models.enums.ModeloVan;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.ParseException;

public class AdicionarVeiculoView extends JFrame {
    private JComboBox<String> cbTipoVeiculo;
    private JComboBox<Marca> cbMarca;
    private JComboBox<Estado> cbEstado;
    private JComboBox<Categoria> cbCategoria;
    private JComboBox<Object> cbModelo;
    private JFormattedTextField txtValorCompra, txtPlaca;
    private JSpinner spAno;
    private JButton btnAdicionar;
    private VeiculoController veiculoController;

    public AdicionarVeiculoView(VeiculoController veiculoController) {
        super("Adicionar Veículo");
        this.veiculoController = veiculoController;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);
        initUI();
        addListeners();
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

    private void addListeners() {
        cbTipoVeiculo.addActionListener(e -> atualizarModelos());
        btnAdicionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tipo = (String) cbTipoVeiculo.getSelectedItem();
                Marca marca = (Marca) cbMarca.getSelectedItem();
                Categoria categoria = (Categoria) cbCategoria.getSelectedItem();
                double valorCompra = 0.0;
                try {
                    valorCompra = NumberFormat.getCurrencyInstance().parse(txtValorCompra.getText()).doubleValue();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Valor de compra inválido.");
                    return;
                }
                String placa = txtPlaca.getText();
                int ano = (int) spAno.getValue();
                Object modelo = cbModelo.getSelectedItem();
                if (tipo.equals("Automóvel") && modelo instanceof ModeloAutomovel) {
                    veiculoController.cadastrarVeiculo(new Automovel(marca, categoria, valorCompra, placa, ano, (ModeloAutomovel) modelo));
                } else if (tipo.equals("Motocicleta") && modelo instanceof ModeloMotocicleta) {
                    veiculoController.cadastrarVeiculo(new Motocicleta(marca, categoria, valorCompra, placa, ano, (ModeloMotocicleta) modelo));
                } else if (tipo.equals("Van") && modelo instanceof ModeloVan) {
                    veiculoController.cadastrarVeiculo(new Van(marca, categoria, valorCompra, placa, ano, (ModeloVan) modelo));
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione um modelo válido.");
                }
                JOptionPane.showMessageDialog(null, "Veículo cadastrado!");
            }
        });
        atualizarModelos();
    }

    private void atualizarModelos() {
        String tipo = (String) cbTipoVeiculo.getSelectedItem();
        DefaultComboBoxModel<Object> model = new DefaultComboBoxModel<>();
        if (tipo.equals("Automóvel")) {
            for (ModeloAutomovel m : ModeloAutomovel.values()) model.addElement(m);
        } else if (tipo.equals("Motocicleta")) {
            for (ModeloMotocicleta m : ModeloMotocicleta.values()) model.addElement(m);
        } else if (tipo.equals("Van")) {
            for (ModeloVan m : ModeloVan.values()) model.addElement(m);
        }
        cbModelo.setModel(model);
    }

    // Getters
    public JComboBox<String> getCbTipoVeiculo() { return cbTipoVeiculo; }
    public JComboBox<Marca> getCbMarca() { return cbMarca; }
    public JComboBox<Estado> getCbEstado() { return cbEstado; }
    public JComboBox<Categoria> getCbCategoria() { return cbCategoria; }
    public JComboBox<Object> getCbModelo() { return cbModelo; }
    public JFormattedTextField getTxtValorCompra() { return txtValorCompra; }
    public JFormattedTextField getTxtPlaca() { return txtPlaca; }
    public JSpinner getSpAno() { return spAno; }
    public JButton getBtnAdicionar() { return btnAdicionar; }
}