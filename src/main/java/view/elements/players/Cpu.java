package view.elements.players;

import audio.SoundEffect;
import logic.Relation;
import view.elements.board.Piece;
import view.elements.board.Board;
import view.elements.interfaces.Button;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static view.elements.board.Piece.getValue;

public class Cpu extends Player {

    private Move moveInMind;

    private Button cpuPlay;

    public Cpu() {
        super("Chat GPT", "Gray", 1);
        moveInMind = new Move();
    }



    public ArrayList<Integer> getPiecesIndexFromSolution(String solution) {
        ArrayList<Integer> piecesIndex = new ArrayList<>();

        boolean[] pieceTaken = new boolean[chars.length];
        Arrays.fill(pieceTaken, false);

        for(int i=0; i<solution.length(); ++i) {
            for(int j=0; j<chars.length; ++j) {
                if(solution.charAt(i)==chars[j] && !pieceTaken[j]) {
                    piecesIndex.add(j);
                    pieceTaken[j]=true;
                    break;
                }
            }
        }

        return piecesIndex;
    }
    public ArrayList<Integer> getPiecesIndexFromSolution() {
        return getPiecesIndexFromSolution(getFinalSolution());
    }

    public String getFinalSolution() {

        ArrayList<String> solutions = removeTrivialSolutions(getSolutions());
        ArrayList<String> top3Solutions = new ArrayList<>();

        String s0 = getRandomSolutionFrom(getSolutionsWithNDigitsNumbers(2, solutions));
        String s1 = getLongestSolution(solutions);
        String s2 = getSolutionWithMostDistinctChars(solutions);

        if(!s0.isEmpty())
            top3Solutions.add(s0);
        top3Solutions.add(s1);
        top3Solutions.add(s2);

        //return top3Solutions.get( (int)(Math.random()*top3Solutions.size()) );

        //return getLongestSolution(solutions);
        return getMostValuableSolution(solutions);
    }


    public String getMostValuableSolution(ArrayList<String> solutions) {
        int maxValue = 0;
        String solution = "";
        for(String s : solutions) {
            int value = new Relation(s).evaluatePieceByPiece();
            if(value>maxValue) {
                maxValue=value;
                solution=s;
            }
        }
        return solution;
    }
    public ArrayList<String> removeTrivialSolutions(ArrayList<String> solutions) {
        ArrayList<String> newSolutions = new ArrayList<>();
        for(String s : solutions) {
            if(!s.matches("[0-9]{1,2}=[0-9]{1,2}"))
                newSolutions.add(s);
        }
        return newSolutions;
    }
    public String getRandomSolutionFrom(ArrayList<String> solutions) {
        int n = (int) (Math.random()*solutions.size());
        if(!solutions.isEmpty())
            return solutions.get(n);
        return "";
    }
    public ArrayList<String> getSolutionsWithNDigitsNumbers(int n, ArrayList<String> solutions) {
        Pattern pattern = Pattern.compile("[0-9]{"+n+"}");
        ArrayList<String> newSolutions = new ArrayList<>();
        for(String s : solutions) {
            Matcher matcher = pattern.matcher(s);
            if(matcher.find())
                newSolutions.add(s);
        }
        return newSolutions;
    }
    public String getLongestSolution(ArrayList<String> solutions) {
        String solution = "";
        for(String s : solutions) {
            if(s.length()>solution.length())
                solution=s;
        }
        return solution;
    }
    public String getSolutionWithMostDistinctChars(ArrayList<String> solutions) {
        String solution = "";
        for(String s : solutions) {
            if(countDistinctChars(s)>countDistinctChars(solution))
                solution=s;
        }
        return solution;
    }
    public int countDistinctChars(String s) {
        HashSet<Character> chars = new HashSet<>();
        for(int i=0; i<s.length(); ++i)
            chars.add(s.charAt(i));
        return chars.size();
    }

