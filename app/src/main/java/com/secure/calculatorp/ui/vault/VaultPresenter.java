package com.secure.calculatorp.ui.vault;

import android.content.Intent;

import com.secure.calculatorp.ui.base.BaseMvpPresenter;

/**
 * Created by zakir on 06/01/2019.
 */

public interface VaultPresenter<V extends VaultView> extends BaseMvpPresenter<V> {

    void onNavItemSelected(int navigationId);
    void onVisibleScreen();
    void onAddClick();
    void onImageSelected(Intent intent);
}
