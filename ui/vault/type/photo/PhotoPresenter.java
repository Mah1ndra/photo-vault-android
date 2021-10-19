package com.lionroarsrk.scapp.ui.vault.type.photo;

import android.net.Uri;

import com.lionroarsrk.scapp.ui.base.BaseMvpPresenter;
import com.lionroarsrk.scapp.ui.base.BaseMvpView;


public interface PhotoPresenter<V extends BaseMvpView> extends BaseMvpPresenter<V> {

    void onVisibleScreen();
    void onRefresh();
    void onUpdate();
    void onRestoreClicked(Uri uri);
    void onViewCreated();
    void onImageClick(Uri uri);
}
