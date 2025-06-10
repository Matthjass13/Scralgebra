package view.screens;

import audio.SoundTrack;
import javax.swing.*;
import static util.CustomColor.getColor;
import static util.FileManagement.getParameterAsString;

/**
 * This class contains the common data needed for all Scralgebra screens.
 * @author Matthias Gaillard
 */

public class Screen extends JFrame {

    protected SoundTrack music;
    protected JPanel musicPnl;

    public Screen(String music) {
        initialize(music);
    }
    public void initialize(String musicName) {

        setBounds(100, 100, 1500, 800);
        getContentPane().setBackground(getColor("BackgroundGreen"));
        getContentPane().setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        this.music = new SoundTrack(musicName);
        if(getParameterAsString("Music").equals("On")) {
            musicPnl = music.getPanel();
            musicPnl.setBounds(1200, 690, 420, 60);
            add(musicPnl);
        }

    }

    /** This method allows the user to go from the current screen to another one,
     * without changing the frame size and location.
     * @param s Screen the user will go to
     */
    public void changeScreen(Screen s) {
        if(music!=null)
            music.stop();
        s.setVisible(true);
        int x = getX();
        int y = getY();
        int width = getWidth();
        int height = getHeight();
        s.setBounds(x, y, width, height);
        dispose();
    }


}