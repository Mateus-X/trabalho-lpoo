package views.tables;

import models.Cliente;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class ClienteTableModel extends AbstractTableModel {
    private List<Cliente> clientes = new ArrayList<>();
    private final String[] colunas = {"Nome", "Sobrenome", "RG", "CPF", "Endereco"};

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    @Override
    public int getRowCount() {
        return clientes.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Cliente c = clientes.get(rowIndex);
        switch (columnIndex) {
            case 0: return c.getNome();
            case 1: return c.getSobrenome();
            case 2: return c.getRG();
            case 3: return c.getCPF();
            case 4: return c.getEndereco();
            default: return null;
        }
    }
}
