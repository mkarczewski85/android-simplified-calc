package com.karczewski.calculator;

/**
 * Klasa narzędziowa Utils
 */

public final class Utils {

    private Utils() {
        // klasa narzędziowa, brak możliwości stworzenia instancji
    }

    //metoda sprawdza czy wyrażenie jest poprawne
    public static boolean validateExpression(String expression) {
        if (expression.endsWith("*") ||
                expression.endsWith("/") ||
                expression.endsWith("-") ||
                expression.endsWith("+") ||
                expression.endsWith(".")) {
            return false;
        } else if (expression.equals("")) {
            return false;
        } else if (expression.length() > 16) {
            return false;
        } else {
            return true;
        }
    }

    //metoda sprawdza czy wyrażenie posiada operator arytmetyczny
    public static boolean checkForOperator(String expression) {
        boolean hasOperator = false;
        for (int i = 0; i < expression.length(); i++) {
            if (expression.charAt(i) == '+' ||
                    expression.charAt(i) == '-' ||
                    expression.charAt(i) == '*' ||
                    expression.charAt(i) == '/') {
                hasOperator = true;
            }
        }
        return hasOperator;
    }

    public static String replaceOperatorSymbols(String expression) {
        expression.replace('\u00D7', '*');
        expression.replace('\u00F7', '/');
        return expression;
    }


}
