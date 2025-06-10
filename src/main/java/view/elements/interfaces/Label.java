package view.elements.interfaces;

import javax.swing.*;
import java.awt.*;
import static util.CustomColor.getColor;

public class Label extends JLabel {

    public Label(String text, String size, String color) {

        super(text);
        setForeground(getColor(color));
        setOpaque(false);

        int fontSize = switch (size) {
            case "small" -> {
                setSize(200, 30);
                yield 20;
            }
            default -> {
                setSize(300, 50);
                yield 30;
            }
            case "large" -> {
                setSize(400, 70);
                yield 40;
            }
        };
        setFont(new Font("Tahoma", Font.BOLD, fontSize));

    }

    public Label(String text, String color) {
        this(text, "medium", color);
    }
    public Label(String text) {
        this(text, "medium", "Black");
    }


}