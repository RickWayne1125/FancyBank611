package Frontend;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends AbstractJPanel {
    private JButton savingsButton;
    private JButton checkingButton;
    private JButton securitiesButton;
    private JButton loansButton;
    private JPanel basePanel;
    private JButton logOutButton;
    private JButton profileButton;

    public Menu() {
        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Frontend.getInstance().back();
            }
        });
        savingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Frontend.getInstance().next(ViewFactory.getSavingsAccount());
            }
        });
        checkingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Frontend.getInstance().next(ViewFactory.getCheckingAccount());
            }
        });
        profileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Frontend.getInstance().next(ViewFactory.getCustomersListView());
            }
        });
    }

    @Override
    public JPanel getBasePanel() {
        return basePanel;
    }
}
