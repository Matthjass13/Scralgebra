package view.elements.interfaces;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

import static util.CustomColor.getColor;

public class TextArea extends JTextArea {

    public TextArea(String s, int size) {

		super(s);

		LineBorder outsideBorder = new LineBorder(new Color(0, 0, 0), 10);
		EmptyBorder insideBorder = new EmptyBorder(10, 10, 10, 10);
		CompoundBorder b = new CompoundBorder(outsideBorder, insideBorder);
		setBorder(b);



		setWrapStyleWord(true);
		setEditable(false);
		setFont(new Font("Tahoma", Font.PLAIN, size));
		setLineWrap(true);

		setBackground(getColor("LightGold"));

	}

	public void addLine(String s) {
		setText(getText()+ "\r\n" + s);
	}
	public void addParagraph(String s) {
		addLine("");
		addLine(s);
	}


}