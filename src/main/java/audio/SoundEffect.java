package audio;

import static util.FileManagement.*;

public class SoundEffect extends Audio {

    public SoundEffect(String name) {
        super(name);
        folderPath+="/soundEffects/";
    }

    public void play() {
        if(getParameterAsString("Sound Effect").equals("On")) {
            super.play(false);
        }
    }

    public void play(boolean repeat) {
        if(getParameterAsString("Sound Effect").equals("On")) {
            super.play(repeat);
        }
    }

    public void play(float volume) {
        if(getParameterAsString("Sound Effect").equals("On")) {
            super.play(volume, false);
        }
    }


}
