package com.secure.calculatorp.ui.vault.type.photo;

import android.net.Uri;

import com.secure.calculatorp.crypto.CryptoManager;
import com.secure.calculatorp.data.DataManager;

import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;

import javax.crypto.SecretKey;
import javax.inject.Inject;



public class PhotoPresenterContract<V extends PhotoView> implements PhotoPresenter<V> {

    private V mvpView;

    @Inject
    DataManager dataManager;

    @Inject
    CryptoManager cryptoKeyManager;

    @Inject
    public PhotoPresenterContract() {

    }

    @Override
    public void onAttach(V mvpView) {
        this.mvpView = mvpView;
    }

    @Override
    public void onVisibleScreen() {

    }

    @Override
    public void onRestoreClicked(Uri uri) {
        dataManager.restoreImageToPublicStorage(uri);
    }

    @Override
    public void onUpdate() {
        onViewCreated();
    }

    @Override
    public void onViewCreated() {
        try {
            SecretKey secretKey = (SecretKey) cryptoKeyManager.retrieveKey(dataManager.getTempPin().toCharArray());
            if (secretKey != null) {
                mvpView.updateList(dataManager.getTemporaryImages());
            }
        } catch (KeyStoreException | UnrecoverableEntryException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRefresh() {
        onViewCreated();
    }

    @Override
    public void onImageClick(Uri uri) {
        mvpView.showFullScreenImageView(uri);
    }
}
