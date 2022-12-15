package Frontend;

import API.Controller;
import Account.Security.SecurityAccount;
import BoughtStock.BoughtStock;
import Person.Customer.Customer;
import Stock.Stock;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SecurityAccountView extends AbstractJPanel{
    private JButton backButton;
    private JPanel basePanel;
    private JTable accountDetails;
    private JTabbedPane tabbedPane1;
    private JPanel allStocksPanel;
    private JPanel ownedStocksPanel;
    private SecurityAccount account;
    private Customer customer;

    public SecurityAccountView(SecurityAccount account) {
        this.customer =  (Customer) Frontend.getInstance().getUser();
        allStocksPanel.setLayout(new java.awt.BorderLayout());
        ownedStocksPanel.setLayout(new java.awt.BorderLayout());

        this.account = account;
        if (this.account == null) {
            utils.showNotice("Error. Please contact support");
        } else {
            this.refresh();
        }

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Frontend.getInstance().back();
            }
        });
    }

    public void refresh(){
        setSecurityAccountDetails();

        List<BoughtStock> ownedStocks = Controller.viewBoughtStock(this.account);
        List<Stock> allStocks = Controller.getAllStock();

        setBoughtStocksPanel(ownedStocks);
        setAllStocksPanel(allStocks);
    }

    public void setSecurityAccountDetails(){
        String[] columns = new String[] {
                "", ""
        };
        //actual data for the table in a 2d array
        Object[][] data = new Object[][] {
                {"Account Number", this.account.getAccountNumber() },
                {"Current Balance", this.account.getCurrentBalance()},
                {"Un-realized profit", this.account.getUnrealized()},
                {"Realized profit", this.account.getRealized()}
            };

        accountDetails.setModel(utils.getTableModel(data, columns));
    }

    public void setBoughtStocksPanel(List<BoughtStock> stocks){
        if(stocks != null) {
            ownedStocksPanel.removeAll();
            for(BoughtStock stock:stocks){
                ownedStocksPanel.add((new BoughtStockView(stock, this)).getBasePanel());
            }
        }
        ownedStocksPanel.revalidate();
    }
    public void setAllStocksPanel(List<Stock> stocks){
        if(stocks != null) {
            allStocksPanel.removeAll();
            for(Stock stock:stocks){
                allStocksPanel.add((new StockView(stock, this)).getBasePanel());
            }
        }
        allStocksPanel.revalidate();
    }

    @Override
    public JPanel getBasePanel() {
        return basePanel;
    }

    public SecurityAccount getSecurityAccount(){
        return this.account;
    }
}
