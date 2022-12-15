package Frontend;

import API.Controller;
import BoughtStock.BoughtStock;
import Utils.Helpers;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BoughtStockView extends AbstractJPanel {
    private JPanel basePanel;
    private JButton sellButton;
    private JTable stockDetail;

    private BoughtStock stock;
    public BoughtStockView(BoughtStock stock, SecurityAccountView parent){
        this.stock = stock;
        refresh();
        sellButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Helpers.sellStock(parent.getSecurityAccount(), stock)){
                    parent.refresh();
                }
            }
        });
    }

    public void refresh(){
        loadStockDetail();
    }

    public void loadStockDetail(){
        stockDetail.removeAll();
        String[] columns = new String[] {
                "", "", "", "", "", ""
        };
        //actual data for the table in a 2d array
        Object[][] data = new Object[][] {
                {"ID", this.stock.getStockId(), "Price", this.stock.getStockPrice(), "Unit", this.stock.getStockUnit()}
        };
        stockDetail.setModel(utils.getTableModel(data, columns));
    }

    @Override
    public JPanel getBasePanel() {
        return basePanel;
    }
}
