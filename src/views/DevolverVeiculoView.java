package views;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DevolverVeiculoView extends JFrame {
    private JTable tblVeiculosLocados;
    private LocacaoTableModel tableModel;
    private JButton btnDevolver;
    
    public DevolverVeiculoView() {
        super("Devolução de Veículos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 500);
        setLocationRelativeTo(null);
        
        initUI();
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
        
        tableModel = new LocacaoTableModel();
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
    public LocacaoTableModel getTableModel() { return tableModel; }
    public JButton getBtnDevolver() { return btnDevolver; }
}   