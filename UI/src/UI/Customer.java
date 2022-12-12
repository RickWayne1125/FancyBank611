package UI;

import javax.swing.*;

public class Customer extends AbstractJPanel{
    private JLabel name;
    private JPanel basePanel;

    public Customer(String name){
        this.name.setText(name);
        this.setVisible(true);
    }

    @Override
    public JPanel getBasePanel() {
        return basePanel;
    }
}
