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
    private static boolean expressionHasOperator;


    interface CalculationResult {
        void onExpressionChanged(String result, boolean succesful);
    }

    public void setCalculationResultListener(CalculationResult calculationResult) {
        this.calculationResult = calculationResult;
        currentExpression = "";
    }

    public Calculations() {
        symbols = new Symbols();
        expressionHasOperator = false;
    }

    /**
     * usuwa pojedynczy znak w currentExpression, chyba że jest puste
     */
    public void deleteSingleChar() {
        if (currentExpression.length() > 0) {
            //jeszcze jeden if sprawdzający czy ununięty znak to operator, trzeba uprościć
            String singleChar = currentExpression.substring(currentExpression.length(),
                    currentExpression.length()-1);
            if (singleChar == "+" || singleChar == "-" || singleChar == "*" || singleChar == "/"){
                expressionHasOperator = false;
            }
            currentExpression = currentExpression.substring(0, currentExpression.length() - 1);
            calculationResult.onExpressionChanged(currentExpression, true);
        } else {
            calculationResult.onExpressionChanged("Nieprawidłowe wyrażenie", false);
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
            expressionHasOperator = false;
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
                calculationResult.onExpressionChanged("Liczba jest zdecydowanie zbyt długa", false);
            }
        }
    }

    /**
     * dodaje znak operatora do currentExpression
     */
    public void appendOperator(String operator) {
        if (validateExpression(currentExpression)) {
            currentExpression += operator;
            calculationResult.onExpressionChanged(currentExpression, true);
            expressionHasOperator = true;
        }
    }

    /**
     * dodaje kropkę
     */
    public void appendDecimal() {
        if (validateExpression(currentExpression)) {
            currentExpression += ".";
            calculationResult.onExpressionChanged(currentExpression, true);
        }
    }

    /**
     * jeśli currentExpression spełnia warunki, metoda przekazuje wyrażenie do meody eval w obiekcie
     * symbols (biblioteka Arity). Trzeba stworzyć metodę w Utils, która zamieniałaby symbole
     * operatorów z InputFragment na symbole przejmowane przez Arity.
     */
    public void performEvaluation() {
        if (validateExpression(currentExpression)) {
            try {
                Double result = symbols.eval(currentExpression);
                currentExpression = Double.toString(result);
                calculationResult.onExpressionChanged(currentExpression, true);
                expressionHasOperator = false;
            } catch (SyntaxException e) {
                calculationResult.onExpressionChanged("Wyrażenie jest nieprawidłowe", false);
                e.printStackTrace();
            }
        }
    }

    /**
     * zewnętrzna metoda sprawdzająca czy currentExpression spełnia warunki, trzeba wywalić
     * wywołania metody w obiekcie z powrotem do metod tej klasy (elsach) a metodę validateExpression
     * jako statyczną przenieśc do klasy Utils
     */
    public boolean validateExpression(String expression) {
        if (expression.endsWith("*") ||
                expression.endsWith("/") ||
                expression.endsWith("-") ||
                expression.endsWith("+") ||
                expression.endsWith(".")
                ) {
            calculationResult.onExpressionChanged("Wyrażenie jest nieprawidłowe", false);
            return false;
        } else if (expression.equals("")) {
            calculationResult.onExpressionChanged("Wyrażenie jest puste", false);
            return false;
        } else if (expression.length() > 16) {
            calculationResult.onExpressionChanged("Wyrażenie jest zdecydowanie zbyt długie", false);
            return false;
        } else if (!expressionHasOperator) { //ja to rozwiązać? może zewnętrzna metoda sprawdzająca całe currentExpression
            calculationResult.onExpressionChanged("To nie jest wyrażenie arytmetyczne", false);
            return false;
        } else {
            return true;
        }

    }

}