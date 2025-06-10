package view.screens;

import view.elements.interfaces.Button;
import view.elements.interfaces.Label;
import view.elements.interfaces.TextArea;
import javax.swing.*;

public class RulesScreen extends Screen {

    private static final int FONT_SIZE = 28;
    private static final String GENERALITIES_FR =
        "Bienvenue dans Scralgebra, le jeu qui lie Scrabble et algèbre !" + "\r\n" + "\r\n" +
        "Au début de la partie, les joueurs reçoivent des pièces de leur couleur. " +
        "À tour de rôle, chacun les déplace sur le plateau de façon à former une égalité mathématique. " +
        "Par exemple, '2+3=5', '3x3=9'... " + "\r\n" + "\r\n" +
                "Puis le joueur clique sur le bouton 'Check'. " +
        "Si l'égalité posée est syntaxiquement et mathématiquement correcte, " +
        "il remporte un nombre de points égal à la somme des valeurs des pièces posées, comme au Scrabble usuel. " +
        "Sinon, il doit réaliser une nouvelle tentative. " + "\r\n" + "\r\n" +
        "Le jeu se finit généralement lorsque le ou les joueurs ont chacun joué un nombre de tour prédéfini. " +
        "Dans ce cas, le joueur avec le plus de points remporte la partie." + "\r\n" + "\r\n";

    private static final String GENERALITIES_EN =
        "Welcome to Scralgebra, the game that links Scrabble and algebra !" + "\r\n" + "\r\n" +
        "First, players receive pieces of their respective color. " +
        "Each player then, turn by turn, moves them on the board to form a mathematical relation. " +
        "For example, '2+3=5', '3x3=9'... " + "\r\n" + "\r\n" +
        "Then, the player clicks on the 'Check' button. " +
        "If the equality is syntactically and mathematically correct, " +
        "he earns a number of points equals to the sum of the values of the pieces, like in usual Scrabble. " +
        "Otherwise, he has to try again. " + "\r\n" + "\r\n" +
        "The game usually ends when the players have played a certain known number of turns. " +
        "In that case, the player with the highest score wins." + "\r\n" + "\r\n";

    private static final String OBSERVATIONS_FR =
        "Remarques :" + "\r\n" + "\r\n" +
        "- Cliquez sur le bouton 'Fix' si vous avez éparpillé vos pièces durant vos réflexions et que voulez les remettre en ordre." + "\r\n" + "\r\n" +
        "Le symbole '%' est le pourcentage." + "\r\n" +
        "- Le symbole '!' représente la factorielle mathématique. " + "\r\n" +
                "  Elle calcule le produit de tous les entiers naturels non nuls inférieurs à un entier donné." + "\r\n" +
                "  Par exemple, 5! = 5 x 4 x 3 x 2 x 1 = 120." + "\r\n" + "\r\n" +
        "- Le symbole '?' est le joker, il peut prendre la valeur de n'importe quelle autre pièce." + "\r\n";
    private static final String OBSERVATIONS_EN =
        "Notes :" + "\r\n" + "\r\n" +
        "- Click on the 'fix' button if you put your pieces everywhere when thinking, and if you want to revert them back to their original position. " + "\r\n" + "\r\n" +
        "The '%' symbol is the percentage." + "\r\n" +
        "- The '!' symbol represents the mathematical factorial. " + "\r\n" +
                "  It computes the product of all natural non zero integers inferior to a given natural n. " + "\r\n" +
                "  For example, 5! = 5 x 4 x 3 x 2 x 1 = 120. " + "\r\n" + "\r\n" +
        "- The '?' symbol is the joker, it can be used as any other piece. " + "\r\n";

    private static final String STEAL_FR =
        "Une particularité de Scralgebra est que vous pouvez voler des points à vos adversaires ! " + "\r\n" + "\r\n" +
        "Pour ce faire, il faut que vous écriviez une égalité en 'croisant', c'est-à-dire en utilisant des pièces déjà placées sur le plateau auparavant. " +
        "Ce faisant, non seulement vous gagnez les points de ces pièces, mais le joueur qui les avaient mises perd ces points-là ! " + "\r\n" + "\r\n" +
        "Les pièces volées changent de couleur en la vôtre. " +
        "En faisant preuve d'astuce, vous pouvez même voler toute une égalité à un joueur. " + "\r\n" + "\r\n" +
        "Tout est permis du moment que les égalités présentes sur le plateau demeurent correctes. ";

    private static final String STEAL_EN =
        "In Scralgebra, you can also steal points from your opponent ! " + "\r\n" + "\r\n" +
        "To do that, you have to place an 'crossing' equality, which means you should use pieces that have already been placed on the board beforehand. " +
        "You will then not only gain the points from these pieces, but the player which they belong to will lose them too ! " + "\r\n" + "\r\n" +
        "Stolen pieces will have their color be changed to yours. " +
        "If you are lucky, you could even steal a whole equality from your opponent. " + "\r\n" + "\r\n" +
        "Everything is allowed as long as the current equalities on the board stay correct. ";

