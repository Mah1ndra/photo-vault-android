package com.lionroarsrk.scapp.ui.keypad;

import com.lionroarsrk.scapp.ui.base.BaseMvpView;

import javax.crypto.SecretKey;



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
