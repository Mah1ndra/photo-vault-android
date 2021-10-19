package com.lionroarsrk.scapp.ui.keypad;

import android.content.Context;

import com.lionroarsrk.scapp.R;
import com.lionroarsrk.scapp.crypto.key.CryptoKeyManager;
import com.lionroarsrk.scapp.data.DataManager;
import com.lionroarsrk.scapp.di.ActivityContext;
import com.lionroarsrk.scapp.util.StringUtil;

import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;

import javax.crypto.SecretKey;
import javax.inject.Inject;



public class PinValidator {

    private ArrayList<String> listOperators;

    @Inject
    public PinValidator() {
        listOperators = new ArrayList<>();
        initOperators();
    }

    public void initOperators() {
        listOperators.add("+");
        listOperators.add("-");
        listOperators.add("x");
        listOperators.add("/");
        listOperators.add("%");
    }

    public boolean isPinPossible(String pin) {
        return (!StringUtil.isNullOrEmpty(pin) && isOperatorSelected(pin));
    }

    public String extractPin(String pin) {
        return pin.substring(0, pin.length() - 1);
    }

    public String extractOperator(String pin) {
        return pin.substring(pin.length() - 1, pin.length());
    }

    private boolean isOperatorSelected(String s) {
        if(listOperators!= null) {
            for (String op :
                    listOperators) {
                if (s.contains(op))
                    return true;
            }
        }
        return false;
    }


    public boolean isActivatorPressed(String pin, String operator) {
        return operator.charAt(0) == pin.charAt(pin.length() - 1);
    }

}
