package com.lionroarsrk.scapp.ui.base;



public interface BaseMvpPresenter<V extends BaseMvpView> {

    void onAttach(V mvpView);
}
