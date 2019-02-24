package com.secure.calculatorp.ui.base;



public interface BaseMvpPresenter<V extends BaseMvpView> {

    void onAttach(V mvpView);
}
