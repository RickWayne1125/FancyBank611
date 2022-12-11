package UI;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class root extends JFrame {
    private static root r;
    private List<AbstractJPanel> panels;
    private final int WIDTH = 500;
    private final int HEIGHT = 500;

    private root(AbstractJPanel firstPanel){
        panels = new ArrayList<>();
        panels.add(firstPanel);

        this.init();
        this.refresh();
    }

    public static root getRoot(AbstractJPanel firstPanel){
        if (r == null){
            r = new root(firstPanel);
        };
        return r;
    }

    public static root getRoot(){
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
