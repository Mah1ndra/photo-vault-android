package com.secure.calculatorp.ui.base;

import android.support.annotation.StringRes;

/**
 * Created by zakir on 06/01/2019.
 */

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
