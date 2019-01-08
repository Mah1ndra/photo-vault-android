package com.secure.calculatorp.ui.vault;

import android.Manifest;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Base64;

import com.secure.calculatorp.R;
import com.secure.calculatorp.crypto.CryptoKeyManager;
import com.secure.calculatorp.data.DataManager;
import com.secure.calculatorp.di.ActivityContext;
import com.secure.calculatorp.util.AppConstants;
import com.secure.calculatorp.util.StringUtil;


import java.nio.charset.StandardCharsets;
import java.security.KeyStoreException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.crypto.SecretKey;
import javax.inject.Inject;

/**
 * Created by zakir on 06/01/2019.
 */

public class VaultPresenterContract<V extends VaultView> implements VaultPresenter<V> {


    @Inject
    DataManager dataManager;

    @Inject
    CryptoKeyManager cryptoKeyManager;

    private VaultView mvpView;

    @Inject
    public VaultPresenterContract() {

    }

    @Override
    public void onAttach(V mvpView) {
        this.mvpView = mvpView;
        dataManager.setTempPin(mvpView.getPinCode());
    }

    @Override
    public void onNavItemSelected(int navigationId) {
        if (navigationId == R.id.navigation_photos) {
            mvpView.switchFragment(AppConstants.Fragment.PHOTO);
        } else if (navigationId == R.id.navigation_videos) {
//            mvpView.switchFragment(AppConstants.Fragment.PHOTO);
        } else if (navigationId == R.id.navigation_file) {
//            mvpView.switchFragment(AppConstants.Fragment.PHOTO);
        }
    }

    @Override
    public void onVisibleScreen() {
        if (!mvpView.isPermissionGranted(Manifest.permission.READ_EXTERNAL_STORAGE)
                || !mvpView.isPermissionGranted(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            mvpView.requestPermission(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, AppConstants.PERMISSION_EXTERNAL_STORAGE_CODE);
        } else {
            mvpView.switchFragment(AppConstants.Fragment.PHOTO);
        }
    }

    @Override
    public void onAddClick() {
        mvpView.openFileBrowser();
    }

    @Override
    public void onSelectedData(Intent intent) {
        encryptAndStoreData(intent);
    }

    private void encryptAndStoreData(Intent intent) {
        byte[] iv;
        if (!dataManager.hasInitializationVector()) {
            iv = cryptoKeyManager.generateRandom(16);
            dataManager.setInitializationVector(StringUtil.getIvStringFromByteArray(iv));
        } else {
            iv = StringUtil.getIvBytesFromString(dataManager.getInitializationVector());
        }

        SecretKey secretKey = null;
        try {
            if (!StringUtil.isNullOrEmpty(dataManager.getTempPin())) {
                secretKey = cryptoKeyManager.getSecretKey(dataManager.getTempPin().toCharArray());
            }
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }

        if (intent.getData() != null && secretKey != null) {
            dataManager.storeImage(intent.getData(), secretKey, iv);
        } else if (intent.getClipData() != null) {
            dataManager.storeImage(getUriListFromIntent(intent.getClipData()), secretKey, iv);
        }
    }

    private ArrayList<Uri> getUriListFromIntent(ClipData clipData) {

        ArrayList<Uri> mArrayUri = new ArrayList<>();

        for (int i = 0; i < clipData.getItemCount(); i++) {
            ClipData.Item item = clipData.getItemAt(i);
            Uri uri = item.getUri();
            mArrayUri.add(uri);

        }
        return mArrayUri;
    }
}
