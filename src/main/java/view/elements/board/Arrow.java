package view.elements.board;

import java.awt.*;
import java.util.Objects;

public class Arrow {
    private final Point base;
    private final Point direction;
    private int length;

    public Arrow(int x, int y, String direction, int length) {
        base = new Point(x, y);
        if (direction.equals("right"))
            this.direction = new Point(0, 1);
        else
            this.direction = new Point(1, 0);
        this.length=length;
    }
    public Arrow(int x, int y, String direction) {
        this(x, y, direction, 1);
    }

    public int getLength() {
        return length;
    }
    public void incrementLength() {
        ++length;
    }
    public Point[] getCoordinates() {
        Point[] coordinates = new Point[length];
        for(int j = 0; j<length; ++j) {
            coordinates[j] = new Point(base.x+j*direction.x, base.y+j*direction.y);
        }
        return coordinates;
    }
    public String toString(Board board) {
        StringBuilder s = new StringBuilder();
        for(Point p : getCoordinates())
            s.append((board.getCases()[p.x][p.y].getCharacter()));
        return s.toString();
    }

    // Unused methods
    @Override
    public boolean equals(Object o) {
        Arrow a = (Arrow) o;
        return base.equals(a.base) && direction.equals(a.direction) && length==a.length;
    }
    @Override
    public int hashCode() {
        return Objects.hash(base, direction, length);
    }

}