package view.elements.players;

import audio.SoundEffect;
import view.elements.board.Piece;
import view.elements.interfaces.Timer;
import view.elements.interfaces.Label;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.Arrays;
import static util.FileManagement.*;
import static view.elements.board.Piece.getSIZE;

public class Player {

    private final String name;
    private final String color;
    private final int number;

    private int score;
    private Label scoreLbl;


    private final int operatorsPerTurn = getParameter("Operators Per Turn");
    private final int digitsPerTurn = getParameter("Digits Per Turn");
    private final int MAX_NUMBER_OF_PIECES = 1 + operatorsPerTurn + digitsPerTurn;
    protected char[] chars = new char[MAX_NUMBER_OF_PIECES];



    protected JPanel zone;
    private final int MAX_NUMBER_OF_LIVES;
    private int lives;
    private Label livesLbl;
    private Timer timer;
    private Label messageLbl;
    private JLabel yourTurnArrow;


    public Player(String name, String color, int number) {
        this.name = name;
        this.color = color;
        score = 0;
        if (getParameterAsString("Lives").equals("∞")) {
            MAX_NUMBER_OF_LIVES = 0;
        } else {
            MAX_NUMBER_OF_LIVES = getParameter("Lives");
        }
        lives = MAX_NUMBER_OF_LIVES;
        this.number = number;
        createPanel();
        addComponents();
        Arrays.fill(chars, ' ');
    }

    public String getName() {
        return name;
    }
    public String getColor() {
        return color;
    }
    public int getNumber() {
        return number;
    }

