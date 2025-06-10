package view.screens;

import settings.CheckBoxSetting;
import settings.TextFieldSetting;
import settings.RadioSetting;
import settings.Setting;
import view.elements.interfaces.Button;

import view.elements.interfaces.Label;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

import static util.CustomColor.getColor;
import static util.FileManagement.getParameterAsString;
import static util.FileManagement.setParameter;

/**
 * This class displays a bunch of options that allow the user to customize game settings.
 * @author Matthias Gaillard
 */
public class OptionsScreen extends Screen {

    private final Setting[] settings;

    public OptionsScreen() {

        super("Beneath the Mask");
        music.play();

        Label title = new Label("Choose your settings !", "large", "Black");
        title.setBounds(40, 10, 600, title.getHeight());
        add(title);

        String[] LABELS = {
                "Player Names",
                "Player 1 Color",
                "Player 2 Color",
                "Game Mode",
                "Turns",
                "Lives",
                "Timer",
                "Operators",
                "Relations",
                "Audio"
        };

        String[][] OPTIONS_LABELS = {
                {getParameterAsString("Player 1 Name"), getParameterAsString("Player 2 Name")},
                {"Red", "Blue", "Yellow", "Green"},
                {"Red", "Blue", "Yellow", "Green"},
                {"Solo", "2-player", "Versus CPU"},
                {"1", "3", "5", "10"},
                {"1", "3", "5", "∞"},
                {"30s", "60s", "120s", "Off"},
                {"+", "-", "x", ":", ".", "%", "(", ")", "!", "?"},
                {"=", "<", ">"},
                {"Music", "Sound Effect"}
        };

        settings = new Setting[LABELS.length];
        JPanel[] panels = new JPanel[LABELS.length];
        Label[] titleLbls = new Label[LABELS.length];

        titleLbls[0]= new Label(LABELS[0]);
        add(titleLbls[0]);
        titleLbls[0].setBounds(70, 120, 300, 68);
        settings[0] = new TextFieldSetting(LABELS[0], OPTIONS_LABELS[0]);
        panels[0] = settings[0].getPanel();
        add(panels[0]);
        panels[0].setBounds(350, 120, 400, 68);

        for (int i = 1; i < 7; ++i) {
            titleLbls[i]= new Label(LABELS[i]);
            add(titleLbls[i]);
            titleLbls[i].setBounds(70, 120+60*i, 300, 68);
            settings[i] = new RadioSetting(LABELS[i], OPTIONS_LABELS[i]);
            panels[i]=settings[i].getPanel();
            add(panels[i]);
            panels[i].setBounds(350, 120+60*i, 850, 68);
        }

        for (int i = 7; i < LABELS.length; ++i) {
            titleLbls[i]= new Label(LABELS[i]);
            add(titleLbls[i]);
            titleLbls[i].setBounds(70, 120+60*i, 300, 68);
            settings[i] = new CheckBoxSetting(LABELS[i], OPTIONS_LABELS[i]);
            panels[i]=settings[i].getPanel();
            add(panels[i]);
            panels[i].setBounds(350, 120+60*i, 850, 68);
        }

        createBtns();

        createOptionsBackground();

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            OptionsScreen s = new OptionsScreen();
            s.setVisible(true);
        });
    }

    public void createBtns() {


        Button resetBtn = new Button("Reset", "Gray");
        resetBtn.addActionListener(e -> {
            resetJSON();
            TitleScreen s = new TitleScreen();
            changeScreen(s);
        });
        resetBtn.setBounds(1220, 630, resetBtn.getWidth(), resetBtn.getHeight());
        add(resetBtn);


        JButton saveBtn = new Button("Save", "Green");
        saveBtn.addActionListener(e -> {
            updateJSON();
            TitleScreen s = new TitleScreen();
            changeScreen(s);
        });
        saveBtn.setBounds(1350, 630, saveBtn.getWidth(), saveBtn.getHeight());
        add(saveBtn);

    }
    public void createOptionsBackground() {
        JPanel optionsArea = new JPanel();
        LineBorder outsideBorder = new LineBorder(new Color(0, 0, 0), 10);
        EmptyBorder insideBorder = new EmptyBorder(10, 10, 10, 10);
        CompoundBorder b = new CompoundBorder(outsideBorder, insideBorder);
        optionsArea.setBorder(b);
        optionsArea.setBounds(50, 100, 1130, 640);
        add(optionsArea);
        optionsArea.setBackground(getColor("LightGold"));
    }
    public void updateJSON() {
        for(Setting s : settings) {
            s.updateJSON();
        }
    }
    public static void resetJSON() {
        setParameter("Player 1 Name", "Alice");
        setParameter("Player 2 Name", "Bob");
        setParameter("Player 1 Color", "Red");
        setParameter("Player 2 Color", "Blue");
        setParameter("Game Mode", "Versus CPU");
        setParameter("Turns", "3");
        setParameter("Lives", "3");
        setParameter("Timer", "Off");
        setParameter("Operators", "+-x:");
        setParameter("Relations", "=");
        setParameter("Sound Effect", "On");
        setParameter("Music", "On");
    }


}