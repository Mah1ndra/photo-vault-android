package com.secure.calculatorp.ui.keypad;

import android.content.Context;

import com.secure.calculatorp.R;
import com.secure.calculatorp.crypto.CryptoKeyManager;
import com.secure.calculatorp.data.DataManager;
import com.secure.calculatorp.di.ActivityContext;
import com.secure.calculatorp.util.StringUtil;

import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

import javax.crypto.SecretKey;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by zakir on 02/01/2019.
 */

public class PinValidator {

    private DataManager dataManager;
    private CryptoKeyManager cryptoKeyManager;
    private Context context;

    @Inject
    public PinValidator(DataManager dataManager, CryptoKeyManager cryptoKeyManager,
                        @ActivityContext Context context) {
        this.dataManager = dataManager;
        this.cryptoKeyManager = cryptoKeyManager;
        this.context = context;
    }

    public boolean isPinPossible(String pin) {
        return (!StringUtil.isNullOrEmpty(pin)
                && isOperatorSelected(pin));
    }

    public String extractPin(String pin) {
        return pin.substring(0, pin.length() - 1);
    }

    public String extractOperator(String pin) {
        return pin.substring(pin.length() - 1, pin.length());
    }

    private boolean isOperatorSelected(String s) {
        return s.contains(context.getString(R.string.str_plus))
                || s.contains(context.getString(R.string.str_minus))
                || s.contains(context.getString(R.string.str_multiply))
                || s.contains(context.getString(R.string.str_percentage))
                || s.contains(context.getString(R.string.str_division));
    }


    public boolean hasPin() {
        return dataManager.hasPinCodeEnabled();
    }

    public boolean generateSecretKey(String pin) {
        String newPin = extractPin(pin);
        String newOperator = extractOperator(pin);

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
            SecretKey secretKey = cryptoKeyManager.generateKey(newPin.toCharArray());
            cryptoKeyManager.storeKey(newPin.toCharArray(), secretKey);
            return secretKey;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | KeyStoreException e) {
            e.printStackTrace();
            return null;
        }
    }

    public SecretKey validateAndExtractKey(String pin) {
        try {
            return cryptoKeyManager.getSecretKey(pin.toCharArray());
        } catch (KeyStoreException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean isActivatorPressed(String pin) {
        String operator = dataManager.getOperator();
        return operator.charAt(0) == pin.charAt(pin.length() - 1);
    }

}