    private static final String OPTIONS_FR =
        "Il est enfin possible de personaliser le jeu dans l'écran des options." + "\r\n" + "\r\n" +
        "Vous pourrez notemment :" + "\r\n" +
        "- Changer le nom et la couleur des joueurs" + "\r\n" +
        "- Choisir le nombre de joueurs ou combattre l'ordinateur (mode 'Versus CPU')" + "\r\n" +
        "- Définir un nombre de vies aux joueurs, qui disparaissent en cas d'erreur : " + "\r\n" +
            "  si un joueur n'a plus de vies, il perd la partie." + "\r\n" +
        "- Définir un temps à disposition pour chaque joueur :" + "\r\n" +
            "  si un joueur n'a plus de temps, il perd également la partie " + "\r\n" +
        "- Choisir les types de pièces qui peuvent apparaître" + "\r\n" +
        "- Activer ou désactiver les musiques et les bruitages." + "\r\n" + "\r\n" +
        "Amusez-vous bien avec Scralgebra !" + "\r\n";

    private static final String OPTIONS_EN =
        "It is also possible to personalize the game in the options screen." + "\r\n" + "\r\n" +
        "You will be able to :" + "\r\n" +
        "- Changer the players names and colors" + "\r\n" +
        "- Choose the number of players or fight the CPU" + "\r\n" +
        "- Define the number of lives each players has, which will disappear in case of a mistake : " + "\r\n" +
        "  were a player to have no lives left, should he lose the game." + "\r\n" +
        "- Define available time for each player :" + "\r\n" +
        "  if a player has no time left, he should also lose the game. " + "\r\n" +
        "- Choose the piece types that can appear" + "\r\n" +
        "- Enable or disable music and sound effects." + "\r\n" + "\r\n" +
        "Have fun with Scralgebra !" + "\r\n";

    private static final String[] TEXTS_FR = {GENERALITIES_FR, OBSERVATIONS_FR, STEAL_FR, OPTIONS_FR};
    private static final String[] TEXTS_EN = {GENERALITIES_EN, OBSERVATIONS_EN, STEAL_EN, OPTIONS_EN};
    private String langage = "English";
    private int currentTextIndex=0;




    public RulesScreen() {

        super("Box Has Key");
        music.play();

        Label title = new Label("Rules of Scralgebra", "large", "Black");
        title.setBounds(40, 10, 1400, title.getHeight());
        add(title);

        TextArea rulesArea = new TextArea(GENERALITIES_EN, FONT_SIZE);
        rulesArea.setBounds(50, 100, 1200, 520);
        add(rulesArea);
        String[][] texts = {
                {GENERALITIES_FR, GENERALITIES_EN},
                {OBSERVATIONS_FR, OBSERVATIONS_EN},
                {STEAL_FR, STEAL_EN},
                {OPTIONS_FR, OPTIONS_EN}
        };



        Button translateBtn = new Button("French", "Violet");
        translateBtn.addActionListener(e -> {
            if(translateBtn.getText().equals("French")) {
                langage = "French";
                translateBtn.setText("English");
            } else {
                langage = "English";
                translateBtn.setText("French");
            }
            translate(rulesArea, langage);
        });
        translateBtn.setSize(150, translateBtn.getHeight());
        translateBtn.setBounds(1300, 100, translateBtn.getWidth(), translateBtn.getHeight());
        add(translateBtn);

        Button previousBtn = new Button("Previous", "Blue");
        Button nextBtn = new Button("Next", "Blue");

        previousBtn.addActionListener(e -> {
            --currentTextIndex;
            translate(rulesArea, langage);
            nextBtn.setVisible(true);
            if(currentTextIndex==0)
                previousBtn.setVisible(false);
        });
        previousBtn.setSize(150, previousBtn.getHeight());
        previousBtn.setBounds(950, 630, previousBtn.getWidth(), previousBtn.getHeight());
        add(previousBtn);
        previousBtn.setVisible(false);

        nextBtn.addActionListener(e -> {
            ++currentTextIndex;
            translate(rulesArea, langage);
            previousBtn.setVisible(true);
            if(currentTextIndex==texts.length-1)
                nextBtn.setVisible(false);
        });
        nextBtn.setBounds(1150, 630, 100, nextBtn.getHeight());
        add(nextBtn);




        Button quitBtn = new Button("Quit", "Red");
        quitBtn.addActionListener(e -> changeScreen(new TitleScreen()));
        quitBtn.setBounds(1300, 630, quitBtn.getWidth(), quitBtn.getHeight());
        add(quitBtn);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RulesScreen s = new RulesScreen();
            s.setVisible(true);
        });
    }


    public void translate(TextArea rulesArea, String langage) {
        switch(langage) {
            case "English":
                rulesArea.setText(TEXTS_EN[currentTextIndex]);
                break;
            case "French":
                rulesArea.setText(TEXTS_FR[currentTextIndex]);
                break;
        }
    }


}
