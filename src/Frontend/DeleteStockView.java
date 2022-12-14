package Frontend;

import API.Controller;
import Stock.Stock;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteStockView  extends AbstractJPanel {
    private JPanel basePanel;
    private JLabel stockName;
    private JButton deleteButton;
    private JLabel stockValue;

    public DeleteStockView(Stock stock, ManagerStocksView parent) {
        stockName.setText(stock.getStockName());
        stockValue.setText(stock.getCurrentPrice().toString());
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.deleteStock(stock.getStockId());
                parent.refresh();
            }
        });
    }

    @Override
    public JPanel getBasePanel() {
        return basePanel;
    }
}
