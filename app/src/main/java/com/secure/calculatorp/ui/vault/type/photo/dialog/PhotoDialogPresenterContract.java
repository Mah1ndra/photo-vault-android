package com.secure.calculatorp.ui.vault.type.photo.dialog;

import android.content.Intent;

import com.secure.calculatorp.data.DataManager;

import javax.inject.Inject;


public class PhotoDialogPresenterContract<V extends PhotoDialogView> implements PhotoDialogPresenter<V> {

    V mvpView;

    @Inject
    DataManager dataManager;

    @Inject
    public PhotoDialogPresenterContract() {

    }

    @Override
    public void onStart() {
        mvpView.switchToFullScreen();
    }

    @Override
    public void onViewCreated() {
        mvpView.initArguments();
    }

    @Override
    public void onRestoreClick() {
        mvpView.transmitRestoreClick();
    }

    @Override
    public void onSelectedData(Intent intent) {

    }

    @Override
    public void onAttach(V view) {
        mvpView = view;
    }
}
