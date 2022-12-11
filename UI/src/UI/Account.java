package UI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Account extends AbstractJPanel{
    private JLabel accountLabel;
    private JPanel basePanel;
    private JButton backButton;
    private JComboBox currencyType;
    private JTable accountDetails;
    private JTable transactions;
    private JTextField textField1;
    private JButton depositButton;
    private JTextField textField2;
    private JButton withdrawButton;

    public Account(String accountType){
        accountLabel.setText(accountType);


        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                root.getRoot().back();
            }
        });

        this.loadAccountDetails();
        this.loadTransactionDetails();
    }

    public void loadAccountDetails(){
        String[] columns = new String[] {
                "", ""
        };
        //actual data for the table in a 2d array
        Object[][] data = new Object[][] {
                {"Account Number", 123456789 },
                {"Balance", 100 },
                {"Balance", 100 },
                {"Balance", 100 }
        };
        accountDetails.setModel(utils.getTableModel(data, columns));
    }

    public void loadTransactionDetails(){
        String[] columns = new String[] {
                "id", "date", "credit", "debit", "balance"
        };
        Object[][] data = new Object[][] {
                {"1", "2022-12-01", "100", "0", "12344" },
                {"2", "2022-12-01", "100", "0", "12344" },
                {"3", "2022-12-01", "100", "0", "12344" },
                {"4", "2022-12-01", "100", "0", "12344" },
                {"5", "2022-12-01", "100", "0", "12344" },
                {"6", "2022-12-01", "100", "0", "12344" },
                {"7", "2022-12-01", "100", "0", "12344" },
        };
        transactions.setModel(utils.getTableModel(data, columns));

    }


    @Override
    public JPanel getBasePanel() {
        return basePanel;
    }
}
