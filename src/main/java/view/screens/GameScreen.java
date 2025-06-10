package view.screens;

import audio.SoundEffect;
import audio.SoundTrack;
import view.elements.board.Piece;
import view.elements.players.*;
import view.elements.interfaces.Button;
import view.elements.interfaces.Label;
import view.elements.board.Arrow;
import view.elements.board.Board;
import javax.swing.*;
import java.awt.*;
import static util.FileManagement.getParameter;
import static util.FileManagement.getParameterAsString;

public class GameScreen extends Screen {

	private Board board;
	private final String gameMode = getParameterAsString("Game Mode");
	private boolean versusCPU;
	private int numberOfPlayers;
	private Player[] players;
	private int current;


	private final boolean isTimerEnabled = getParameterAsString("Timer").equals("On");

	private Piece[][] pieces;

    private Button drawBtn;
	private Button changeBtn;
	private Button checkBtn;
    private Button fixBtn;

    private final int MAX_NUMBER_OF_TURNS = getParameter("Turns");
	private int turnNumber=1;
	private Label turnNumberLbl;


	private JPanel victoryMusicPnl;

	private SoundTrack victorySoundTrack = new SoundTrack("Victory Fanfare");


	public GameScreen() {

		super("Puzzle Battle");
		music.play();
		(new SoundEffect("crossExamination")).play();
		(new SoundEffect("gameSet")).play();

		defineSettings();

		create(new Player[numberOfPlayers]);

		int numberOfPieces = 1+getParameter("Operators Per Turn")+getParameter("Digits Per Turn");
		distribute(new Piece[numberOfPlayers][numberOfPieces]);

		for(int i=0; i<numberOfPlayers; ++i)
			add(players[i].getZone());

		createCommonPanel();

		create(new Board());

	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			GameScreen s = new GameScreen();
			s.setVisible(true);
		});
	}

	public void defineSettings() {
		if(gameMode.equals("Solo"))
			numberOfPlayers=1;
		else
			numberOfPlayers=2;

		versusCPU = false;
		if(gameMode.equals("Versus CPU")) {
			versusCPU=true;
            Button cpuPlay = new Button("Auto", "Gray", (SoundEffect) null);
			cpuPlay.addActionListener(e -> {
				((Cpu) players[1]).autoPlay(board, pieces[1], checkBtn, changeBtn);
			});
			add(cpuPlay);
			cpuPlay.setBounds(1320, 640, cpuPlay.getWidth(), cpuPlay.getHeight());
		}

		victoryMusicPnl = victorySoundTrack.getPanel();
		add(victoryMusicPnl);
		victoryMusicPnl.setVisible(false);
		victoryMusicPnl.setBounds(1150, 690, 420, 60);
	}
	public void create(Player[] players) {

		this.players = players;

		current = 0;

		players[0] = new Player(getParameterAsString("Player 1 Name"), getParameterAsString("Player 1 Color"), 0);
		players[0].getZone().setBounds(760, 10, 700, 285);

		if(isTimerEnabled) {
			players[0].getTimer().addActionListener(e -> {
				if(players[0].getTimeRemaining()==0)
					endGame("TimeUp", players[0]);
			});
		}

		if(numberOfPlayers==2) {
			if(versusCPU) {
				players[1]= new Cpu();
			} else {
				String colorP2 = getParameterAsString("Player 2 Color");
				String nameP2 = getParameterAsString("Player 2 Name");
				players[1] = new Player(nameP2, colorP2, 1);
			}
			if(isTimerEnabled) {
				players[1].getTimer().addActionListener(e -> {
					if(players[1].getTimeRemaining()==0)
						endGame("TimeUp", players[1]);
				});
			}

			players[1].stopTimer();
			players[1].getZone().setBounds(players[0].getZone().getX(), 465, players[0].getZone().getWidth(), players[0].getZone().getHeight());
			players[0].setTurnArrowVisible(true);
		}

	}
	public void distribute(Piece[][] pieces) {
		this.pieces = pieces;
		for(int i=0; i<numberOfPlayers; ++i) {
			players[i].drawPieces();
			for(int j=0; j<players[i].getMAX_NUMBER_OF_PIECES(); ++j) {
				pieces[i][j] = new Piece(players[i].getChars()[j], players[i].getColor());
				add(pieces[i][j]);
			}
			players[i].order(pieces[i], false);
		}
	}
	public void create(Board board) {
		this.board = board;
		board.setBounds(10, 10, getHeight()-60, getHeight()-60);
		board.setLayout(new GridLayout(0, 15, 0, 0));
		board.setBackground(Color.BLACK);
		add(board);
	}
	public void createCommonPanel() {

        JPanel commonZone = new JPanel();
		commonZone.setBounds(players[0].getZone().getX()-30, 320, players[0].getZone().getWidth()+60, 120);
		commonZone.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 15));
		commonZone.setOpaque(false);

		drawBtn = new Button("Draw", "Blue",  new SoundEffect("piecesDrawn"));

		drawBtn.addActionListener(e -> {
			for(int i = 0; i<players.length; ++i) {
				players[i].update(pieces[i]);
				players[i].drawPieces();
				players[i].updateLbls(pieces[i]);
				players[i].order(pieces[i], false);
			}
		});
		commonZone.add(drawBtn);

		changeBtn = new Button("Change", "Yellow", new SoundEffect("piecesDrawn"));
		changeBtn.addActionListener(e -> {
			players[current].change(pieces[current]);
			changeCurrentPlayer();
			if(isGameOver())
				endGame(getLooser());
		});
		commonZone.add(changeBtn);

		checkBtn = new Button("Check", "Green", (SoundEffect) null);
		checkBtn.addActionListener(e -> {
			checkBoard();
		});
		commonZone.add(checkBtn);
		fixBtn = new Button("Fix", "Pink", (SoundEffect) null);
		fixBtn.addActionListener(e -> {
			new SoundEffect("crazyDiamond").play(5.0f);
			board.clean();
			players[current].order(pieces[current], false);
			players[current].setMessage("");
		});
		commonZone.add(fixBtn);
        Button resetBtn = new Button("Restart", "Orange");
		resetBtn.addActionListener(e -> {
			victorySoundTrack.stop();
			changeScreen(new GameScreen());
		});
		commonZone.add(resetBtn);
        Button quitBtn = new Button("Quit", "Red");
		quitBtn.addActionListener(e -> {
			victorySoundTrack.stop();
			changeScreen(new TitleScreen());
		});
		commonZone.add(quitBtn);

		turnNumberLbl = new Label("Turn " + turnNumber + "/" + MAX_NUMBER_OF_TURNS);
		commonZone.add(turnNumberLbl);

		add(commonZone);
	}

	public void checkBoard() {

		for(Piece p : pieces[current])
			p.enter(board);

		if(board.checkFor(players[current])) {
			Arrow a = board.getEqualityArrowToCheck();
			int score = board.evaluate(a);
			if(score>0) {
				(new SoundEffect("Correct")).play();
				players[current].increaseScore(score);
			}
			players[current].setMessage("");
			board.lock();
			if(numberOfPlayers==1)
				board.color(a, players[current]);
			else
				board.color(a, players[current], players[(current +1)%2]);
			changeCurrentPlayer();
			drawBtn.doClick();
		} else {
			(new SoundEffect("Wrong")).play();
			players[current].loseLife();
			players[current].setVisible(pieces[current]);
			board.clean();
		}

		if(isGameOver())
			endGame(getLooser());

	}
	public void changeCurrentPlayer() {
		if(players.length>1) {
			players[current].stopTimer();
			players[current].addIncrement();
			current = (current+1)%numberOfPlayers;
			players[current].setTurnArrowVisible(true);
			players[(current+1)%numberOfPlayers].setTurnArrowVisible(false);
			if(current == 0) {
				++turnNumber;
				turnNumberLbl.setText("Turn " + turnNumber + "/" + MAX_NUMBER_OF_TURNS);
			}
			players[current].resumeTimer();
		} else {
			++turnNumber;
			turnNumberLbl.setText("Turn " + turnNumber + "/" + MAX_NUMBER_OF_TURNS);
		}
	}

	public void endGame(String reason, Player looser) {

		players[current].setTurnArrowVisible(false);
		music.stop();
		for(Player p : players) {
			p.setMessage("");
			if(!getParameterAsString("Timer").equals("Off"))
				p.stopTimer();
		}
		drawBtn.setEnabled(false);
		changeBtn.setEnabled(false);
		fixBtn.setEnabled(false);
		checkBtn.setEnabled(false);

        if (reason.equals("TimeUp")) {
            turnNumberLbl.setText("Game ends ! " + looser.getName() + " timed out !");
            (new SoundEffect("timeUp")).play();
        } else {
            if (players.length > 1) {
                if (looser != null) {
					int winnerNumber = (getLooser().getNumber() + 1) % numberOfPlayers;
					turnNumberLbl.setText("Game ends ! Winner is " + players[winnerNumber].getName() + " !");
					music.stop();
					if(getParameterAsString("Music").equals("On")) {
						victorySoundTrack.play(-10.0f, false);
						musicPnl.setVisible(false);
						victoryMusicPnl.setVisible(true);
					}
				}
                else
                    turnNumberLbl.setText("Game ends ! It is a tie !");
            } else {
                if (players[0].hasNoLivesLeft()) {
                    turnNumberLbl.setText("Game ends ! You lost all your lives !");
                } else
                    turnNumberLbl.setText("Game ends ! ");
            }
        }

	}
	public void endGame(Player looser) {
		endGame("", looser);
	}
	public boolean isGameOver() {
        for (Player player : players)
            if (player.hasNoLivesLeft())
				return true;
		return turnNumber>MAX_NUMBER_OF_TURNS;
	}
	public Player getLooser() {
        for (Player player : players)
            if (player.hasNoLivesLeft())
                return player;
		if(players[0].getScore() > players[1].getScore())
			return players[1];
		if (players[0].getScore() < players[1].getScore())
			return players[0];
		return null;
	}
	public void printLeftCorners() {
		Point[][] points = board.getLeftCornerCasesCoordinates();
		for(Point[] points1 : points) {
			for(Point point : points1) {
				System.out.println(point.x + ","+ point.y);
			}
		}
	}


}