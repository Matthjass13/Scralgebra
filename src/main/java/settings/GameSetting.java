package settings;

public final class GameSetting {

    private GameSetting() {
        throw new UnsupportedOperationException("This class cannot be instanced");
    }

    private static String player1Name;
    private static String player1Color;
    private static String player2Name;
    private static String player2Color;
    private static int numberOfTurns;
    private static int numberOfLives;
    private static int piecesPerTurn;
    private static String cpu;
    private static String operators;

}
