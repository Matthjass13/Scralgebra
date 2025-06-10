package view.elements.interfaces;

import audio.SoundEffect;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import static util.CustomColor.getColor;

public class Button extends JButton {

    public Button(String text, String size, String color, SoundEffect soundEffect) {

        super(text);
        Color realColor = getColor(color);
        setBackground(realColor);
        int borderThickness;
        int fontSize;

        switch (size) {
            case "small":
                setPreferredSize(new Dimension(200, 60));
                fontSize = 10;
                borderThickness = 3;
                break;
            case "large" :
                setPreferredSize(new Dimension(400, 120));
                fontSize = 80;
                borderThickness = 7;
                break;
            default :
                setSize(100, 50);
                fontSize = 30;
                borderThickness = 3;

        }
        setFont(new Font("Tahoma", Font.BOLD, fontSize));
        setBorder(new LineBorder(Color.BLACK, borderThickness));
        if(soundEffect != null)
            addActionListener(e -> soundEffect.play());

        ToolTipManager.sharedInstance().setInitialDelay(0);
        UIManager.put("ToolTip.font", new Font("Tahoma", Font.BOLD, 14));
        UIManager.put("ToolTip.foreground", new ColorUIResource(Color.BLACK));
        UIManager.put("ToolTip.background", new ColorUIResource(Color.WHITE));
        if(!getToolTipText(text).isEmpty())
            setToolTipText(getToolTipText(text));

    }
    public Button(String text, String size, String color) {
        this(text, size, color, new SoundEffect("Button pressed"));
    }
    public Button(String text, String color, SoundEffect soundEffect) {
        this(text, "medium", color, soundEffect);
    }
    public Button(String text, String color) {
        this(text, "medium", color);
    }

    public static String getToolTipText(String buttonLbl) {
        return switch (buttonLbl) {
            case "Draw" -> "Draw missing pieces";
            case "Change" -> "Change all your pieces and pass your turn";
            case "Check" -> "End turn after checking your equality. If correct, gain points";
            case "Fix" -> "Reset the positions of your current pieces";
            case "Restart" -> "Restart the game";
            case "Quit" -> "Go back to title screen";
            case "Auto" -> "Make CPU play automatically";
            case "Cancel" -> "Don't save settings and quit";
            case "Save" -> "Save settings and play";
            default -> "";
        };
    }


}