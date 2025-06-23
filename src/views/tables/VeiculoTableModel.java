package views.tables;

import models.Veiculo;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class VeiculoTableModel extends AbstractTableModel {
    private List<Veiculo> veiculos = new ArrayList<>();
    private final String[] colunas = {"Placa", "Marca", "Categoria", "Ano", "Estado", "Valor da diaria"};

    public void setVeiculos(List<Veiculo> veiculos) {
        this.veiculos = veiculos;
    }

    @Override
    public int getRowCount() {
        return veiculos.size();
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
        Veiculo v = veiculos.get(rowIndex);
        switch (columnIndex) {
            case 0: return v.getPlaca();
            case 1: return v.getMarca();
            case 2: return v.getCategoria();
            case 3: return v.getano();
            case 4: return v.getEstado();
            case 5: return v.getValorDiariaLocacao();
            default: return null;
        }
    }
}
