package ru.spbstu.appmath.gurevich;

/*
 * Implicitly makes a tree of expressions
 */

public class Parser {
    public Parser() {
    }
    /* creates a tree of expressions */
    public Expression parse(String s) throws Exception{

        String trimmed = s.trim();  //this function cuts out all spaces and tabs
    
        /*processes +- outside brackets */
        int plusPos = findPosOperator(trimmed, '+');
        int minusPos = findPosOperator(trimmed, '-');
        if (plusPos != -1 && (minusPos == -1 || plusPos <  minusPos)) {
            return new Binary(parse(trimmed.substring(0, plusPos)), parse(trimmed.substring(plusPos + 1)), '+');
        } else if (minusPos != -1) {
            return new Binary(parse(trimmed.substring(0, minusPos)), parse(trimmed.substring(minusPos + 1)), '-');
        }

        /* processes * outside brackets*/
        int multPos = findPosOperator(trimmed, '*');
        if (multPos != -1) {
            return new Binary(parse(trimmed.substring(0, multPos)), parse(trimmed.substring(multPos + 1)), '*');
        }

        /* processes / outside brackets*/
        int divPos = findPosOperator(trimmed, '/');
        if (divPos != -1) {
            return new Binary(parse(trimmed.substring(0, divPos)), parse(trimmed.substring(divPos + 1)), '/');
        }

        /* processes the interior of brackets */
        final int openBracketPos = trimmed.indexOf('(');
        final int closeBracketPos = trimmed.lastIndexOf(')');
        if (openBracketPos != -1 && closeBracketPos != -1 && openBracketPos < closeBracketPos) {
            return parse(trimmed.substring(openBracketPos + 1, closeBracketPos));
        }

        /* processes the variable or number */
        if (openBracketPos == -1 && closeBracketPos == -1) {
            if (isNumber(trimmed)) {
                if (trimmed.equals(""))
                    return new Const(0);
                return new Const(Double.parseDouble(trimmed));
            }
            if ("x".equals(trimmed)) {
                return new Var();
            }
            throw new Exception("Unexpected symbol!");
        }
        throw new Exception("Syntax error!");
    }

    /* Checks if string is a number or empty */
    private static boolean isNumber(String s) {
        if (s.equals(""))
            return true;
        for (char c : s.toCharArray()) {
            if (!Character.isDigit(c) && c != '.')
                return false;
        }
        return true;
    }

    /* looks for operator op outside brackets */
    private int findPosOperator(String trimmed, char op) throws Exception{
        int index = 0;
        int pos;
        do {
            pos = trimmed.indexOf(op, index);
            index = getIndexLastClose(trimmed, pos);
        } while (inBrackets(trimmed, pos));
        return pos;
    }

    /*Checks if i-th character of string s is closed in brackets*/
    private static boolean inBrackets(String s, int i) {
        if (i != -1)
        {
            int cOpen = 0;
            int cClose = 0;
            for (int j = 0; j < i; j++) {
                if (s.charAt(j) == '(')
                    cOpen++;
                if (s.charAt(j) == ')')
                    cClose++;
            }
            return cOpen != cClose;
        } else {
            return false;
        }
    }

    /* returns position of the end of bracket construction after i-th element*/
    private static int getIndexLastClose(String s, int i) throws Exception{
        if (i != -1)
        {
            int cOpen = 0;
            int cClose = 0;
            for (int j = 0; j < i ; j++) {
                if (s.charAt(j) == '(')
                    cOpen++;
                if (s.charAt(j) == ')')
                    cClose++;
            }
            int index = i;
            while (cOpen != cClose) {
                index = s.indexOf(')', index + 1);
                if (index != -1)
                    cClose++;
                else
                    throw new Exception("Problem with brackets!");
            }
            return index + 1;
        }
        else
            return 0;
    }
}
