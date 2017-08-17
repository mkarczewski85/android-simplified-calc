package com.karczewski.calculator;

/**
 * Klasa narzędziowa Utils
 */

public final class Utils {

    private Utils() {
        // klasa narzędziowa, brak możliwości stworzenia instancji
    }

    public static boolean validateExpressionForOperators(String expression) {
        if (expression.endsWith("+") ||
                expression.endsWith("–") ||
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
        if (expression.equals("")){
            return false;
        } else if (expression.endsWith("+") ||
                expression.endsWith("–") ||
                expression.endsWith("\u00F7") ||
                expression.endsWith("\u00D7") ||
                expression.endsWith(".")){
            return false;
        } else if (checkForDecimal(expression)){
            return false;
        } else {
            return true;
        }
    }

    public static boolean validateExpressionForEvaluation(String expression) {
        if (expression.length() > 16){
            return false;
        } else if (expression.endsWith(".") ||
                expression.endsWith("+") ||
                expression.endsWith("–") ||
                expression.endsWith("*") ||
                expression.endsWith("/")){
            return false;
        } else if (!checkForOperator(expression)){
            return false;
        } else {
            return true;
        }
    }

    //metoda sprawdza czy wyrażenie posiada operator arytmetyczny
    private static boolean checkForOperator(String expression) {
        boolean hasOperator = false;
        for (int i = 0; i < expression.length(); i++) {
            if (expression.charAt(i) == '+' ||
                    expression.charAt(i) == '-' ||
                    expression.charAt(i) == '\u00D7' ||
                    expression.charAt(i) == '\u00F7') {
                hasOperator = true;
            }
        }
        return hasOperator;
    }

    private static boolean checkForDecimal(String expression) {
        boolean faultInExpression = false;
        int i = 0;
        int decimalOccurrence = 0;

        while (!faultInExpression){
            while (expression.charAt(i) != '+' ||
                    expression.charAt(i) != '-' ||
                    expression.charAt(i) != '\u00D7' ||
                    expression.charAt(i) != '\u00F7' &&
                    i < expression.length()){
                if (expression.charAt(i) == '.'){
                    decimalOccurrence++;
                }
                i++;
            }
            if (decimalOccurrence >= 1){
                faultInExpression = true;
            }
            i++;
            decimalOccurrence = 0;
        }
        return faultInExpression;
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
