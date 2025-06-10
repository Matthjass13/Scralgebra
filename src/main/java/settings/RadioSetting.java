package settings;

import audio.SoundEffect;
import javax.swing.*;
import java.awt.*;
import static util.FileManagement.*;
import static util.FileManagement.setParameter;

public class RadioSetting extends Setting {
    private JRadioButton[] radioBtns;
    private ButtonGroup grp;

    public RadioSetting(String title, String[] options) {
        super();
        this.title=title;
        this.options=options;
        addComponents();
    }

    @Override
    public void addComponents() {
        radioBtns = new JRadioButton[options.length];
        grp = new ButtonGroup();
        for(int i=0; i<radioBtns.length; ++i) {
            createComponent(i);
        }
    }
    @Override
    public void createComponent(int i) {
        radioBtns[i] = new JRadioButton(options[i]);
        radioBtns[i].addActionListener(e -> (new SoundEffect("Button pressed")).play());
        radioBtns[i].setOpaque(false);
        radioBtns[i].setFont(new Font("Tahoma", Font.BOLD, 30));
        if(getParameterAsString(title).equals(options[i]))
            radioBtns[i].setSelected(true);
        grp.add(radioBtns[i]);
        panel.add(radioBtns[i]);
    }
    @Override
    public void updateJSON() {
        setParameter(title, options[getOptionSelected()]);
    }
    public int getOptionSelected() {
        for(int i=0; i<radioBtns.length; ++i) {
            if(radioBtns[i].isSelected()) {
                return i;
            }
        }
        return -1;
    }


}



