package Frontend;

import API.Controller;
import Transact.Transaction;
import Transact.TransactionStatus;
import Transact.TransactionType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

public class TransactionsView extends AbstractJPanel{
    private JButton backButton;
    private JTable transactionsTable;
    private JPanel basePanel;

    public TransactionsView() {
        refresh();
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Frontend.getInstance().back();
            }
        });
    }

    public void refresh(){
        Date date = new Date();
        String dateStr = date.getMonth() + "-" + date.getDate() + "-" + (1900+date.getYear());
        try {
            List<Transaction> transactions = Controller.getTransactionByDate(dateStr);
            System.out.println(dateStr);
            System.out.println(transactions);
            loadTransactionsTable(transactions);
        } catch (Exception e){
            utils.showNotice("ERROR: Please contact support.");
        }

    }

    public void loadTransactionsTable(List<Transaction> transactions){
        transactionsTable.removeAll();
        String[] columns = new String[] {
                "id", "status", "date", "from", "to", "amount"
        };
        Object[][] data = new Object[transactions.size()][6];
        for (int i = 0; i<transactions.size(); i++){
            Transaction transaction = transactions.get(i);
            String from = ""+transaction.getFrom().getAccountNumber();
            String to = ""+transaction.getTo().getAccountNumber();
            double money = transaction.getMoney().getAmount();
            data[transactions.size()-i-1] = new Object[]{transaction.getId(), transaction.getTransactionStatus(),transaction.getDate().toString(), from, to, money };
        }
        transactionsTable.setModel(utils.getTableModel(data, columns));
        transactionsTable.revalidate();
    }

    @Override
    public JPanel getBasePanel() {
        return basePanel;
    }
}
