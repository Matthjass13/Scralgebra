package view.elements.players;

import java.util.ArrayList;

public class Move {

    protected ArrayList<Integer> piecesIndex;
    private String direction;
    private int row;
    private int col;

    public Move(ArrayList<Integer> piecesIndex, int row, int col, String direction) {
        this.piecesIndex=piecesIndex;
        this.row=row;
        this.col=col;
        this.direction=direction;
    }
    public Move() {
        piecesIndex = new ArrayList<>();
    }

    public ArrayList<Integer> getPiecesIndex() {
        return piecesIndex;
    }
    public void removePiece() {
        piecesIndex.removeFirst();
    }
    public int getRow() {
        return row;
    }
    public void increaseRow() {
        ++row;
    }
    public int getCol() {
        return col;
    }
    public void increaseCol() {
        ++col;
    }
    public String getDirection() {
        return direction;
    }


}
