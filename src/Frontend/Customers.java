package Frontend;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Customers extends AbstractJPanel {
    private JButton backButton;
    private JRadioButton balanceRadioButton;
    private JRadioButton loansRadioButton;
    private JPanel basePanel;
    private JPanel customersListPanel;

    private List<JPanel> customers;

    public Customers() {
        customersListPanel.setLayout(new BoxLayout(customersListPanel, BoxLayout.Y_AXIS));
        customers = new ArrayList<>();
        this.getCustomers();
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Frontend.getInstance().back();
            }
        });
    }

    private void getCustomers(){
        customers.clear();
        for (int i = 0; i< 100; i++){
            customers.add((new CustomerView("abc "+i)).getBasePanel());
        }
        this.refresh();
    }
    private void refresh(){
        customersListPanel.removeAll();
        for (JPanel panel:customers){
            customersListPanel.add(panel);
        }
        System.out.println(customersListPanel);
        customersListPanel.revalidate();
        Frontend.getInstance().pack();
    }

    @Override
    public JPanel getBasePanel() {
        return basePanel;
    }
}
