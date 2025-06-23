package views.tables;

import models.Veiculo;
import javax.swing.table.AbstractTableModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DevolverVeiculoTableModel extends AbstractTableModel {
    private List<Veiculo> veiculos = new ArrayList<>();
    private final String[] colunas = { "Nome do Cliente", "Placa", "Marca", "Modelo", "Ano", "Data da locacao",
            "Preco da diaria", "Qtde Dias Locados", "Valor da Locacao" };

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
        if (v.getLocacao() == null)
            return null;
        switch (columnIndex) {
            case 0:
                return v.getLocacao().getCliente().getNome();
            case 1:
                return v.getPlaca();
            case 2:
                return v.getMarca();
            case 3:
                return getModeloNome(v);
            case 4:
                return v.getano();
            case 5:
                return v.getLocacao().getDataFormatada();
            case 6:
                return v.getValorDiariaLocacao();
            case 7:
                return v.getLocacao().getdias();
            case 8:
                return v.getLocacao().getValor();
            default:
                return null;
        }
    }

    private String getModeloNome(Veiculo v) {
        try {
            if (v.getClass().getSimpleName().equals("Automovel")) {
                return ((models.Automovel) v).getModelo().name();
            } else if (v.getClass().getSimpleName().equals("Motocicleta")) {
                return ((models.Motocicleta) v).getModelo().name();
            } else if (v.getClass().getSimpleName().equals("Van")) {
                return ((models.Van) v).getModelo().name();
            }
        } catch (Exception e) {
            return "";
        }
        return "";
    }
}
