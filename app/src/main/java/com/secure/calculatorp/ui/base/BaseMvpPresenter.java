package com.secure.calculatorp.ui.base;

/**
 * Created by zakir on 06/01/2019.
 */

public interface BaseMvpPresenter<V extends BaseMvpView> {

    void onAttach(V mvpView);
}
