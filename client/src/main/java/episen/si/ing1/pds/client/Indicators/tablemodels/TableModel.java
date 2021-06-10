package episen.si.ing1.pds.client.Indicators.tablemodels;

import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.Map;

public class TableModel extends AbstractTableModel {
    private final List<Map> data;

    public TableModel(List<Map> data) {
        this.data = data;
    }

    @Override
    public String getColumnName(int column) {
        String col = getColName(column);
        if(col.equalsIgnoreCase("companyName"))
            return "Entreprise";
        else if(col.equalsIgnoreCase("occupancyRate"))
            return "Taux d'occupation";
        else
            return col;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return data.get(0).size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data.get(rowIndex).get(getColName(columnIndex));
    }

    private String getColName(int column) {
        return (String) data.get(0).keySet().toArray()[column];
    }
}
