package com.secure.calculatorp.ui.keypad;

import com.secure.calculatorp.crypto.CryptoConstant;

import javax.inject.Inject;

/**
 * Created by zakir on 30/12/2018.
 */

public class KeyPadPresenterContract<V extends KeyPadView> implements KeyPadPresenter<V> {

    private PinValidator pinCodeValidator;
    private Calculator calculator;
    private V keyPadView;


    @Inject
    KeyPadPresenterContract(PinValidator pinValidator, Calculator calculator) {
        this.calculator = calculator;
        this.pinCodeValidator = pinValidator;
        calculator.init();
    }

    @Override
    public void handleKeyInput(String keyPressed) {
        String pin = calculator.getString(keyPressed);

        if (pinCodeValidator.isPinPossible(pin)) {
            String strippedPin = pin.substring(0, pin.length() - 1);
            if (!pinCodeValidator.hasPin()) {
                if (pin.length() > CryptoConstant.MIN_PIN_LENGTH) {
                    if (pinCodeValidator.generateSecretKey(pin)) {
                        keyPadView.showPinSuccessDialog();
                        goToVault(strippedPin);
                    }
                } else {
                    keyPadView.showPinErrorToast();
                }
            } else if (pinCodeValidator.isActivatorPressed(pin)) {
                if(pinCodeValidator.validateAndExtractKey(strippedPin) != null) {
                    goToVault(strippedPin);
                }
            }
        }

        keyPadView.setResultView(calculator.getStrKey(keyPressed));
    }

    private void goToVault(String pin) {
        calculator.setStrKey("0");
        keyPadView.destroyActivity();
        keyPadView.moveToVaultActivity(pin);
    }


    @Override
    public void onVisibleScreen() {
        if (!pinCodeValidator.hasPin()) {
            calculator.setPinMode(true);
            keyPadView.showPinDialog();
        } else {
            calculator.setPinMode(false);
        }
    }

    @Override
    public void onHiddenScreen() {
        calculator.setStrKey("0");
        keyPadView.setResultView("0");
    }


    @Override
    public void onAttach(V mvpView) {
        keyPadView = mvpView;
    }
}
