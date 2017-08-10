package com.karczewski.calculator;

/**
 * Interfejs View-Presenter
 */

public interface CalculatorInterface {

    //metody wyświetlające w View (DisplayFragment)
    interface PublishToView {

        void showResult(String result);

        void showToastMessage(String message);
    }

    //przekazuje eventy z View (DisplayFragment) do presentera
    interface ForwardDisplayInteractionToPresenter {

        void onDeleteShortClick();

        void onDeleteLongClick();

    }

    //przekazuje eventy z View (InputFragment) do presentera
    interface ForwardInputInteractionToPresenter {

        void onNumberClick(int number);

        void onDecimalClick();

        void onEvaluateClick();

        void onOperatorClick(String operator);

    }

}
