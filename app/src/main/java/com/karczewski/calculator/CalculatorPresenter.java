package com.karczewski.calculator;

/**
 * Klasa CalculatorPresenter
 */

public class CalculatorPresenter implements CalculatorInterface.ForwardDisplayInteractionToPresenter,
        CalculatorInterface.ForwardInputInteractionToPresenter, CalculatorInterface.PublishToView {

    private CalculatorInterface.PublishToView publishResult;

    public CalculatorPresenter(CalculatorInterface.PublishToView publishResult) {
        this.publishResult = publishResult;
    }

    @Override
    public void showResult(String result) {

    }

    @Override
    public void showToastMessage(String message) {

    }

    @Override
    public void onDeleteShortClick() {

    }

    @Override
    public void onDeleteLongClick() {

    }

    @Override
    public void onNumberClick(int number) {

    }

    @Override
    public void onDecimalClick() {

    }

    @Override
    public void onEvaluateClick() {

    }

    @Override
    public void onOperatorClick(String operator) {

    }
}
