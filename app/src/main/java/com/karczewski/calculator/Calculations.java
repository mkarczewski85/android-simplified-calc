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
        if (currentExpression != "") {
            currentExpression = "";
            calculationResult.onExpressionChanged(currentExpression, true);
        }
    }

    /**
     * dodaje kolejną wciśniętą cyfrę do currentExpression
     */
    public void appendNumber(String number) {
        if (currentExpression.length() <= 16) {
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
        if (!operator.equals("–") && Utils.validateExpressionForOperators(currentExpression)) {
            currentExpression += operator;
            calculationResult.onExpressionChanged(currentExpression, true);
        } else if (operator.equals("–") && Utils.validateForMinus(currentExpression)) {
            currentExpression += "-";
            calculationResult.onExpressionChanged(currentExpression, true);
        }
    }

    /**
     * dodaje kropkę
     */
    public void appendDecimal() {
        if (Utils.validateExpressionForDecimal(currentExpression)) {
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
        if (currentExpression != "" && Utils.validateExpressionForEvaluation(currentExpression)) {
            try {
                Double result = symbols.eval(currentExpression);
                if (Utils.validateResult(result)){
                    currentExpression = Double.toString(result);
                    currentExpression = Utils.trimExpression(currentExpression);
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