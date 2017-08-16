package com.karczewski.calculator;

import org.javia.arity.Symbols;
import org.javia.arity.SyntaxException;

/**
 * Klasa Calculations
 */

public class Calculations {

    private final Symbols symbols;
    private CalculationResult calculationResult;
    private static String currentExpression;


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
        if (currentExpression.equals("")) {
//            calculationResult.onExpressionChanged("Wyświetlacz jest pusty", false);
        } else {
            currentExpression = "";
            calculationResult.onExpressionChanged(currentExpression, true);
        }
    }

    /**
     * dodaje kolejną wciśniętą cyfrę do currentExpression
     */
    public void appendNumber(String number) {
        if (currentExpression.startsWith("0") && number.equals("0")) {
            calculationResult.onExpressionChanged("Nieprawidłowe wyrażenie", false);
        } else {
            if (currentExpression.length() <= 16) {
                calculationResult.onExpressionChanged(currentExpression, true);
            } else {
                calculationResult.onExpressionChanged("Wyrażenie jest zbyt długie!", false);
            }
        }
    }

    /**
     * dodaje znak operatora do currentExpression
     */
    public void appendOperator(String operator) {
        if (Utils.validateExpression(currentExpression)) {
            currentExpression += operator;
            calculationResult.onExpressionChanged(currentExpression, true);
        } else {
            calculationResult.onExpressionChanged("Wyrażenie jest nieprawidłowe", false);
        }
    }

    /**
     * dodaje kropkę
     */
    public void appendDecimal() {
        if (Utils.validateExpression(currentExpression)) {
            currentExpression += ".";
            calculationResult.onExpressionChanged(currentExpression, true);
        } else {
            calculationResult.onExpressionChanged("Wyrażenie jest nieprawidłowe", false);
        }
    }

    /**
     * jeśli currentExpression spełnia warunki, metoda przekazuje wyrażenie do meody eval w obiekcie
     * symbols (biblioteka Arity). Zewnętrzna metoda replaceOperatorSymbols zmienia symbole
     * operatorów mnożenia i dzielenia z klawiatury na symbole obsługiwane przez Arity.
     */
    public void performEvaluation() {
        Utils.replaceOperatorSymbols(currentExpression);
        if (Utils.validateExpression(currentExpression)) {
            try {
                Double result = symbols.eval(currentExpression);
                currentExpression = Double.toString(result);
                calculationResult.onExpressionChanged(currentExpression, true);
            } catch (SyntaxException e) {
                calculationResult.onExpressionChanged("Wyrażenie jest nieprawidłowe", false);
                e.printStackTrace();
            }
        }
    }

}