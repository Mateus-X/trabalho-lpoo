package views;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private JButton btnClientes;
    private JButton btnAdicionarVeiculo;
    private JButton btnLocarVeiculo;
    private JButton btnDevolverVeiculo;
    private JButton btnVenderVeiculo;
    
    public MainFrame() {
        super("Sistema de Locação de Veículos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        initUI();
    }
    
    private void initUI() {
        // Painel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Adiciona componentes
        mainPanel.add(createHeader(), BorderLayout.NORTH);
        mainPanel.add(createContent(), BorderLayout.CENTER);
        mainPanel.add(createFooter(), BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private JPanel createHeader() {
        JPanel header = new JPanel();
        header.add(new JLabel("Sistema de Locação de Veículos", SwingConstants.CENTER));
        header.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return header;
    }
    
    private JPanel createContent() {
        JPanel content = new JPanel(new GridLayout(5, 1, 10, 10));
        content.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        
        btnClientes = new JButton("Gerenciar Clientes");
        btnAdicionarVeiculo = new JButton("Adicionar Veículo");
        btnLocarVeiculo = new JButton("Locar Veículo");
        btnDevolverVeiculo = new JButton("Devolver Veículo");
        btnVenderVeiculo = new JButton("Vender Veículo");
        
        content.add(btnClientes);
        content.add(btnAdicionarVeiculo);
        content.add(btnLocarVeiculo);
        content.add(btnDevolverVeiculo);
        content.add(btnVenderVeiculo);
        
        return content;
    }
    
    private JPanel createFooter() {
        JPanel footer = new JPanel();
        footer.add(new JLabel("© 2025 - Sistema de Locação de Veículos"));
        footer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return footer;
    }
    
    // Getters para os botões
    public JButton getBtnClientes() { return btnClientes; }
    public JButton getBtnAdicionarVeiculo() { return btnAdicionarVeiculo; }
    public JButton getBtnLocarVeiculo() { return btnLocarVeiculo; }
    public JButton getBtnDevolverVeiculo() { return btnDevolverVeiculo; }
    public JButton getBtnVenderVeiculo() { return btnVenderVeiculo; }
}