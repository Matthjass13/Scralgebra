package util;

import java.awt.*;
import java.util.HashMap;

public final class CustomColor {
    private CustomColor() {
        throw new UnsupportedOperationException("This class cannot be instanced");
    }
    public static Color getColor(String name) {
        HashMap<String, Color> colors = getCustomColors();
        for (String i : colors.keySet()) {
            if(i.equals(name))
                return colors.get(i);
        }
        return Color.GRAY;
    }

    public static HashMap<String, Color> getCustomColors() {

        HashMap<String, Color> colors = new HashMap<>();

        colors.put("Red", new Color(255, 99, 71));
        colors.put("LightRed", new Color(255, 153, 102));
        colors.put("Blue", new Color(0, 176, 240));
        colors.put("LightBlue", new Color(153, 204, 255));
        colors.put("Yellow", new Color(255, 255, 0));
        colors.put("LightYellow", new Color(255, 255, 204));
        colors.put("LightOrange", new Color(255, 225, 94));
        colors.put("Green", new Color(154, 205, 50));
        colors.put("LightGreen", new Color(204, 255, 102));
        colors.put("BackgroundGreen", new Color(32, 178, 170));
        colors.put("Orange", new Color(255, 140, 0));
        colors.put("Pink", new Color(251, 111, 146));
        colors.put("LightBrown", new Color(220, 170, 120));
        colors.put("Gray", new Color(178, 178, 178));
        colors.put("LightGray", new Color(221, 221, 221));
        colors.put("Black", new Color(51, 51, 51));
        colors.put("White", new Color(255, 255, 255));
        colors.put("Violet", new Color(228, 102, 255));
        colors.put("LightGold", new Color(251, 236, 171));
        colors.put("RX3", Color.decode("#FF0000"));
        colors.put("RX2", Color.decode("#FFC0CB"));
        colors.put("CX3", Color.decode("#0000FF"));
        colors.put("CX2", Color.decode("#ADD8E6"));
        colors.put("Star", Color.decode("#FFFF00"));

        return colors;
    }

}