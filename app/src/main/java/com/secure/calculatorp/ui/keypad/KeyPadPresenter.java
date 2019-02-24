package com.secure.calculatorp.ui.keypad;


import com.secure.calculatorp.ui.base.BaseMvpPresenter;


public interface KeyPadPresenter<V extends KeyPadView> extends BaseMvpPresenter<V>{

    void handleKeyInput(String key);
    void onVisibleScreen();
    void onHiddenScreen();
}
