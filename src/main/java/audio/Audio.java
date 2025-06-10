package audio;

import javax.sound.sampled.*;
import java.io.File;

import static util.FileManagement.getFolderPathOf;

public class Audio {

    protected String name;
    protected Clip clip;
    protected String folderPath;
    public Audio(String name) {
        this.name = name;
        folderPath = getFolderPathOf("audio/");
    }

    /**
     * ChatGPT generated
     */
    public void play(float volume, boolean repeat) {
        try {
            String filePath = folderPath + toFileName();
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            if(repeat) {
                clip.addLineListener(event -> {
                    if (event.getType() == LineEvent.Type.STOP) {
                        clip.setMicrosecondPosition(0);
                        clip.start();
                    }
                });
            }
            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volumeControl.setValue(volume);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play(boolean repeat) {
        play(0, repeat);
    }


    public void stop() {
        if (clip != null && clip.isOpen()) {
            clip.stop();
            clip.close();
        }
    }

    /**
     * ChatGPT generated
     */
    public String toFileName() {

        StringBuilder fileName = new StringBuilder();

        boolean capitalizeNext = false;
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);

            if (c == ' ') {
                capitalizeNext = true;
            } else {
                if (capitalizeNext && !fileName.isEmpty()) {
                    fileName.append(Character.toUpperCase(c));
                    capitalizeNext = false;
                } else {
                    fileName.append(Character.toLowerCase(c));
                }
            }
        }

        return fileName +".wav";
    }

}
