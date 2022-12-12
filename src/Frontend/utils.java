package Frontend;

import javax.swing.table.DefaultTableModel;

public class utils {
    public static DefaultTableModel getTableModel(Object[][] data, String[] columns){
        //create table model with data
        return new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
            @Override
            public Class<?> getColumnClass(int columnIndex)
            {
                return String.class;
            }
        };
    }
}
