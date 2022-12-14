package Frontend;

import Person.Person;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class Frontend extends JFrame {
    // Singleton class
    private static Frontend r;
    private Person user;
    private String userType;
    private List<AbstractJPanel> panels;
    private final int WIDTH = 500;
    private final int HEIGHT = 500;

    private Frontend(AbstractJPanel firstPanel){
        panels = new ArrayList<>();
        panels.add(firstPanel);

        this.init();
        this.refresh();
    }

    public void setUser(Person user) {
        this.user = user;
    }
    public void setUserType(String userType){
        this.userType = userType;
    }

    public Person getUser() {
        return this.user;
    }
    public String getUserType(){
        return this.userType;
    }
    public static Frontend getInstance(AbstractJPanel firstPanel){
        if (r == null){
            r = new Frontend(firstPanel);
        };
        return r;
    }

    public static Frontend getInstance(){
        return r;
    }

    private void init(){
        this.setLayout(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    private void refresh(){
        int i = panels.size();

        if (i > 1) {
            AbstractJPanel prev = panels.get(i-2);
            prev.setVisible(false);
            this.remove(prev);
        }

        AbstractJPanel panel = panels.get(i-1);

        panel.setVisible(true);
        panel.setSize(this.getSize());
        panel.requestFocus();

        JPanel basePanel = panel.getBasePanel();
        basePanel.setSize(panel.getSize());
        this.setContentPane(basePanel);
        this.pack();
        this.setSize(WIDTH, HEIGHT);
        this.setLocationRelativeTo(null);
    }

    public void back(){
        int i = panels.size();
        if (i != 1) {
            panels.remove(i - 1);
            this.refresh();
        } else {
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }
    }

    public void next(AbstractJPanel nextPanel){
        panels.add(nextPanel);
        this.refresh();
    }
}
