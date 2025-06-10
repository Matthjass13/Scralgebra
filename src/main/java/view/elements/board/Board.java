package view.elements.board;

import logic.Expression;
import logic.Relation;
import view.elements.players.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Board extends JPanel {

    private final Case[][] cases;
    private final int SIZE = 15;

    public Board() {
        setLayout(new GridLayout(0, SIZE, 0, 0));
        setBackground(Color.BLACK);
        cases = new Case[SIZE][SIZE];
        for (int i = 0; i < getSIZE(); i++) {
            for (int j = 0; j < getSIZE(); j++) {
                cases[i][j] = new Case(determineType(i, j));
                add(cases[i][j]);
            }
        }
    }

    private String determineType(int i, int j) {
        return "Normal";
    }

    public boolean checkFor(Player player) {

        ArrayList<Arrow> arrows = getArrowsToCheck();


        if(arrows.isEmpty()) {
            player.setMessage("Place your pieces !");
            return false;
        }

        for (Arrow arrow : arrows) {
            System.out.println(arrow.toString(this));
            String s = arrow.toString(this);
            Relation r = new Relation(s);
            String message = r.getMessage();
            if(!message.equals("OK")) {
                player.setMessage(message);
                return false;
            }
        }

        return true;

    }
    public Arrow getEqualityArrowToCheck() {
        ArrayList<Arrow> arrows = getArrowsToCheck();
        for (Arrow arrow : arrows) {
            String s = arrow.toString(this);
            if(s.matches(".+[=<>].+"))
                return arrow;
        }
        return null;
    }
    public ArrayList<Arrow> getArrowsToCheck() {
        ArrayList<Arrow> arrows = getArrowsToCheck("right");
        arrows.addAll(getArrowsToCheck("down"));
        return arrows;
    }
    public ArrayList<Arrow> getArrowsToCheck(String direction) {
        ArrayList<Arrow> arrows = new ArrayList<>();
        for(int i=0; i<SIZE; ++i) {
            arrows.addAll(getArrowsToCheck(direction, i));
        }
        return arrows;
    }
    public ArrayList<Arrow> getArrowsToCheck(String direction, int line) {

        ArrayList<Arrow> arrows = new ArrayList<>();

        Arrow a = null;
        boolean arrowInProgress = false;

        if(direction.equals("right")) {
            for (int j = 0; j < getSIZE(); ++j) {
                if(cases[line][j].getCharacter() != ' ') {
                    if (arrowInProgress) {
                        a.incrementLength();
                    } else {
                        a = new Arrow(line, j, direction);
                        arrowInProgress = true;
                    }
                } else {
                    if(arrowInProgress) {
                        if(a.getLength()>1 && containsNewPieces(a)) {
                            arrows.add(a);
                        }
                        a=null;
                        arrowInProgress = false;
                    }
                }
            }
        } else {
            for (int j = 0; j < getSIZE(); ++j) {
                if(cases[j][line].getCharacter() != ' ') {
                    if (arrowInProgress) {
                        a.incrementLength();
                    } else {
                        a = new Arrow(j, line, direction);
                        arrowInProgress = true;
                    }
                } else {
                    if(arrowInProgress) {
                        if(a.getLength()>1 && containsNewPieces(a)) {
                            arrows.add(a);
                        }
                        a=null;
                        arrowInProgress = false;
                    }
                }
            }
        }
        if(arrowInProgress && a.getLength()>1 && containsNewPieces(a)) {
            arrows.add(a);
        }


        return arrows;
    }
    public boolean containsNewPieces(Arrow a) {
        for(Point p : a.getCoordinates()) {
            if(!cases[p.x][p.y].isLocked())
                return true;
        }
        return false;
    }

    public int evaluate(Arrow arrow) {
        if(arrow!=null) {
            String s = arrow.toString(this);
            Relation r = new Relation(s);
            return r.evaluatePieceByPiece();
        }
        return 0;
    }
    public void clean() {
        for(int i = 0; i < getSIZE(); ++i) {
            for(int j = 0; j < getSIZE(); ++j)
                cases[i][j].clean();
        }
    }
    public void lock() {
        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                if(cases[i][j].hasChar())
                    cases[i][j].lock();
            }
        }
    }
    public void addPiece(Piece p, int i, int j) {
        cases[i][j].setCharacter(p.getCharacter());
    }

    public void color(Arrow a, Player p) {
        if(a!=null) {
            Point[] coordinates = a.getCoordinates();
            for(Point point : coordinates) {
                if(cases[point.x][point.y].getCharacter()=='?') {
                    Relation r = new Relation(a.toString(this));
                    char jokerValue = r.getJokerValue();
                    cases[point.x][point.y].setCharacter(jokerValue);
                    break;
                }
            }
            for(Point point : coordinates) {
                cases[point.x][point.y].color(p.getColor());
            }
        }
    }
    public void color(Arrow a, Player p1, Player p2) {
        if(a!=null) {
            Point[] coordinates = a.getCoordinates();
            for(Point point : coordinates) {
                if(cases[point.x][point.y].getCharacter()=='?') {
                    String s = a.toString(this);
                    Relation r = new Relation(s);
                    char jokerValue = r.getJokerValue();
                    cases[point.x][point.y].setCharacter(jokerValue);
                    break;
                }
            }
            for(Point p : coordinates) {
                if(cases[p.x][p.y].isColored() && cases[p.x][p.y].getColorName().equals(p2.getColor())) {
                    int valueThatP2Lose = Integer.parseInt(cases[p.x][p.y].getValueLbl().getText());
                    p2.increaseScore(-1*valueThatP2Lose);
                }
                cases[p.x][p.y].color(p1.getColor());
            }
        }
    }

    public Point[][] getLeftCornerCasesCoordinates() {
        Point[][] coordinates = new Point[SIZE][SIZE];
        for(int i=0; i<SIZE; ++i) {
            for(int j=0; j<SIZE; ++j) {
                coordinates[i][j] = new Point(getX()+i*49, getY()+j*49);
            }
        }
        return coordinates;
    }
    public Point getLeftCornerCasesCoordinates(Point caseCoordinates) {
        return new Point(getX()+caseCoordinates.x*49, getY()+caseCoordinates.y*49);
    }
    public int getSIZE() {
        return SIZE;
    }
    public Case[][] getCases() {
        return cases;
    }


}
