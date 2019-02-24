package com.secure.calculatorp.ui.vault;

import android.Manifest;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;

import com.secure.calculatorp.R;
import com.secure.calculatorp.crypto.CryptoManager;
import com.secure.calculatorp.data.DataManager;
import com.secure.calculatorp.data.model.FileModel;
import com.secure.calculatorp.ui.task.EncryptionTask;
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


public class VaultPresenterContract<V extends VaultView> implements VaultPresenter<V> {


    @Inject
    DataManager dataManager;

    @Inject
    CryptoManager cryptoManager;

    private VaultView vaultView;
    private boolean isPausedForSelection = false;

    @Inject
    public VaultPresenterContract() {

    }

    @Override
    public void onAttach(V mvpView) {
        this.vaultView = mvpView;
    }

    @Override
    public void onNavItemSelected(int navigationId) {
        if (navigationId == R.id.navigation_photos) {
            vaultView.switchFragment(AppConstants.Fragment.PHOTO);
        } else if (navigationId == R.id.navigation_videos) {
//            vaultView.switchFragment(AppConstants.Fragment.PHOTO);
        } else if (navigationId == R.id.navigation_file) {
//            vaultView.switchFragment(AppConstants.Fragment.PHOTO);
        }
    }

    @Override
    public void onScreenVisible() {
        isPausedForSelection = false;
        if (!vaultView.isPermissionGranted(Manifest.permission.READ_EXTERNAL_STORAGE)
                || !vaultView.isPermissionGranted(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            isPausedForSelection = true;
            vaultView.requestPermission(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, AppConstants.PERMISSION_EXTERNAL_STORAGE_CODE);
        } else {
            vaultView.switchFragment(AppConstants.Fragment.PHOTO);
        }
    }

    @Override
    public void onAddClick() {
        vaultView.openFileBrowser();
    }

    @Override
    public void onImageSelected(Intent intent) {
        isPausedForSelection = false;
        encryptData(intent);
    }

    @Override
    public void onScreenHidden() {
        if (!isPausedForSelection) {
            dataManager.removeTemporaryImages();
            vaultView.destroyActivity();
        }
    }

    @Override
    public void onPausedForSelection() {
        isPausedForSelection = true;
    }

    private void encryptData(Intent intent) {

        SecretKey secretKey = getKey();

        if (secretKey != null) {

            EncryptionTask encryptionTask = new EncryptionTask(dataManager, createData(intent),
                    new EncryptionTask.EncryptionTaskCallback() {
                @Override
                public void onEncrypted() {
                    try {
                        dataManager.createTemporaryImages(secretKey);
                    } catch (IOException | NoSuchAlgorithmException
                            | NoSuchPaddingException
                            | InvalidAlgorithmParameterException | InvalidKeyException e) {
                        vaultView.encryptionErrorDialog();
                    }
                }

                @Override
                public void onError() {

                }

                @Override
                public void onStart() {
                    vaultView.showLoading();
                }

                @Override
                public void onStop() {
                    vaultView.hideLoading();
                }
            });

            encryptionTask.execute(secretKey);
        }
    }

    private ArrayList<FileModel> createData(Intent intent) {
        try {
            if (intent.getData() != null) {
                return createFileModel(intent.getData());
            } else if (intent.getClipData() != null) {
                return getUriListFromIntent(intent.getClipData());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    private ArrayList<FileModel> createFileModel(Uri uri) {
        ArrayList<FileModel> fileModels = new ArrayList<>();
        fileModels.add(new FileModel(uri, CommonUtils.generateRandom(16)));
        return fileModels;
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
