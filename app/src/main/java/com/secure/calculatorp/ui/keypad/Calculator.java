package com.secure.calculatorp.ui.keypad;

import android.content.Context;
import android.text.TextUtils;

import com.secure.calculatorp.R;
import com.secure.calculatorp.di.ActivityContext;
import com.secure.calculatorp.util.StringUtil;

import java.util.ArrayList;

import javax.inject.Inject;


public class Calculator {


    public void setStrKey(String strKey) {
        this.strKey = strKey;
    }

    private String strKey = "";
    private String operatorSelected = "";
    private Context context;
    private ArrayList<String> listOperators = new ArrayList<>();
    private boolean isPinMode;


    @Inject
    public Calculator(@ActivityContext Context context) {
        this.context = context;
    }

    public void init() {
        initOperators();
    }

    public void initOperators() {
        listOperators.add(context.getString(R.string.str_plus));
        listOperators.add(context.getString(R.string.str_minus));
        listOperators.add(context.getString(R.string.str_multiply));
        listOperators.add(context.getString(R.string.str_division));
    }

    public String getString(String keyPressed) {
        return strKey + keyPressed;
    }

    public String getStrKey(String strBtn) {
        if (isPinMode) {
            if (strBtn.equals(context.getString(R.string.str_backspace))) {
                if (strKey.length() > 0)
                    strKey = strKey.substring(0, strKey.length() - 1);
            } else {
                strKey += strBtn;
            }

        } else {
            if (TextUtils.isDigitsOnly(strBtn) || strBtn.equals(".")) {
                strKey += strBtn;
            } else if (listOperators.contains(strBtn)) {
                if (operatorSelected.isEmpty()) {
                    strKey += strBtn;
                    operatorSelected = strBtn;
                } else {
                    String result = getResult(operatorSelected, strKey);
                    if (result.isEmpty()) {
                        strKey = strKey.substring(0, strKey.length() - 1) + strBtn;
                    } else {
                        strKey = result + strBtn;
                    }
                    operatorSelected = strBtn;
                }
            } else {
                if (strBtn.equals(context.getString(R.string.str_clear))) {
                    strKey = "";
                    operatorSelected = "";
                } else if (strBtn.equals(context.getString(R.string.str_backspace))) {
                    if (strKey.length() > 0)
                        strKey = strKey.substring(0, strKey.length() - 1);
                    if (!StringUtil.isOperatorSelected(strKey, context))
                        operatorSelected = "";
                } else if (strBtn.equals(context.getString(R.string.str_equal)) && !operatorSelected.isEmpty()) {
                    strKey = getResult(operatorSelected, strKey);
                    operatorSelected = "";
                } else if (strBtn.equals(context.getString(R.string.str_percentage))) {
                    if (!operatorSelected.isEmpty()) {
                        strKey = getResult(operatorSelected, strKey);
                        if (!strKey.isEmpty()) {
                            strKey = getPercentage(strKey);
                        }
                    } else {
                        strKey = getPercentage(strKey);
                    }
                    operatorSelected = "";
                }
            }
        }


        return strKey.isEmpty() ? "0" : strKey;
    }

    private String getResult(String operatorSelected, String strKey) {
        String result = "";
        switch (operatorSelected) {
            case "+": {
                String[] tokens = strKey.split("\\+");
                if (tokens.length == 2) {
                    result = String.valueOf(Float.valueOf(tokens[0]) + Float.valueOf(tokens[1]));
                }
                break;
            }
            case "-": {
                String[] tokens = strKey.split("-");
                if (tokens.length == 2) {
                    result = String.valueOf(Float.valueOf(tokens[0]) - Float.valueOf(tokens[1]));
                }
                break;
            }
            case "/": {
                String[] tokens = strKey.split("/");
                if (tokens.length == 2) {
                    result = String.valueOf(Float.valueOf(tokens[0]) / Float.valueOf(tokens[1]));
                }
                break;
            }
            case "x": {
                String[] tokens = strKey.split("x");
                if (tokens.length == 2) {
                    result = String.valueOf(Float.valueOf(tokens[0]) * Float.valueOf(tokens[1]));
                }
                break;
            }
        }
        return result;
    }

    private String getPercentage(String number) {
        try {
            return String.valueOf(Float.valueOf(number) / 100);
        } catch (NumberFormatException e) {
            return number;
        }
    }

    public void setPinMode(boolean pinMode) {
        isPinMode = pinMode;
    }
}
