package logic;

import static view.elements.board.Piece.getValue;

public class Relation {
    private final Expression[] expressions = new Expression[2];
    private char symbol = ' ';
    private final String relation;

    private static String INCORRECT_RELATION = "Incorrect relation !";
    public Relation(String relation) {
        this.relation = relation;
        int indexOfSymbol = max(relation.indexOf("="), relation.indexOf(">"), relation.indexOf("<"));
        if(indexOfSymbol!=-1) {

            symbol = relation.charAt(indexOfSymbol);
            expressions[0] = new Expression(relation.substring(0, indexOfSymbol));
            expressions[1] = new Expression(relation.substring(indexOfSymbol + 1));
        }
    }
    public static int max(int a, int b, int c) {
        return Math.max(Math.max(a, b), c);
    }
    public int evaluatePieceByPiece() {
        int sum = 0;
        for(int i=0; i<relation.length(); ++i) {
            sum += getValue(relation.charAt(i));
        }
        return sum;
    }

    public String getMessage() {
        if(relation.contains("?")) {
            String solution = removeJokers();
            if(solution.equals("Impossible !"))
                return "Impossible !";
            return new Relation(solution).getMessage();
        }

        String m1 = expressions[0].getMessage();
        String m2 = expressions[1].getMessage();

        if(!relation.matches(".+[=><].+"))
            return "Not an equality !";
        if(m1.equals(Expression.DIVISION_BY_0) || m2.equals(Expression.DIVISION_BY_0))
            return Expression.DIVISION_BY_0;
        if(m1.equals(Expression.INCORRECT_SYNTAX) || m2.equals(Expression.INCORRECT_SYNTAX))
            return Expression.INCORRECT_SYNTAX;
        if(m1.equals(Expression.TOO_BIG) || m2.equals(Expression.TOO_BIG))
            return Expression.TOO_BIG;

        if(!isMathematicallyCorrect())
            return INCORRECT_RELATION;
        return "OK";
    }



    public boolean isMathematicallyCorrect(Double d1, Double d2) {
        return switch (symbol) {
            case '=' -> d1.equals(d2);
            case '<' -> d1 < d2;
            case '>' -> d1 > d2;
            default -> false;
        };
    }

    public boolean isMathematicallyCorrect() {
        return switch (symbol) {
            case '=' -> expressions[0].compareTo(expressions[1]) == 0;
            case '<' -> expressions[0].compareTo(expressions[1]) < 0;
            case '>' -> expressions[0].compareTo(expressions[1]) > 0;
            default -> false;
        };
    }


    public String removeJokers() {
        String possibilities = "0123456789";
        possibilities+="+x-:.%!()";
        for(int i=0; i<possibilities.length(); ++i) {
            String newRelation = relation.substring(0, toString().indexOf('?'))
                               + possibilities.charAt(i)
                               + relation.substring(toString().indexOf('?')+1);
            Relation r = new Relation(newRelation);
            if(r.getMessage().equals("OK"))
                return newRelation;
        }
        return "Impossible !";
    }
    public char getJokerValue() {
        int indexOfJoker = relation.indexOf('?');
        return removeJokers().charAt(indexOfJoker);
    }

    public String toString() {
        return relation;
    }


    public Expression[] getExpressions() {
        return expressions;
    }




}
