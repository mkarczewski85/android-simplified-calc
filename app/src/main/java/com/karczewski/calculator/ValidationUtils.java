package com.karczewski.calculator;

import java.util.ArrayList;

/**
 * Klasa narzędziowa ValidationUtils
 */

public final class ValidationUtils {

    private ValidationUtils() {
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

    public static boolean validateForMinus(String expression) {
        if (expression.endsWith("-")) {
            return false;
        } else if (expression.endsWith("+")) {
            return false;
        } else if (expression.endsWith(".")) {
            return false;
        } else if (expression == "") {
            return true;
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
        } else if (!checkForDecimal(expression)) {
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
        if (expression.charAt(i) == '-') {
            i++;
        }
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
        ArrayList<String> valueList = new ArrayList<>();
        boolean endOfExpression = false;
        StringBuilder tmpValue;

        if (expression.startsWith("-")) {
            i++;
        }

        while (!endOfExpression) {
            tmpValue = new StringBuilder();
            while (i < expression.length() && expression.charAt(i) != '+' &&
                    expression.charAt(i) != '-' &&
                    expression.charAt(i) != '\u00D7' &&
                    expression.charAt(i) != '\u00F7') {
                tmpValue.append(expression.charAt(i));
                if (i + 1 == expression.length()) {
                    break;
                } else {
                    i++;
                }
            }
            if (!tmpValue.toString().isEmpty()) {
                valueList.add(tmpValue.toString());
            }
            if (i + 1 < expression.length() && (expression.charAt(i) == '+' ||
                    expression.charAt(i) == '-' ||
                    expression.charAt(i) == '\u00D7' ||
                    expression.charAt(i) == '\u00F7')) {
                i++;
            } else if (i + 1 == expression.length()) {
                endOfExpression = true;
            }
        }
        if (valueList.get(valueList.size() - 1).contains(".")) {
            return false;
        } else {
            return true;
        }
    }

    public static String trimExpression(String expression) {
        if (expression.length() > 2 && expression.endsWith(".0")) {
            expression = expression.substring(0, expression.length() - 2);
        }
        return expression;
    }

    public static boolean validateResult(double result) {
        if (Double.isInfinite(result)){
            return false;
		} else if (Double.isNaN(result)) {
			return false;
        } else {
            return true;
        }
    }

}
