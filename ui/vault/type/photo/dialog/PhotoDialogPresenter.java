package com.lionroarsrk.scapp.ui.vault.type.photo.dialog;

import android.content.Intent;

import com.lionroarsrk.scapp.ui.base.BaseMvpPresenter;



public interface PhotoDialogPresenter<V extends PhotoDialogView> {

    void onStart();
    void onViewCreated();
    void onRestoreClick();
    void onSelectedData(Intent intent);
    void onAttach(V view);
}
