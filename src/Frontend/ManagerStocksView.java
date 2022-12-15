package Frontend;

import API.Controller;

import Stock.Stock;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ManagerStocksView extends AbstractJPanel{
    private JButton backButton;
    private JTextField stockPriceAmount;
    private JTextField stockName;
    private JPanel stocksField;
    private JButton addStockButton;
    private JPanel basePanel;



    public ManagerStocksView() {
        stocksField.setLayout(new BoxLayout(stocksField, BoxLayout.Y_AXIS));
        stockName.setText("");
        stockPriceAmount.setText("");
        refresh();
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Frontend.getInstance().back();
            }
        });
        addStockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = stockName.getText();
                String amt = stockPriceAmount.getText();
                try {
                    int amount = Integer.parseInt(amt);
                    Controller.addStock(name, amount);
                    refresh();
                } catch (Exception ex){
                    utils.showNotice("Invalid");
                }
            }
        });
    }

    public void refresh(){
        loadStocksField();
    }
    public void loadStocksField(){
        stocksField.removeAll();
        List<Stock> stocks = Controller.getAllStock();
        System.out.println(stocks);
        for(int i = 0; stocks != null && i< stocks.size(); i++){
            stocksField.add((new DeleteStockView(stocks.get(i), this)).getBasePanel());
        }
        stocksField.revalidate();
        Frontend.getInstance().render();
    }

    @Override
    public JPanel getBasePanel() {
        return basePanel;
    }
}
