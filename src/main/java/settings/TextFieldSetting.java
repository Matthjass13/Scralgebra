package settings;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import static util.FileManagement.setParameter;

public class TextFieldSetting extends Setting {

    private JTextField[] textFields;
    private final String[] lbls;

    public TextFieldSetting(String title, String[] lbls) {
        super();
        this.title=title;
        this.lbls=lbls;
        addComponents();
    }

    @Override
    public void addComponents() {
        textFields = new JTextField[lbls.length];
        for(int i=0; i<textFields.length; ++i) {
            createComponent(i);
        }
    }

    @Override
    public void createComponent(int i) {
        textFields[i] = new JTextField(lbls[i]);
        textFields[i].setFont(new Font("Tahoma", Font.BOLD, 30));
        textFields[i].setBorder(new LineBorder(Color.BLACK, 2));
        panel.add(textFields[i]);
    }

    @Override
    public void updateJSON() {
        setParameter("Player 1 Name", getSelection()[0]);
        setParameter("Player 2 Name", getSelection()[1]);
    }

    public String[] getSelection() {
        String[] selection = new String[textFields.length];
        for(int i=0; i<textFields.length; ++i) {
            selection[i]=textFields[i].getText();
        }
        return selection;
    }

}