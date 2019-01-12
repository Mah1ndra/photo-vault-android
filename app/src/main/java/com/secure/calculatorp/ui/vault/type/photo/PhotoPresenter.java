package com.secure.calculatorp.ui.vault.type.photo;

import android.net.Uri;

import com.secure.calculatorp.ui.base.BaseMvpPresenter;
import com.secure.calculatorp.ui.base.BaseMvpView;

/**
 * Created by zakir on 08/01/2019.
 */

public interface PhotoPresenter<V extends BaseMvpView> extends BaseMvpPresenter<V> {

    void onVisibleScreen();
    void onViewCreated();
    void onImageClick(Uri uri);
}
