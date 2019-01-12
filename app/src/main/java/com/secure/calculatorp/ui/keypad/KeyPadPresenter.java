package com.secure.calculatorp.ui.keypad;


import com.secure.calculatorp.ui.base.BaseMvpPresenter;

/**
 * Created by zakir on 30/12/2018.
 */

public interface KeyPadPresenter<V extends KeyPadView> extends BaseMvpPresenter<V>{

    void handleKeyInput(String key);
    void onVisibleScreen();
    void onHiddenScreen();
}
