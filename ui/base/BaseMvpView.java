package com.lionroarsrk.scapp.ui.base;


import androidx.annotation.StringRes;

public interface BaseMvpView {
    void showLoading();

    void hideLoading();

    void onError(@StringRes int resId);

    void onError(String message);

    void showMessage(String message);

    void showMessage(@StringRes int resId);

    void hideKeyboard();

    boolean isPermissionGranted(String permission);

    void requestPermission(String[] permissions, int requestCode);
}
