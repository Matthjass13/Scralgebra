package audio;

import view.elements.interfaces.Label;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.swing.*;
import java.awt.*;
import java.io.File;

import static util.FileManagement.*;

public class SoundTrack extends Audio {
    private final String game;
    private final Composer composer;

    public SoundTrack(String name) {
        super(name);
        folderPath+="/soundTracks/";
        game = getGame();
        composer=getComposer();
    }

    public void play() {
        if(getParameterAsString("Music").equals("On")) {
            super.play(-8.0f, true);
        }
    }


    public static String[][] getTrackInfos() {
        String[][] trackInfos = {
                {"Beneath the Mask", "Persona 5", "Rike", "Schmalz"},
                {"Box Has Key", "Baba is You", "Arvi", "Teikari"},
                {"Mii Channel", "Nintendo Wii", "Kazumi", "Totaka"},
                {"Puzzle Battle", "Layton 3", "Tomohito", "Nishiura"},
                {"Victory Fanfare", "Final Fantasy XII", "Nobuo", "Uematsu"},
        };
        return trackInfos;
    }

    public String getGame() {
        String[][] trackInfos = getTrackInfos();
        for (String[] musicInfo : trackInfos) {
            if (name.equals(musicInfo[0]))
                return musicInfo[1];
        }
        return "";
    }

    public Composer getComposer() {
        String[][] trackInfos = getTrackInfos();
        for (String[] musicInfo : trackInfos) {
            if (name.equals(musicInfo[0]))
                return new Composer(musicInfo[2], musicInfo[3]);
        }
        return null;
    }

    public JPanel getPanel() {

        JPanel pnl = new JPanel();
        pnl.setLayout(null);
        pnl.setOpaque(false);

        Label musicIcon = new Label("");
        musicIcon.setBounds(0, 0, 50, 50);
        musicIcon.setIcon(getImgIconOf("musicIcon"));
        pnl.add(musicIcon);

        Label musicLbl = new Label("<html>" + name + " - " + game + "<br>" + composer + "</html>", "small", "Black");
        musicLbl.setFont(new Font("Tahoma", Font.BOLD, 15));
        musicLbl.setBounds(40, 0, 400, 50);
        pnl.add(musicLbl);

        return pnl;

    }

    public String getTextInCredits() {
        return "- " + name + " - " + game + " (" + composer + ")";
    }



    public String getName() {
        return name;
    }


}












