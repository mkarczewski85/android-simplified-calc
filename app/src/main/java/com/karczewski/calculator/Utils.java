package com.karczewski.calculator;

import java.util.ArrayList;

/**
 * Klasa narzędziowa Utils
 */

public final class Utils {

    private Utils() {
        // klasa narzędziowa, brak możliwości stworzenia instancji
    }

    public static boolean validateExpressionForOperators(String expression) {
        if (expression.endsWith("+") ||
                expression.endsWith("-") ||
                expression.endsWith("\u00F7") ||
                expression.endsWith("\u00D7") ||
                expression.endsWith(".")) {
            return false;
        } else if (expression.equals("")) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean validateExpressionForDecimal(String expression) {
        if (expression.equals("") || expression == null) {
            return false;
        } else if (expression.endsWith("+") ||
                expression.endsWith("-") ||
                expression.endsWith("\u00F7") ||
                expression.endsWith("\u00D7") ||
                expression.endsWith(".")) {
            return false;
        } else if (checkForDecimal(expression)) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean validateExpressionForEvaluation(String expression) {
        if (expression.length() > 16 || expression == "" || expression == null) {
            return false;
        } else if (expression.endsWith(".") ||
                expression.endsWith("+") ||
                expression.endsWith("-") ||
                expression.endsWith("*") ||
                expression.endsWith("/")) {
            return false;
        } else if (!checkForOperator(expression)) {
            return false;
        } else {
            return true;
        }
    }

    //metoda sprawdza czy wyrażenie posiada operator arytmetyczny
    private static boolean checkForOperator(String expression) {
        boolean hasOperator = false;
        int i = 0;
        while (i < expression.length() && !hasOperator) {
            if (expression.charAt(i) == '+' ||
                    expression.charAt(i) == '-' ||
                    expression.charAt(i) == '\u00D7' ||
                    expression.charAt(i) == '\u00F7') {
                hasOperator = true;
            }
            i++;
        }
        return hasOperator;
    }

    private static boolean checkForDecimal(String expression) {

        int i = 0;
        int decCounter = 0;
        ArrayList<String> valueList = new ArrayList<>();
        boolean endOfExpression = false;

        while (!endOfExpression) {
            StringBuilder tmpValue = new StringBuilder();
            while (i < expression.length() && (expression.charAt(i) != '+' ||
                    expression.charAt(i) != '-' ||
                    expression.charAt(i) != '\u00D7' ||
                    expression.charAt(i) != '\u00F7')) {
                tmpValue.append(expression.charAt(i));
                if (expression.charAt(i) == '.') {
                    decCounter++;
                }
                if (i+1 >= expression.length()){
                    break;
                } else {
                    i++;
                }
            }
            valueList.add(tmpValue.toString());
            if (i < expression.length() && (expression.charAt(i + 1) == '+' ||
                    expression.charAt(i) == '-' ||
                    expression.charAt(i) == '\u00D7' ||
                    expression.charAt(i) == '\u00F7')) {
                i++;
            } else if (i == expression.length()) {
                endOfExpression = true;
            }
        }

        if (decCounter <= valueList.size()) {
            return true;
        } else {
            return false;
        }
    }

    public static String replaceOperatorSymbols(String expression) {
        expression.replace('\u00D7', '*');
        expression.replace('\u00F7', '/');
        return expression;
    }

    public static String trimExpression(String expression) {
        if (expression.length() > 1 && expression.charAt(expression.length() - 1) == '0' &&
                expression.charAt(expression.length() - 2) == '.') {
            expression = expression.substring(0, expression.length() - 2);
        }
        return expression;
    }

}