    public ArrayList<String> getSolutions() {
        HashMap<Integer, ArrayList<ArrayList<Integer>>> hashMap = getHashMap();
        HashSet<String> finalSolutions = new HashSet<>();
        for (int value : hashMap.keySet()) {
            int numberOfSolutions = hashMap.get(value).size();
            for(int j=0; j<numberOfSolutions;++j) {
                for(int k=j+1; k<numberOfSolutions;++k) {
                    if(areCompatible(hashMap.get(value).get(j), hashMap.get(value).get(k))) {
                        String solution = buildEquality(hashMap.get(value).get(j), hashMap.get(value).get(k));
                        finalSolutions.add(solution);
                    }
                }
            }
        }
        return new ArrayList<>(finalSolutions);
    }
    public boolean areCompatible(ArrayList<Integer> list1, ArrayList<Integer> list2) {
        HashSet<Integer> set = new HashSet<>(list1);
        for (Integer num : list2)
            if (set.contains(num))
                return false;
        return true;
    }
    public String buildEquality(ArrayList<Integer> positions, ArrayList<Integer> positions2) {
        return buildExpression(positions) + "=" + buildExpression(positions2);
    }
    public String buildExpression(ArrayList<Integer> positions) {
        StringBuilder exp = new StringBuilder();
        for (Integer position : positions)
            exp.append(chars[position]);
        return exp.toString();
    }
    public HashMap<Integer, ArrayList<ArrayList<Integer>>> getHashMap() {

        String digits = getDigits();
        String operators = getOperators();
        HashMap<Integer, ArrayList<ArrayList<Integer>>> hashMap = new HashMap<>();
        int valueToAdd;

        for(int i=0; i<digits.length(); ++i) {

            int iToInt = Integer.parseInt(digits.charAt(i) + "");

            valueToAdd = iToInt;

            ArrayList<ArrayList<Integer>> solutions = new ArrayList<>();
            ArrayList<Integer> positions = new ArrayList<>();
            positions.add(i + operators.length()+1);
            solutions.add(positions);

            if (hashMap.containsKey(valueToAdd))
                hashMap.get(valueToAdd).add(positions);
            else
                hashMap.put(valueToAdd, solutions);

            for(int j=0;j<digits.length(); ++j) {
                if(i!=j) {

                    int jToInt = Integer.parseInt(digits.charAt(j) + "");
                    if(iToInt!=0) {

                        valueToAdd = 10*iToInt+jToInt;

                        ArrayList<ArrayList<Integer>> solutions2 = new ArrayList<>();
                        ArrayList<Integer> positions2 = new ArrayList<>();
                        positions2.add(i + operators.length()+1);
                        positions2.add(j + operators.length()+1);
                        solutions2.add(positions2);

                        if (hashMap.containsKey(valueToAdd))
                            hashMap.get(valueToAdd).add(positions2);
                        else
                            hashMap.put(valueToAdd, solutions2);
                    }

                    for(int k=0; k<operators.length(); ++k) {

                        ArrayList<ArrayList<Integer>> solutions3 = new ArrayList<>();
                        ArrayList<Integer> positions3 = new ArrayList<>();

                        String binaries = "+-x:";
                        if(binaries.contains(operators.charAt(k)+"")) {

                            if(operators.charAt(k)==':') {
                                if(digits.charAt(j)=='0') {
                                    continue;
                                } else {
                                    if(iToInt % jToInt != 0) {
                                        continue;
                                    }
                                }
                            }

                            valueToAdd = evaluateBinaryOperation(iToInt, operators.charAt(k), jToInt);
                            positions3.add(i + operators.length()+1);
                            positions3.add(k+1);
                            positions3.add(j + operators.length()+1);
                            solutions3.add(positions3);

                        } else {
                            if(operators.charAt(k)=='!') {
                                valueToAdd = fact(iToInt);
                                positions3.add(i + operators.length()+1);
                                positions3.add(k+1);
                                solutions3.add(positions3);
                            }
                        }

                        if(!solutions3.isEmpty()) {
                            if (hashMap.containsKey(valueToAdd))
                                hashMap.get(valueToAdd).add(positions3);
                            else
                                hashMap.put(valueToAdd, solutions3);
                        }


                        for(int m=0; m<digits.length(); ++m) {
                            if(m!=i && m!=j && iToInt!=0) {

                                int mToInt = Integer.parseInt(digits.charAt(m) + "");


                                ArrayList<ArrayList<Integer>> solutions4 = new ArrayList<>();
                                ArrayList<Integer> positions4 = new ArrayList<>();

                                String binaries2 = "+-x:";
                                if(binaries2.contains(operators.charAt(k)+"")) {

                                    if(operators.charAt(k)==':') {
                                        if(digits.charAt(m)=='0')
                                            continue;
                                        else {
                                            if((10*iToInt+jToInt) % mToInt != 0)
                                                continue;
                                        }
                                    }

                                    valueToAdd = evaluateBinaryOperation(
                                        10*iToInt+jToInt,
                                        operators.charAt(k),
                                        mToInt
                                    );
                                    positions4.add(i + operators.length()+1);
                                    positions4.add(j + operators.length()+1);
                                    positions4.add(k+1);
                                    positions4.add(m + operators.length()+1);
                                    solutions4.add(positions4);

                                }

                                if(!solutions4.isEmpty()) {
                                    if (hashMap.containsKey(valueToAdd))
                                        hashMap.get(valueToAdd).add(positions4);
                                    else
                                        hashMap.put(valueToAdd, solutions4);
                                }
                            }
                        }
                    }
                }
            }
        }

        return hashMap;
    }
    public int evaluateBinaryOperation(int leftOperand, char operator, int rightOperand) {
        return switch (operator) {
            case '+' -> leftOperand + rightOperand;
            case 'x' -> leftOperand * rightOperand;
            case '-' -> leftOperand - rightOperand;
            case ':' -> leftOperand / rightOperand;
            default -> 0;
        };
    }
    public int fact(int i) {
        if(i==0)
            return 1;
        else return i*fact(i-1);
    }

