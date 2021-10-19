package com.lionroarsrk.scapp.ui.keypad;


import com.lionroarsrk.scapp.ui.base.BaseMvpPresenter;


public interface KeyPadPresenter<V extends KeyPadView> extends BaseMvpPresenter<V>{

    void handleKeyInput(String key);
    void onVisibleScreen();
    void onHiddenScreen();
}
