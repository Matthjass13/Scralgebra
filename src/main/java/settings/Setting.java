package settings;

import javax.swing.*;
import java.awt.*;

public abstract class Setting {

    protected String title;
    protected JPanel panel;
    protected String[] options;

    public Setting() {
        panel = new JPanel();
        panel.setLayout(new GridLayout(1, 0, 10, 0));
        panel.setOpaque(false);
    }

    public void addComponents() {
    }
    public void createComponent(int i) {
    }
    public void updateJSON(){
    }
    public JPanel getPanel() {
        return panel;
    }

}
