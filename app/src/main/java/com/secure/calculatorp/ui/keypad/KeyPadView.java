package com.secure.calculatorp.ui.keypad;

import com.secure.calculatorp.ui.base.BaseMvpView;

import javax.crypto.SecretKey;

/**
 * Created by zakir on 30/12/2018.
 */

public interface KeyPadView extends BaseMvpView{

    void showPinDialog();
    void showPinSuccessDialog();
    void showPinErrorToast();
    void setResultView(String result);
    void destroyActivity();
    void moveToVaultActivity();
    void showProgress();
    void hideProgress();
}
