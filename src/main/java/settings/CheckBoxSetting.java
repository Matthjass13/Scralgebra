package settings;

import audio.SoundEffect;
import javax.swing.*;
import java.awt.*;
import static util.FileManagement.*;
import static util.FileManagement.setParameter;

public class CheckBoxSetting extends Setting {
    private JCheckBox[] checkBoxes;

    public CheckBoxSetting(String title, String[] options) {
        super();
        this.title=title;
        this.options=options;
        addComponents();
    }

    @Override
    public void addComponents() {
        checkBoxes = new JCheckBox[options.length];
        for(int i=0; i<options.length; ++i) {
            createComponent(i);
        }
    }

    @Override
    public void createComponent(int i) {

        checkBoxes[i] = new JCheckBox(options[i]);
        checkBoxes[i].addActionListener(e -> (new SoundEffect("Button pressed")).play());
        checkBoxes[i].setOpaque(false);
        checkBoxes[i].setFont(new Font("Tahoma", Font.BOLD, 30));

        switch(title) {
            case "Audio":
                if(getParameterAsString(options[i]).equals("On"))
                    checkBoxes[i].setSelected(true);
                break;
            case "Operators":
            case "Relations":
                if(getParameterAsString(title).contains(options[i]))
                    checkBoxes[i].setSelected(true);
                break;
        }

        panel.add(checkBoxes[i]);

    }

    @Override
    public void updateJSON() {
        switch(title) {
            case "Audio":
                for(int i = 0; i< checkBoxes.length; ++i) {
                    if(checkBoxes[i].isSelected()) {
                        setParameter(options[i], "On");
                    } else {
                        setParameter(options[i], "Off");
                    }
                }
                break;
            case "Operators":
            case "Relations":
                setParameter(title, getOptionsSelected());
                break;
        }
    }

    public String getOptionsSelected() {
        StringBuilder s = new StringBuilder();
        for (JCheckBox checkBox : checkBoxes) {
            if (checkBox.isSelected()) {
                s.append(checkBox.getText());
            }
        }
        return s.toString();
    }


}

