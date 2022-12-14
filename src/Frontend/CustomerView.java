package Frontend;

import javax.swing.*;

public class CustomerView extends AbstractJPanel{
    private JLabel name;
    private JPanel basePanel;

    public CustomerView(String name){
        this.name.setText(name);
        this.setVisible(true);
    }

    @Override
    public JPanel getBasePanel() {
        return basePanel;
    }
}
