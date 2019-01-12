package com.secure.calculatorp.ui.task;

import android.os.AsyncTask;

import com.secure.calculatorp.data.DataManager;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 * Created by zakir on 11/01/2019.
 */

public class EncryptionTask extends AsyncTask<SecretKey, String, Boolean> {

    private DataManager dataManager;
    private DecryptionTaskCallback decryptionTaskCallback;

    public EncryptionTask(DataManager dataManager,
                          DecryptionTaskCallback decryptionTaskCallback) {
        this.dataManager = dataManager;
        this.decryptionTaskCallback = decryptionTaskCallback;
    }

    public interface DecryptionTaskCallback {
        void onDecrypted();

        void onError();
    }


    @Override
    protected Boolean doInBackground(SecretKey... params) {
        SecretKey secretKey = params[0];
        try {
            dataManager.createTempImages(secretKey);
        } catch (IOException | NoSuchAlgorithmException | NoSuchPaddingException
                | InvalidAlgorithmParameterException | InvalidKeyException e) {
            return false;
        }
        return true;
    }


    @Override
    protected void onPostExecute(Boolean result) {
        if (result) {
            decryptionTaskCallback.onDecrypted();
        } else {
            decryptionTaskCallback.onError();
        }
    }


    @Override
    protected void onPreExecute() {

    }


    @Override
    protected void onProgressUpdate(String... text) {

    }
}