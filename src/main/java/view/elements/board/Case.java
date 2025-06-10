package view.elements.board;

import view.elements.interfaces.Label;


import javax.swing.*;
import java.awt.*;

import static util.CustomColor.getColor;
import static util.FileManagement.getImgIconOf;
import static view.elements.board.Piece.*;

public class Case extends JPanel {
    private char character = ' ';
    private JLabel charLbl;
    private JLabel valueLbl;
    private boolean locked = false;
    private JLabel backgroundPiece;
    private String colorName;
    private boolean colored = false;

    public Case(String type) {

        JLabel typeLbl = new Label("", "Small", "Black");

        switch(type) {
            case "42":
                String description = "The player automatically wins if he puts a 42-valued relation on this case.";
            case "69":
            case "∞":
            case "Prime":
            case "Algebraic Closure":
            case "Relation x2":
                typeLbl.setText("RX2");
            case "Relation x3":

                typeLbl.setText("RX3");
            case "Character x2":
                typeLbl.setText("RX2");
                setBackground(getColor(typeLbl.getText()));
                break;
            case "Character x3":
            case "Tor":
            case "Pythagorean's Theorem":
            case "Romanesco Cabbage":
            case "Orthogonal Projection":
            case "π = e":
            case "Orthonormal Reference":
            default:
        }

        createPanel();
    }

    public void createPanel() {

        setPreferredSize(new Dimension(60, 60));
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        setLayout(null);
        charLbl = new JLabel("");
        charLbl.setOpaque(false);
        charLbl.setFont(new Font("Tahoma", Font.BOLD, 30));
        charLbl.setBounds(14, -1, 50 /2, 50);
        add(charLbl);

        valueLbl = new JLabel("");
        valueLbl.setFont(new Font("Tahoma", Font.BOLD, 50 /4));
        valueLbl.setBounds(34, 29, 10, 10);
        add(valueLbl);

        backgroundPiece =new JLabel();
        backgroundPiece.setBounds(1, 1, 47, 47);
        add(backgroundPiece);

    }
    public void clean() {
        if (!locked) {
            character=' ';
            charLbl.setText("");
            valueLbl.setText("");
        }
    }
    public void color(String colorName) {
        this.colorName=colorName;
        String pieceFileName = (colorName.charAt(0)+"").toLowerCase() + colorName.substring(1) + "Piece";
        backgroundPiece.setIcon(getImgIconOf(pieceFileName));
        this.colored=true;
    }
    public boolean hasChar() {
        return character!=' ';
    }
    public char getCharacter() {
        return character;
    }
    public void setCharacter(char c) {
        character=c;
        charLbl.setText(c+"");
        if(c=='%')
            charLbl.setFont(new Font("Tahoma", Font.BOLD, getSmallFontSize()));
        valueLbl.setText(getValue(c)+"");

        if(Character.isDigit(character))
            valueLbl.setVisible(false);



    }
    public JLabel getValueLbl() {
        return valueLbl;
    }
    public boolean isLocked() {
        return locked;
    }
    public void lock() {
        locked=true;
    }
    public String getColorName() {
        return colorName;
    }
    public boolean isColored() {
        return colored;
    }

}
