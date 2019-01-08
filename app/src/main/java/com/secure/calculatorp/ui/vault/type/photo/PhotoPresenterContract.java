package com.secure.calculatorp.ui.vault.type.photo;

import com.secure.calculatorp.crypto.CryptoKeyManager;
import com.secure.calculatorp.data.DataManager;
import com.secure.calculatorp.util.StringUtil;

import java.nio.charset.StandardCharsets;
import java.security.KeyStoreException;

import javax.crypto.SecretKey;
import javax.inject.Inject;

/**
 * Created by zakir on 08/01/2019.
 */

public class PhotoPresenterContract<V extends PhotoView> implements PhotoPresenter<V> {

    private V mvpView;

    @Inject
    DataManager dataManager;

    @Inject
    CryptoKeyManager cryptoKeyManager;

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
    public void onViewCreated() {
        try {
            String iv = dataManager.getInitializationVector();
            SecretKey secretKey = cryptoKeyManager.getSecretKey(dataManager.getTempPin().toCharArray());
            if (secretKey != null && !StringUtil.isNullOrEmpty(iv)) {
                mvpView.updateList(dataManager.getImageList(), secretKey, StringUtil.getIvBytesFromString(iv));
            }
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onImageClick(byte[] imageBytes) {
        mvpView.showFullScreenImageView(imageBytes);
    }
}
