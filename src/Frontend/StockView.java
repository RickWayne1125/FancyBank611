package Frontend;

import Stock.Stock;
import Utils.Helpers;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StockView extends AbstractJPanel{
    private JButton buyButton;
    private JTable stockDetail;
    private JPanel basePanel;

    private Stock stock;
    public StockView(Stock stock, SecurityAccountView parent){
        this.stock = stock;
        refresh();
        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Helpers.buyStock(parent.getSecurityAccount(), stock)){
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
                "", "", "", ""
        };
        //actual data for the table in a 2d array
        Object[][] data = new Object[][] {
                {"Name", this.stock.getStockName(), "Price", this.stock.getCurrentPrice()}
        };
        stockDetail.setModel(utils.getTableModel(data, columns));
    }

    @Override
    public JPanel getBasePanel() {
        return basePanel;
    }
}
