package com.karczewski.calculator;

import org.javia.arity.Symbols;
import org.javia.arity.SyntaxException;
import org.javia.arity.Util;

/**
 * Klasa Calculations
 */

public class Calculations {

    private final Symbols symbols;
    private CalculationResult calculationResult;
    private static String currentExpression;

    private static final int MAX_DIGITS = 12;
    private static final int ROUNDING_DIGITS = Math.max(17 - MAX_DIGITS, 0);
    public static final int MAX_EXPRESSION_LENGTH = 16;

    interface CalculationResult {
        void onExpressionChanged(String result, boolean succesful);
    }

    public void setCalculationResultListener(CalculationResult calculationResult) {
        this.calculationResult = calculationResult;
        currentExpression = "";
    }

    public Calculations() {
        symbols = new Symbols();
    }

    /**
     * usuwa pojedynczy znak w currentExpression, chyba że jest puste
     */
    public void deleteSingleChar() {
        if (currentExpression.length() > 0) {
            currentExpression = currentExpression.substring(0, currentExpression.length() - 1);
            calculationResult.onExpressionChanged(currentExpression, true);
        } else {
            currentExpression = "";
        }
    }

    /**
     * usuwa całe wpisane wyrażenie arytmetyczne lub wwynik w currentExpression
     */
    public void deleteExpression() {
        if (currentExpression != "") {
            currentExpression = "";
            calculationResult.onExpressionChanged(currentExpression, true);
        }
    }

    /**
     * dodaje kolejną wciśniętą cyfrę do currentExpression
     */
    public void appendNumber(String number) {
        if (currentExpression.length() <= MAX_EXPRESSION_LENGTH) {
            currentExpression += number;
            calculationResult.onExpressionChanged(currentExpression, true);
        } else {
            calculationResult.onExpressionChanged("Wyrażenie jest zbyt długie!", false);
        }
    }

    /**
     * dodaje znak operatora do currentExpression
     */
    public void appendOperator(String operator) {
        if (!operator.equals("–") && ValidationUtils.validateExpressionForOperators(currentExpression)) {
            currentExpression += operator;
            calculationResult.onExpressionChanged(currentExpression, true);
        } else if (operator.equals("–") && ValidationUtils.validateForMinus(currentExpression)) {
            currentExpression += "-";
            calculationResult.onExpressionChanged(currentExpression, true);
        }
    }

    /**
     * dodaje kropkę
     */
    public void appendDecimal() {
        if (ValidationUtils.validateExpressionForDecimal(currentExpression)) {
            currentExpression += ".";
            calculationResult.onExpressionChanged(currentExpression, true);
        }
    }

    /**
     * jeśli currentExpression spełnia warunki, metoda przekazuje wyrażenie do meody eval w obiekcie
     * symbols (biblioteka Arity). Zewnętrzna metoda replaceOperatorSymbols zmienia symbole
     * operatorów mnożenia i dzielenia z klawiatury na symbole obsługiwane przez Arity.
     */
    public void performEvaluation() {
        if (currentExpression != "" && ValidationUtils.validateExpressionForEvaluation(currentExpression)) {
            try {
                Double result = symbols.eval(currentExpression);
                if (ValidationUtils.validateResult(result)){
                    currentExpression =  Util.doubleToString(result, MAX_DIGITS, ROUNDING_DIGITS);
                    currentExpression = ValidationUtils.trimExpression(currentExpression);
                    calculationResult.onExpressionChanged(currentExpression, true);
                } else {
                    currentExpression = "";
                    calculationResult.onExpressionChanged(currentExpression, true);
                    calculationResult.onExpressionChanged("Wyrażenie jest nieprawidłowe!", false);
                }
            } catch (SyntaxException e) {
                calculationResult.onExpressionChanged("Wyrażenie jest nieprawidłowe!", false);
                e.printStackTrace();
            }
        }
    }

}