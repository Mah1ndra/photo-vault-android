package com.secure.calculatorp.ui.task;

import com.secure.calculatorp.data.DataManager;
import com.secure.calculatorp.data.model.FileModel;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;



public class EncryptionTask extends BaseAsyncTask<SecretKey, String, Boolean> {

    private DataManager dataManager;
    private ArrayList<FileModel> fileModels;
    private EncryptionTaskCallback encryptionTaskCallback;

    public EncryptionTask(DataManager dataManager,
                          ArrayList<FileModel> fileModels,
                          EncryptionTaskCallback encryptionTaskCallback) {
        super(encryptionTaskCallback);
        this.dataManager = dataManager;
        this.fileModels = fileModels;
        this.encryptionTaskCallback = encryptionTaskCallback;
    }

    public interface EncryptionTaskCallback extends BaseAsyncTask.AsyncCallback{
        void onEncrypted();

        void onError();
    }


    @Override
    protected Boolean doInBackground(SecretKey... params) {
        SecretKey secretKey = params[0];
        try {
            dataManager.storeEncryptedImage(fileModels, secretKey);
        } catch (IOException | NoSuchAlgorithmException | NoSuchPaddingException
                | InvalidAlgorithmParameterException | InvalidKeyException e) {
            return false;
        }
        return true;
    }


    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        if (result) {
            encryptionTaskCallback.onEncrypted();
        } else {
            encryptionTaskCallback.onError();
        }
    }
}