package view.screens;

import audio.SoundTrack;
import util.FileManagement;
import view.elements.interfaces.Button;
import view.elements.interfaces.TextArea;
import java.awt.*;
import java.util.Objects;
import javax.swing.*;

import static audio.SoundTrack.getTrackInfos;
import static util.FileManagement.getImgIconOf;

public class TitleScreen extends Screen {

	private JPanel btnsPnl;
    private Button creditsBtn;
	private TextArea creditsArea;

	public TitleScreen() {

		super("Mii Channel");
		music.play();

		createLogo();
		createBtnsPnl();
		createCreditsBtn();
		createCreditsArea();

	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			TitleScreen s = new TitleScreen();
			s.setVisible(true);
		});
	}

	public void createLogo() {
		JLabel logo = new JLabel();
		logo.setIcon(getImgIconOf("logo"));
		logo.setBounds(60, 142, 700, 500);
		add(logo);
	}
	public void createBtnsPnl() {

		btnsPnl = new JPanel();
		btnsPnl.setAutoscrolls(true);
		btnsPnl.setBounds(750, 70, 600, 600);
		btnsPnl.setOpaque(false);

		btnsPnl.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 60));
        Button playBtn = new Button("Play", "large", "Green");
		playBtn.addActionListener(e -> changeScreen(new GameScreen()));
		btnsPnl.add(playBtn);

        Button optionsBtn = new Button("Options", "large", "Orange");
		optionsBtn.addActionListener(e -> changeScreen(new OptionsScreen()));
		btnsPnl.add(optionsBtn);

        Button rulesBtn = new Button("Rules", "large", "Red");
		rulesBtn.addActionListener(e -> changeScreen(new RulesScreen()));
		btnsPnl.add(rulesBtn);

		add(btnsPnl);

	}
	public void createCreditsArea() {

		creditsArea = new TextArea("Developer : Matthias Gaillard", 24);
		creditsArea.addParagraph("Many thanks to : ");
		creditsArea.addLine("Hugo Da Silva, Benjamin Crot, Lucas Eugster");
		creditsArea.addParagraph("Soundtracks :");

		String[][] trackInfos = getTrackInfos();
		for(String[] trackInfo : trackInfos) {
			SoundTrack t = new SoundTrack(trackInfo[0]);
			creditsArea.addLine(t.getTextInCredits());
		}

		creditsArea.addParagraph("Sound clips from :");
		creditsArea.addLine("- Super Smash Bros Melee");
		creditsArea.addLine("- Ace Attorney Trilogy");
		creditsArea.addLine("- Jojo's Bizarre Adventure");
		creditsArea.addLine("- Fire Emblem Three Houses");
		creditsArea.setBounds(800, 120, 620, 550);
		creditsArea.setVisible(false);
		add(creditsArea);

	}
	public void createCreditsBtn() {
		creditsBtn = new Button("Credits", "medium", "LightBlue");
		creditsBtn.setBounds(1300, 50, 120, 40);
		creditsBtn.addActionListener(e -> {
			if(creditsBtn.getText().equals("Credits"))
				displayCredits();
			else
				hideCredits();
		});
		add(creditsBtn);
	}
	public void displayCredits() {
		btnsPnl.setVisible(false);
		creditsBtn.setText("Back");
		creditsArea.setVisible(true);
	}
	public void hideCredits() {
		btnsPnl.setVisible(true);
		creditsBtn.setText("Credits");
		creditsArea.setVisible(false);
	}


}