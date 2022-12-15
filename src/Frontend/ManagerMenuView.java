package Frontend;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManagerMenuView extends AbstractJPanel{
    private JButton profileButton;
    private JButton logOutButton;
    private JButton viewCustomersButton;
    private JPanel basePanel;
    private JButton loansButton;
    private JButton stocksButton;
    private JButton viewTransactionsButton;

    public ManagerMenuView() {
        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Frontend.getInstance().back();
            }
        });
        profileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Frontend.getInstance().next(ViewFactory.getCreateEditUserView("edit", Frontend.getInstance().getUserType()));
            }
        });
        viewCustomersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Frontend.getInstance().next(ViewFactory.getCustomersListView());
            }
        });
        stocksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Frontend.getInstance().next(ViewFactory.getManagerStockView());
            }
        });
        viewTransactionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Frontend.getInstance().next(ViewFactory.getTransactionsView());
            }
        });
        loansButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Frontend.getInstance().next(ViewFactory.getLoansView(false, null, true));
            }
        });
    }

    @Override
    public JPanel getBasePanel() {
        return basePanel;
    }
}