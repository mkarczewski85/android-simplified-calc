package com.karczewski.calculator;

/**
 * Klasa CalculatorPresenter
 */

public class CalculatorPresenter implements CalculatorInterface.ForwardDisplayInteractionToPresenter,
        CalculatorInterface.ForwardInputInteractionToPresenter, Calculations.CalculationResult {

    private CalculatorInterface.PublishToView publishResult;
    private Calculations calc;

    public CalculatorPresenter(CalculatorInterface.PublishToView publishResult) {
        this.publishResult = publishResult;
        this.calc = new Calculations();
        this.calc.setCalculationResultListener(this);
    }

    @Override
    public void onDeleteShortClick() {
        calc.deleteSingleChar();
    }

    @Override
    public void onDeleteLongClick() {
        calc.deleteExpression();
    }

    @Override
    public void onNumberClick(int number) {
        calc.appendNumber(Integer.toString(number));
    }

    @Override
    public void onDecimalClick() {
        calc.appendDecimal();
    }

    @Override
    public void onEvaluateClick() {
        calc.performEvaluation();
    }

    @Override
    public void onOperatorClick(String operator) {
        calc.appendOperator(operator);
    }

    @Override
    public void onExpressionChanged(String result, boolean successful) {
        if (successful){
            publishResult.showResult(result);
        } else {
            publishResult.showToastMessage(result);
        }
    }
}