    public void autoPlay(Board board, Piece[] pieces, Button checkBtn, Button changeBtn) {

        String solution = getFinalSolution();

        if(!solution.isEmpty()) {
            ArrayList<Integer> piecesIndex = getPiecesIndexFromSolution(solution);
            String[] directions = {"right", "down"};
            String direction = directions[(int) (Math.random() * 2)];
            Point startPoint;
            if (direction.equals("right"))
                startPoint = findRandomStartOfRowLine(piecesIndex.size(), board);
            else
                startPoint = findRandomStartOfColLine(piecesIndex.size(), board);

            thinkOfMove(piecesIndex, startPoint, direction);

            javax.swing.Timer timer = new javax.swing.Timer(200, evt -> {
                if (getPiecesIndexInMind().isEmpty()) {
                    ((javax.swing.Timer) evt.getSource()).stop();
                    checkBtn.doClick();
                } else {
                    placePiece(board, pieces);
                    new SoundEffect("piecePut").play();
                }
            });

            timer.start();
        } else {
            changeBtn.doClick();
        }

    }
    public Point findRandomStartOfRowLine(int length, Board board) {
        ArrayList<Point> points = findStartsOfEmptyRowLine(length, board);
        int index = (int) (Math.random()*points.size());
        return points.get(index);
    }
    public Point findRandomStartOfColLine(int length, Board board) {
        ArrayList<Point> points = findStartsOfEmptyColLine(length, board);
        int index = (int) (Math.random()*points.size());
        return points.get(index);
    }
    public ArrayList<Point> findStartsOfEmptyRowLine(int length, Board board) {
        ArrayList<Point> points = new ArrayList<>();
        for(int i=0; i<board.getSIZE(); ++i) {
            for(int j=0; j<board.getSIZE()-length+1; ++j) {
                boolean isPossible = true;
                for(int k=j; k<j+length; ++k) {
                    if(board.getCases()[i][k].isLocked()
                            ||(k<board.getSIZE()-1 && board.getCases()[i][k+1].isLocked())
                            ||(i<board.getSIZE()-1 && board.getCases()[i+1][k].isLocked())
                            ||(k>0 && board.getCases()[i][k-1].isLocked())
                            ||(i>0 && board.getCases()[i-1][k].isLocked())) {
                        isPossible=false;
                        break;
                    }
                }
                if(isPossible) {
                    points.add(new Point(i, j));
                }
            }
        }
        return points;
    }
    public ArrayList<Point> findStartsOfEmptyColLine(int length, Board board) {
        ArrayList<Point> points = new ArrayList<>();
        for(int j=0; j<board.getSIZE(); ++j) {
            for(int i=0; i<board.getSIZE()-length+1; ++i) {
                boolean isPossible = true;
                for(int k=i; k<i+length; ++k) {
                    if(board.getCases()[k][j].isLocked()
                            ||(k<board.getSIZE()-1 && board.getCases()[k+1][j].isLocked())
                            ||(j<board.getSIZE()-1 && board.getCases()[k][j+1].isLocked())
                            ||(k>0 && board.getCases()[k-1][j].isLocked())
                            ||(j>0 && board.getCases()[k][j-1].isLocked())) {
                        isPossible=false;
                        break;
                    }
                }
                if(isPossible)
                    points.add(new Point(i, j));
            }
        }
        return points;
    }
    public void placePiece(Board board, Piece[] pieces) {

        ArrayList<Integer> piecesIndex = getPiecesIndexInMind();
        int x = getRowInMind();
        int y = getColInMind();
        String direction = getDirectionInMind();

        Point leftCornerCases = board.getLeftCornerCasesCoordinates(new Point(x, y));
        pieces[piecesIndex.getFirst()].setBounds(
                leftCornerCases.x,
                leftCornerCases.y,
                pieces[0].getSIZE(),
                pieces[0].getSIZE()
        );


        if (direction.equals("right"))
            increaseRowInMind();
        else
            increaseColInMind();

        removePieceInMind();
    }
    public void thinkOfMove(ArrayList<Integer> piecesIndex, Point startPoint, String direction) {
        moveInMind= new Move(piecesIndex, (int) (startPoint.getY()), (int) (startPoint.getX()), direction);
    }
    public ArrayList<Integer> getPiecesIndexInMind() {
        return moveInMind.getPiecesIndex();
    }
    public int getColInMind() {
        return moveInMind.getCol();
    }
    public int getRowInMind() {
        return moveInMind.getRow();
    }
    public String getDirectionInMind() {
        return moveInMind.getDirection();
    }
    public void increaseRowInMind() {
        moveInMind.increaseRow();
    }
    public void increaseColInMind() {
        moveInMind.increaseCol();
    }
    public void removePieceInMind() {
        moveInMind.removePiece();
    }


    public Button getCpuPlay() {
        return cpuPlay;
    }
}