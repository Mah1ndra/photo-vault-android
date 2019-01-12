package com.secure.calculatorp.ui.vault;

import android.Manifest;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;

import com.secure.calculatorp.R;
import com.secure.calculatorp.crypto.CryptoManager;
import com.secure.calculatorp.data.DataManager;
import com.secure.calculatorp.data.model.FileModel;
import com.secure.calculatorp.util.AppConstants;
import com.secure.calculatorp.util.CommonUtils;
import com.secure.calculatorp.util.StringUtil;


import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.util.ArrayList;

import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.inject.Inject;

/**
 * Created by zakir on 06/01/2019.
 */

public class VaultPresenterContract<V extends VaultView> implements VaultPresenter<V> {


    @Inject
    DataManager dataManager;

    @Inject
    CryptoManager cryptoManager;

    private VaultView mvpView;
    private boolean isPausedForSelection = false;

    @Inject
    public VaultPresenterContract() {

    }

    @Override
    public void onAttach(V mvpView) {
        this.mvpView = mvpView;
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
    public void onScreenVisible() {
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
    public void onImageSelected(Intent intent) {
        isPausedForSelection = false;
        encryptData(intent);
    }

    @Override
    public void onScreenHidden() {
        if (!isPausedForSelection) {
            dataManager.removeTempImages();
            mvpView.destroyActivity();
        }
    }

    @Override
    public void onPausedForSelection() {
        isPausedForSelection = true;
    }

    private void encryptData(Intent intent) {

        SecretKey secretKey = getKey();

        if (secretKey != null) {
            if (encryptData(intent, secretKey)) {
                try {
                    dataManager.createTempImages(secretKey);
                } catch (IOException | NoSuchAlgorithmException
                        | NoSuchPaddingException
                        | InvalidAlgorithmParameterException | InvalidKeyException e) {
                    mvpView.encryptionErrorDialog();
                }
            }
        }
    }

    private boolean encryptData(Intent intent, SecretKey secretKey) {
        try {
            if (intent.getData() != null) {
                return dataManager.storeImage(createFileModel(intent.getData()), secretKey);
            } else if (intent.getClipData() != null) {
                return dataManager.storeImage(getUriListFromIntent(intent.getClipData()), secretKey);
            }
        } catch (IOException | NoSuchAlgorithmException | InvalidKeyException
                | InvalidAlgorithmParameterException | NoSuchPaddingException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    private FileModel createFileModel(Uri uri) {
        return new FileModel(uri, CommonUtils.generateRandom(16));
    }

    private SecretKey getKey() {
        SecretKey secretKey = null;
        try {
            if (!StringUtil.isNullOrEmpty(dataManager.getTempPin())) {
                secretKey = (SecretKey) cryptoManager.retrieveKey(dataManager.getTempPin().toCharArray());
            }
        } catch (KeyStoreException | UnrecoverableEntryException | NoSuchAlgorithmException e) {
            return null;
        }
        return secretKey;
    }

    private ArrayList<FileModel> getUriListFromIntent(ClipData clipData) {

        ArrayList<FileModel> fileModels = new ArrayList<>();

        for (int i = 0; i < clipData.getItemCount(); i++) {
            ClipData.Item item = clipData.getItemAt(i);
            FileModel fileModel = new FileModel(item.getUri(), CommonUtils.generateRandom(16));
            fileModels.add(fileModel);
        }
        return fileModels;
    }
}