    public void drawPieces() {

        String operators = getParameterAsString("Operators");
        String relations = getParameterAsString("Relations");
        String digits = "0123456789";

        for (int i = 0; i < chars.length; ++i) {
            if (chars[i] == ' ') {

                if(i==0)

                    chars[i] = relations.charAt((int)(Math.random()*relations.length()));
                else {
                    if(i<=operatorsPerTurn)
                        chars[i] = operators.charAt((int)(Math.random()*operators.length()));
                    else {
                        chars[i] = digits.charAt((int)(Math.random()*digits.length()));
                    }
                }
            }
        }


    }
    public int getMAX_NUMBER_OF_PIECES() {
        return MAX_NUMBER_OF_PIECES;
    }
    public char[] getChars() {
        return chars;
    }
    public String getDigits() {
        StringBuilder digits = new StringBuilder();
        for(int i=6; i<chars.length;++i)
            digits.append(chars[i]);
        return digits.toString();
    }
    public String getOperators() {
        StringBuilder operators = new StringBuilder();
        for(int i=1; i<6;++i)
            operators.append(chars[i]);
        return operators.toString();
    }
    public void setChars(String s) {
        for (int i = 0; i < s.length(); ++i) {
            setChar(i, s.charAt(i));
        }
    }
    public void setChar(int position, char c) {
        chars[position] = c;
    }

    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score=score;
    }
    public void increaseScore(int increment) {
        int step = increment > 0 ? 1 : -1;
        int limit = this.score+increment;

        SoundEffect soundEffect=new SoundEffect("fe17XPGaining");

        final int[] count = {this.score};
        setScore(limit);
        javax.swing.Timer timer = new javax.swing.Timer(40, evt -> {
            count[0]+=step;
            scoreLbl.setText("Score : " + count[0]);
            if (count[0] == limit) {
                ((javax.swing.Timer) evt.getSource()).stop();
                soundEffect.stop();
            }
        });
        timer.start();

        soundEffect.play(5.0f, true);

    }

    public void createPanel() {

        zone = new JPanel();
        zone.setLayout(null);
        zone.setPreferredSize(new Dimension(700, 285));
        zone.setBorder(new LineBorder(Color.BLACK, 3));
        Color backgroundColor = util.CustomColor.getColor("Light" + color);
        zone.setBackground(backgroundColor);

    }
    public void addComponents() {

        Label nameLbl = new Label(name, "large", "Black");
        nameLbl.setLocation(30, 10);
        zone.add(nameLbl);

        yourTurnArrow = new JLabel("Your Turn");
        yourTurnArrow.setIcon(getImgIconOf("yourTurnArrow"));
        yourTurnArrow.setBounds(240, 10, 150, 70);
        yourTurnArrow.setVisible(false);
        zone.add(yourTurnArrow);

        scoreLbl = new view.elements.interfaces.Label("Score : " + score, "large", "Black");
        scoreLbl.setLocation(460, 10);
        zone.add(scoreLbl);

        livesLbl = new view.elements.interfaces.Label("♥ ".repeat(lives), "large", "Black");
        livesLbl.setLocation(30, 50);
        zone.add(livesLbl);

        String timerValue = getParameterAsString("Timer");
        if(!timerValue.equals("Off")) {
            int totalTime = Integer.parseInt(timerValue.substring(0, timerValue.length()-1));
            timer = new Timer(totalTime);
            timer.setBounds(480, 70, 200, 50);
            zone.add(timer);
        }

        messageLbl = new view.elements.interfaces.Label("", "Black");
        messageLbl.setLocation(400, 120);
        zone.add(messageLbl);

    }
    public JPanel getZone() {
        return zone;
    }

    public boolean hasNoLivesLeft() {
        if (MAX_NUMBER_OF_LIVES != 0)
            return lives == 0;
        return false;
    }
    public void loseLife() {
        if (MAX_NUMBER_OF_LIVES != 0)
            setLives(lives - 1);
    }
    public void setLives(int n) {
        if (MAX_NUMBER_OF_LIVES != 0) {
            lives = n;
            if (n > 0) {
                livesLbl.setText("♥ ".repeat(n));
            } else {
                livesLbl.setText("");
                setMessage("No lives left !");
            }
        }
    }

    public javax.swing.Timer getTimer() {
        return timer.getTimer();
    }
    public int getTimeRemaining() {
        return timer.getTimeRemaining();
    }
    public void resumeTimer() {
        if(!getParameterAsString("Timer").equals("Off"))
            timer.resumeTimer();
    }
    public void stopTimer() {
        if(!getParameterAsString("Timer").equals("Off"))
            timer.stopTimer();
    }
    public void addIncrement() {
        if(!getParameterAsString("Timer").equals("Off"))
            timer.addIncrement();
    }

    public void setMessage(String message) {
        messageLbl.setText(message);
    }
    public void setTurnArrowVisible(boolean bool) {
        yourTurnArrow.setVisible(bool);
    }




    // Pieces methods

    public void setVisible(Piece[] pieces) {
        for (Piece piece : pieces) {
            piece.setVisible(true);
        }
    }
    public void order(Piece[] pieces, boolean motion) {
        if(!motion) {
            for(int i=0; i<pieces.length; ++i) {
                move(pieces[i], 800+i/2*getSIZE(), 140+zone.getY()+(i%2)*getSIZE(), motion);
            }
        } else {
            final int[] i = {0};
            move(pieces[i[0]], 800+i[0]/2*getSIZE(),140+zone.getY()+(i[0]%2)*getSIZE(), motion);
        }

    }

    public void move(Piece piece, int finalX, int finalY, boolean motion) {
        if(motion) {
            int step = 10;
            final int[] i = {1};
            javax.swing.Timer timer = new javax.swing.Timer(1000, evt -> {
                piece.setBounds(piece.getX() + (finalX-piece.getX()) / step * i[0],
                        piece.getY() + (finalY-piece.getY()) / step * i[0],
                        getSIZE(),
                        getSIZE());
                piece.revalidate();
                piece.repaint();
                ++i[0];
                if (i[0] == step) {
                    ((javax.swing.Timer) evt.getSource()).stop();
                    new SoundEffect("piecePut").play();
                }
            });
            timer.start();

        }


        piece.setBounds(finalX, finalY, getSIZE(), getSIZE());
        piece.setVisible(true);

    }
    public void update(Piece[] pieces) {
        for(int i=0; i<pieces.length; ++i) {
            if(!pieces[i].isVisible())
                setChar(i, ' ');
        }
    }
    public void updateLbls(Piece[] pieces) {
        for(int i=0; i<pieces.length; ++i) {

            pieces[i].setCharacter(chars[i]);
        }
    }
    public void change(Piece[] pieces) {
        for(int i=0; i<pieces.length; ++i)
            setChar(i, ' ');
        drawPieces();
        order(pieces, false);
        updateLbls(pieces);
    }


}