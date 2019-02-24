package com.secure.calculatorp.ui.vault.type.photo.dialog;

import android.content.Intent;

import com.secure.calculatorp.ui.base.BaseMvpPresenter;



public interface PhotoDialogPresenter<V extends PhotoDialogView> {

    void onStart();
    void onViewCreated();
    void onRestoreClick();
    void onSelectedData(Intent intent);
    void onAttach(V view);
}
