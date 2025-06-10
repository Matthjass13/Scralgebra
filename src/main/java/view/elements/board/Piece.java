package view.elements.board;

import audio.SoundEffect;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import static util.FileManagement.getImgIconOf;

public class Piece extends JPanel {


    private static int SMALL_FONT_SIZE = 20;

    /**
     * Allows the user to move the pieces freely in the frame.
     * ChatGPT generated
     */
    private static class DraggableMouseListener extends MouseAdapter {
        private final JPanel panel;
        private Point initialClick;
        public DraggableMouseListener(JPanel panel) {
            this.panel = panel;
        }
        @Override
        public void mousePressed(MouseEvent e) {
            initialClick = e.getPoint();
            panel.getComponentAt(initialClick); // Remember the initial click point
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            // Get the location of the window
            int thisX = panel.getLocation().x;
            int thisY = panel.getLocation().y;

            // Determine how much the mouse moved since the initial click
            int xMoved = e.getX() - initialClick.x;
            int yMoved = e.getY() - initialClick.y;

            // Move panel to this position
            int newX = thisX + xMoved;
            int newY = thisY + yMoved;
            panel.setLocation(newX, newY);

        }

        @Override
        public void mouseReleased(MouseEvent e) {

            new SoundEffect("piecePut").play();
            int thisX = panel.getLocation().x;
            int thisY = panel.getLocation().y;

            int newX = 10;
            for(int i = 59; i<700; i+=49) {
                if(Math.abs(thisX-newX)>Math.abs(thisX-i)) {
                    newX = i;
                }
            }
            int newY = 10;
            for(int i = 59; i<700; i+=49) {
                if(Math.abs(thisY-newY)>Math.abs(thisY-i)) {
                    newY = i;
                }
            }


            if(thisX<750&&thisY<750)
                panel.setLocation(newX+1, newY+1);
        }
    }
    private static final int SIZE = 50;
    private final String color;
    private char character;
    private JLabel charLbl;

    private JLabel valueLbl;

    public Piece(char character, String color) {
        this.character = character;
        this.color = color;
        createPanel();
    }

    public static int getValue(char character) {
        return switch(character) {
            case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> Integer.parseInt(character+"");
            case '?' -> 0;
            case '=', '<', '>' -> 1;



            case '+' -> 2;
            case '-' -> 3;
            case 'x' -> 4;
            case '!' -> 5;
            case ':' -> 6;
            case '.' -> 7;
            case '%' -> 8;
            case '(', ')' -> 9;


            default -> 0;
        };
    }
    public void createPanel() {

        charLbl = new JLabel(character+"");
        charLbl.setFont(new Font("Tahoma", Font.BOLD, 30));
        charLbl.setBounds(14, -1, getSIZE() /2, getSIZE());
        if(character=='%')
            charLbl.setFont(new Font("Tahoma", Font.BOLD, SMALL_FONT_SIZE));
        add(charLbl);

        valueLbl = new JLabel(getValue(character) + "");
        valueLbl.setFont(new Font("Tahoma", Font.BOLD, getSIZE() /4));
        valueLbl.setBounds(34, 28, 10, 10);
        add(valueLbl);
        if(Character.isDigit(character))
            valueLbl.setVisible(false);

        JLabel background = new JLabel();
        String pieceFileName = (color.charAt(0)+"").toLowerCase() + color.substring(1) + "Piece";
        background.setIcon(getImgIconOf(pieceFileName));
        background.setBounds(1, 1, 47, 47);
        add(background);

        DraggableMouseListener listener = new DraggableMouseListener(this);
        setBorder(new LineBorder(Color.BLACK, 5));
        setLayout(null);
        addMouseListener(listener);
        addMouseMotionListener(listener);

    }
    public static int getSIZE() {
        return SIZE;
    }
    public String getColor() {
        return color;
    }
    public char getCharacter() {
        return character;
    }
    public void setCharacter(char character) {
        this.character=character;
        charLbl.setText(character+"");
        updatelbl();
    }
    public void updatelbl() {
        valueLbl.setText(getValue(character)+"");
    }




    public void enter(Board board) {
        int x = findPositionIn(board).x;
        int y = findPositionIn(board).y;
        if(x>=0) {
            placeIn(board);
            if(!board.getCases()[y][x].hasChar()) {
                board.addPiece(this, y, x);
                setVisible(false);
            }
        }
    }

    public Point findPositionIn(Board board) {
        Point[][] cornerPoints = board.getLeftCornerCasesCoordinates();
        Point piecePoint = new Point(getX(), getY());
        Point closestPoint = cornerPoints[0][0];
        int row = 0;
        int col = 0;
        for(int i=0; i<cornerPoints.length; ++i) {
            for(int j=0; j<cornerPoints.length; ++j) {
                if(cornerPoints[i][j].distance(piecePoint) < closestPoint.distance(piecePoint)) {
                    closestPoint = cornerPoints[i][j];
                    row=i;
                    col=j;
                }
            }
        }

        if(closestPoint.distance(piecePoint)<=50)
            return new Point(row, col);
        else
            return new Point(-1, -1);
    }

    public void placeIn(Board board) {
        int x = findPositionIn(board).x;
        int y = findPositionIn(board).y;
        Point point = new Point(x, y);
        int cornerX = board.getLeftCornerCasesCoordinates(point).x;
        int cornerY = board.getLeftCornerCasesCoordinates(point).y;
        setLocation(new Point(cornerX, cornerY));
    }


    public static int getSmallFontSize() {
        return SMALL_FONT_SIZE;
    }
}