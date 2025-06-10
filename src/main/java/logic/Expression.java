package logic;

public class Expression implements Comparable {

    private static String FLOAT_REGEX = "-?[0-9]+(\\.[0-9]+)*";

    protected static String INCORRECT_SYNTAX = "Incorrect syntax !";

    protected static String DIVISION_BY_0 = "Can't divide by zero";

    protected static String TOO_BIG = "Too big !";
    private final String expression;
    public Expression(String expression) {
        this.expression = expression;
    }
    public static String simplifyManually(String exp) {

        if(exp.contains("="))
            return INCORRECT_SYNTAX;

        if(exp.contains("(")) {
            if(exp.contains(")")) {
                int indexOfMatchingClosedP = findIndexOfClosedParenthesesMatchingTheFirstOne(exp);
                if(indexOfMatchingClosedP!=-1) {
                    String beforeParentheses = exp.substring(0, exp.indexOf("("));
                    String insideParentheses = exp.substring(exp.indexOf("(") + 1, indexOfMatchingClosedP);
                    String afterParentheses = exp.substring(indexOfMatchingClosedP + 1);
                    return simplifyManually(beforeParentheses + simplifyManually(insideParentheses) + afterParentheses);
                }
            }
            return INCORRECT_SYNTAX;
        }

        if(exp.contains("%")) {
            return simplifyManually(exp.replaceAll("%", ":100"));
        }

        if(exp.contains("!")) {
            if(exp.indexOf("!")==0)
                return INCORRECT_SYNTAX;
            String removedFact = removeFact(exp);
            if(!removedFact.equals(TOO_BIG))
                return simplifyManually(removedFact);
            return TOO_BIG;
        }

        if(exp.contains("+")) {
            String beforePlus = exp.substring(0, exp.indexOf("+"));
            String afterPlus = exp.substring(exp.indexOf("+")+1);
            if(beforePlus.isEmpty() || afterPlus.isEmpty())
                return INCORRECT_SYNTAX;
            String s1 = simplifyManually(beforePlus);
            String s2 = simplifyManually(afterPlus);
            if(s1.matches(FLOAT_REGEX)) {
                if(s2.matches(FLOAT_REGEX)) {
                    double a = Double.parseDouble(simplifyManually(beforePlus));
                    double b = Double.parseDouble(simplifyManually(afterPlus));
                    return (a + b) + "";
                }
                return s2;
            }
            return s1;
        }

        if(exp.contains("-")) {
            if(exp.contains("--")) {
                String beforeDoubleMinus = exp.substring(0, exp.lastIndexOf("--"));
                String afterDoubleMinus = exp.substring(exp.lastIndexOf("--")+2);
                return simplifyManually(beforeDoubleMinus + "+" + afterDoubleMinus);
            }
            String beforeMinus = exp.substring(0, exp.lastIndexOf("-"));
            String afterMinus = exp.substring(exp.lastIndexOf("-")+1);
            if(afterMinus.isEmpty())
                return INCORRECT_SYNTAX;
            if(!beforeMinus.isEmpty()) {
                String s1 = simplifyManually(beforeMinus);
                String s2 = simplifyManually(afterMinus);
                if(s1.matches(FLOAT_REGEX)) {
                    if(s2.matches(FLOAT_REGEX)) {
                        double a = Double.parseDouble(simplifyManually(beforeMinus));
                        double b = Double.parseDouble(simplifyManually(afterMinus));
                        return (a - b) + "";
                    }
                    return s2;
                }
                return s1;
            }
            if(afterMinus.matches(FLOAT_REGEX))
                return exp;
        }

        if(exp.contains("x")) {
            String beforeCross = exp.substring(0, exp.indexOf("x"));
            String afterCross = exp.substring(exp.indexOf("x")+1);
            if(beforeCross.isEmpty() || afterCross.isEmpty())
                return INCORRECT_SYNTAX;
            String s1 = simplifyManually(beforeCross);
            String s2 = simplifyManually(afterCross);
            if(s1.matches(FLOAT_REGEX)) {
                if(s2.matches(FLOAT_REGEX)) {
                    double a = Double.parseDouble(simplifyManually(beforeCross));
                    double b = Double.parseDouble(simplifyManually(afterCross));
                    return (a * b) + "";
                }
                return s2;
            }
            return s1;
        }

        if(exp.contains(":")) {
            String beforeDivide = exp.substring(0, exp.lastIndexOf(":"));
            String afterDivide = exp.substring(exp.lastIndexOf(":")+1);
            if(beforeDivide.isEmpty() || afterDivide.isEmpty())
                return INCORRECT_SYNTAX;
            String s1 = simplifyManually(beforeDivide);
            String s2 = simplifyManually(afterDivide);
            if(s1.matches(FLOAT_REGEX)) {
                if(s2.matches(FLOAT_REGEX)) {
                    double a = Double.parseDouble(simplifyManually(beforeDivide));
                    double b = Double.parseDouble(simplifyManually(afterDivide));
                    if (b != 0)
                        return (a / b) + "";
                    return DIVISION_BY_0;
                }
                return s2;
            }
            return s1;
        }

        if(exp.matches(FLOAT_REGEX))
            return exp;

        return INCORRECT_SYNTAX;

    }
    public static String removeFact(String s) {
        int indexFact=s.indexOf("!");
        int i=indexFact-1;
        while(i>0 && Character.isDigit(s.charAt(i-1))) {
            --i;
        }
        if(i==indexFact-1)
            return s.substring(0, i)
                + fact(Integer.parseInt(s.substring(i, indexFact)))
                + s.substring(indexFact+1);
        else
            return TOO_BIG;
    }
    public static int fact(int n) {
        if(n==0)
            return 1;
        return n*fact(n-1);
    }
    @Override
    public int compareTo(Object o) {
        double a = Double.parseDouble(simplifyManually());
        double b = Double.parseDouble(((Expression) o).simplifyManually());
        return Double.compare(a, b);
    }
    public String getMessage() {
        String result = simplifyManually();
        if(result.equals(DIVISION_BY_0)
            || result.equals(INCORRECT_SYNTAX)
            || result.equals(TOO_BIG))
                return result;
        return "OK";
    }
    public String simplifyManually() {
        return simplifyManually(expression);
    }



    public static int findIndexOfClosedParenthesesMatchingTheFirstOne(String s) {
        int indexOfFirstOpenP = s.indexOf("(");
        int numberOfOpenEncountered = 1;
        if(indexOfFirstOpenP<s.length()-1)
            for(int i = indexOfFirstOpenP+1; i<s.length(); ++i) {
                if(s.charAt(i) == '(') {
                    ++numberOfOpenEncountered;
                }
                else {
                    if(s.charAt(i) == ')') {
                        if(numberOfOpenEncountered==1)
                            return i;
                        else
                            --numberOfOpenEncountered;
                    }
                }

            }
        return -1;
    }

    public String toString() {
        return expression;
    }





}
