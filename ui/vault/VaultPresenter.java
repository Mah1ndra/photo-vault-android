package com.lionroarsrk.scapp.ui.vault;

import android.content.Intent;

import com.lionroarsrk.scapp.ui.base.BaseMvpPresenter;

public interface VaultPresenter<V extends VaultView> extends BaseMvpPresenter<V> {

    void onNavItemSelected(int navigationId);
    void onScreenVisible();
    void onAddClick();
    void onImageSelected(Intent intent);
    void onScreenHidden();
    void onPausedForSelection();
}
