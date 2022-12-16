package Frontend;

import Account.AccountType;
import Person.Customer.Customer;
import Utils.Helpers;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerMenuView extends AbstractJPanel {
    private JButton savingsButton;
    private JButton checkingButton;
    private JButton securitiesButton;
    private JButton loansButton;
    private JPanel basePanel;
    private JButton logOutButton;
    private JButton profileButton;
    private JButton transferButton;

    public CustomerMenuView() {
        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Frontend.getInstance().setUser(null);
                Frontend.getInstance().setUserType(null);
                Frontend.getInstance().back();
            }
        });
        savingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Helpers.openAccountView((Customer) Frontend.getInstance().getUser(), AccountType.SAVING, false);
            }
        });
        checkingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Helpers.openAccountView((Customer) Frontend.getInstance().getUser(), AccountType.CHECKING, false);
            }
        });
        profileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Frontend.getInstance().next(ViewFactory.getCreateEditUserView("edit", Frontend.getInstance().getUserType()));
            }
        });
        loansButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Frontend.getInstance().next(ViewFactory.getLoanView());
            }
        });
        securitiesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Helpers.securityAccountView();
            }
        });
        transferButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Frontend.getInstance().next(ViewFactory.getTransfersPage());
            }
        });
    }



    @Override
    public JPanel getBasePanel() {
        return basePanel;
    }
}
