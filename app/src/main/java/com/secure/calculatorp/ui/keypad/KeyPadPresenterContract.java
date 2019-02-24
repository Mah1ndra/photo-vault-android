package com.secure.calculatorp.ui.keypad;

import com.secure.calculatorp.crypto.CryptoConstant;
import com.secure.calculatorp.crypto.CryptoManager;
import com.secure.calculatorp.crypto.key.KeyGen;
import com.secure.calculatorp.data.DataManager;
import com.secure.calculatorp.ui.task.DecryptionTask;
import com.secure.calculatorp.util.CommonUtils;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.inject.Inject;


public class KeyPadPresenterContract<V extends KeyPadView> implements KeyPadPresenter<V> {

    private PinValidator pinCodeValidator;
    private Calculator calculator;
    private DataManager dataManager;
    private CryptoManager cryptoManager;
    private V keyPadView;


    @Inject
    KeyPadPresenterContract(PinValidator pinValidator, Calculator calculator,
                            DataManager dataManager, CryptoManager cryptoManager) {
        this.calculator = calculator;
        this.pinCodeValidator = pinValidator;
        this.dataManager = dataManager;
        this.cryptoManager = cryptoManager;
        calculator.init();
    }

    @Override
    public void handleKeyInput(String keyPressed) {
        String pin = calculator.getString(keyPressed);

        if (pin.length() > CryptoConstant.MIN_PIN_LENGTH &&
                pinCodeValidator.isPinPossible(pin)) {
            String strippedPin = pin.substring(0, pin.length() - 1);
            if (!dataManager.hasPinCodeEnabled()) {
                if (generateSecretKey(pin)) {
                    onPinCreated(strippedPin, null);
                }
            } else if (pinCodeValidator.isActivatorPressed(pin, dataManager.getOperator())) {
                try {
                    Key key = cryptoManager.retrieveKey(strippedPin.toCharArray());
                    if (key != null) {
                        onPinCreated(strippedPin, key);
                    }
                } catch (KeyStoreException | UnrecoverableEntryException | NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }
        }

        else if (pin.length() == 1 &&
                pinCodeValidator.isPinPossible(pin)) {
            return;
        }

        keyPadView.setResultView(calculator.getStrKey(keyPressed));
    }

    private void onPinCreated(String pin, Key key) {
        calculator.setStrKey("0");
        keyPadView.setResultView("0");
        dataManager.setTempPin(pin);
        if (key == null) {
            moveToVaultActivity();
        } else {
            decryptImages(key);
        }

    }

    private void moveToVaultActivity() {
        keyPadView.moveToVaultActivity();
    }

    private void decryptImages(Key key) {

        DecryptionTask decryptionTask = new DecryptionTask(dataManager,
                new DecryptionTask.DecryptionTaskCallback() {
                    @Override
                    public void onStart() {
                        keyPadView.showProgress();
                    }

                    @Override
                    public void onStop() {

                    }

                    @Override
                    public void onDecrypted() {
                        keyPadView.moveToVaultActivity();
                        keyPadView.destroyActivity();
                    }

                    @Override
                    public void onError() {

                    }
                });
        decryptionTask.execute((SecretKey) key);
    }

    @Override
    public void onVisibleScreen() {
        if (!dataManager.hasPinCodeEnabled()) {
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

    public boolean generateSecretKey(String pin) {
        String newPin = pinCodeValidator.extractPin(pin);
        String newOperator = pinCodeValidator.extractOperator(pin);

        SecretKey secretKey = generateKey(newPin);

        if (secretKey != null) {
            dataManager.setOperator(newOperator);
            dataManager.setPinCode(true);
            return true;
        }

        return false;
    }

    private SecretKey generateKey(String newPin) {
        try {
            KeyGen.Config config = new KeyGen.Config(256, 2000, CommonUtils.generateRandom(32), newPin.toCharArray());
            return (SecretKey) cryptoManager.generateKey(config);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | KeyStoreException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public void onAttach(V mvpView) {
        keyPadView = mvpView;
    }
}
