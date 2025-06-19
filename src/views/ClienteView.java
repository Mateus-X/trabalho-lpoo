package views;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import controllers.ClienteController;
import models.Cliente;
import views.tables.ClienteTableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ClienteView extends JFrame {
    private JTextField txtNome, txtSobrenome, txtRG, txtCPF, txtEndereco;
    private JButton btnAdicionar, btnAtualizar, btnExcluir;
    private JTable tblClientes;
    private ClienteTableModel tableModel;
    private ClienteController clienteController;
    
    public ClienteView(ClienteController clienteController) {
        super("Gerenciamento de Clientes");
        this.clienteController = clienteController;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        initUI();
        addListeners();
        atualizarTabela();
    }
    
    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        mainPanel.add(createHeader(), BorderLayout.NORTH);
        mainPanel.add(createFormPanel(), BorderLayout.CENTER);
        mainPanel.add(createTablePanel(), BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private JPanel createHeader() {
        JPanel header = new JPanel();
        header.add(new JLabel("Cadastro de Clientes"));
        return header;
    }
    
    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        
        formPanel.add(new JLabel("Nome:"));
        txtNome = new JTextField();
        formPanel.add(txtNome);
        
        formPanel.add(new JLabel("Sobrenome:"));
        txtSobrenome = new JTextField();
        formPanel.add(txtSobrenome);
        
        formPanel.add(new JLabel("RG:"));
        txtRG = new JTextField();
        formPanel.add(txtRG);
        
        formPanel.add(new JLabel("CPF:"));
        txtCPF = new JTextField();
        formPanel.add(txtCPF);
        
        formPanel.add(new JLabel("Endereço:"));
        txtEndereco = new JTextField();
        formPanel.add(txtEndereco);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnAdicionar = new JButton("Adicionar");
        btnAtualizar = new JButton("Atualizar");
        btnExcluir = new JButton("Excluir");
        
        buttonPanel.add(btnAdicionar);
        buttonPanel.add(btnAtualizar);
        buttonPanel.add(btnExcluir);
        
        formPanel.add(new JLabel());
        formPanel.add(buttonPanel);
        
        return formPanel;
    }
    
    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        
        tableModel = new ClienteTableModel();
        tblClientes = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tblClientes);
        
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        return tablePanel;
    }
    
    private void addListeners() {
        btnAdicionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clienteController.cadastrarCliente(
                    txtNome.getText(),
                    txtSobrenome.getText(),
                    txtRG.getText(),
                    txtCPF.getText(),
                    txtEndereco.getText()
                );
                atualizarTabela();
            }
        });
        btnAtualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tblClientes.getSelectedRow();
                if (row >= 0) {
                    clienteController.atualizarCliente(
                        txtNome.getText(),
                        txtSobrenome.getText(),
                        txtRG.getText(),
                        txtCPF.getText(),
                        txtEndereco.getText()
                    );
                    atualizarTabela();
                }
            }
        });
        btnExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tblClientes.getSelectedRow();
                if (row >= 0) {
                    String cpf = (String) tblClientes.getValueAt(row, 3); // Supondo que CPF é a 4ª coluna
                    clienteController.excluirCliente(cpf);
                    atualizarTabela();
                }
            }
        });
        tblClientes.getSelectionModel().addListSelectionListener(e -> {
            int row = tblClientes.getSelectedRow();
            if (row >= 0) {
                txtNome.setText((String) tblClientes.getValueAt(row, 0));
                txtSobrenome.setText((String) tblClientes.getValueAt(row, 1));
                txtRG.setText((String) tblClientes.getValueAt(row, 2));
                txtCPF.setText((String) tblClientes.getValueAt(row, 3));
                txtEndereco.setText((String) tblClientes.getValueAt(row, 4));
            }
        });
    }

    private void atualizarTabela() {
        List<Cliente> clientes = clienteController.listarTodosClientes();
        tableModel.setClientes(clientes); // Supondo que ClienteTableModel tem esse método
        tableModel.fireTableDataChanged();
    }
    
    // Getters
    public JTextField getTxtNome() { return txtNome; }
    public JTextField getTxtSobrenome() { return txtSobrenome; }
    public JTextField getTxtRG() { return txtRG; }
    public JTextField getTxtCPF() { return txtCPF; }
    public JTextField getTxtEndereco() { return txtEndereco; }
    public JButton getBtnAdicionar() { return btnAdicionar; }
    public JButton getBtnAtualizar() { return btnAtualizar; }
    public JButton getBtnExcluir() { return btnExcluir; }
    public JTable getTblClientes() { return tblClientes; }
    public ClienteTableModel getTableModel() { return tableModel; }
}