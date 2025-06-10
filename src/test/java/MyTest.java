
import audio.SoundTrack;
import logic.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import static logic.Expression.removeFact;

public class MyTest {
    public static void main(String[] args) {


        Relation r = new Relation("63!=63!");
        print(r.getMessage());

        Expression e = new Expression("10!");
        print(removeFact(e.toString()));
        print(e.simplifyManually());
        print(e.getMessage());



    }




    public static void printHashMap(HashMap<Integer, ArrayList<ArrayList<Integer>>> hashMap) {
        for (int i : hashMap.keySet()) {
            System.out.println("Value = " + i);
            printSolutions(hashMap.get(i));
        }
    }
    public static void printSolutions(ArrayList<ArrayList<Integer>> solutions) {
        System.out.println("Solutions : ");
        for (ArrayList<Integer> solution : solutions) {
            printPositions(solution);
        }
        System.out.println("--------------------");
    }
    public static void printPositions(ArrayList<Integer> positions) {
        System.out.print("Positions : ");
        for (Integer position : positions)
            System.out.print(position + " ");
        System.out.println();
    }
    public static void printStrings(ArrayList<String> list) {
        for (String s : list)
            System.out.println(s);
        System.out.println();
    }
    public static void printIntegers(ArrayList<Integer> list) {
        for (Integer integer : list)
            System.out.print(integer);
        System.out.println();
    }
    public static void print(HashSet<String> list) {
        for(String s : list)
            System.out.println(s);
        System.out.println();
    }

    public static void print(ArrayList<Point> points) {
        for (Point point : points) {
            System.out.println("(" + point.x + "," + point.y + ")");
        }
    }


    public static void print(String s) {
        System.out.println(s);
    }

    public static void printSimplifiedValueOf(String s) {
        Expression e = new Expression(s);
        print("Simplified value of " + s + " is " + e.simplifyManually());
    }

}
