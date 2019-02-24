package com.secure.calculatorp.ui.vault;

import android.content.Intent;

import com.secure.calculatorp.ui.base.BaseMvpPresenter;

public interface VaultPresenter<V extends VaultView> extends BaseMvpPresenter<V> {

    void onNavItemSelected(int navigationId);
    void onScreenVisible();
    void onAddClick();
    void onImageSelected(Intent intent);
    void onScreenHidden();
    void onPausedForSelection();
}
