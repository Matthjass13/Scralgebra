package util;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import javax.swing.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

public final class FileManagement {

    private FileManagement() {
        throw new UnsupportedOperationException("This class cannot be instanced");
    }

    public static String getFolderPathOf(String name) {
        return "src/main/resources/" + name + "/";
    }
    public static String getJsonFilePath() {
        return getFolderPathOf("json") + "data.json";
    }
    public static ImageIcon getImgIconOf(String name) {
        String filePath= "/img/" + name + ".png";
        ImageIcon imageIcon = new ImageIcon(Objects.requireNonNull(FileManagement.class.getResource(filePath)));
        return imageIcon;
    }


    /** Returns the int value of the parameter in my json file.
     * ChatGPT generated
     */
    public static int getParameter(String parameter) {
        try (FileReader reader = new FileReader(getJsonFilePath())) {
            JsonObject json = new Gson().fromJson(reader, JsonObject.class);
            return json.get(parameter).getAsInt();
        } catch (JsonIOException | JsonSyntaxException e) {
            System.err.println("JSON error : " + e.getMessage());
        } catch (IOException e) {
            System.err.println("JSON read error : " + e.getMessage());
        }
        return 0;
    }

    /** Returns the String value of the parameter in my json file.
     * ChatGPT generated
     */
    public static String getParameterAsString(String parameter) {
        try (FileReader reader = new FileReader(getJsonFilePath())) {
            JsonObject json = new Gson().fromJson(reader, JsonObject.class);
            return json.get(parameter).getAsString();
        } catch (JsonIOException | JsonSyntaxException e) {
            System.err.println("JSON error : " + e.getMessage());
        } catch (IOException e) {
            System.err.println("JSON read error : " + e.getMessage());
        }
        return "";
    }

    /** Sets the parameter of my json file to the int value.
     */
    public static void setParameter(String parameter, int value) {
        setParameter(parameter, value + "");
    }

    /** Sets the parameter of my json file to the String value.
     * ChatGPT generated
     */
    public static void setParameter(String parameter, String value) {
        try {
            JsonObject json;
            try (FileReader reader = new FileReader(getJsonFilePath())) {
                json = new Gson().fromJson(reader, JsonObject.class);
            }
            json.addProperty(parameter, value);
            try (FileWriter writer = new FileWriter(getJsonFilePath())) {
                new Gson().toJson(json, writer);
            }
        } catch (JsonIOException | JsonSyntaxException e) {
            System.err.println("JSON error : " + e.getMessage());
        } catch (IOException e) {
            System.err.println("JSON read / write error : " + e.getMessage());
        }
    }

}