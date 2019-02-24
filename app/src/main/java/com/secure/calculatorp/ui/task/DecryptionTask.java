package com.secure.calculatorp.ui.task;

import com.secure.calculatorp.data.DataManager;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;


public class DecryptionTask extends BaseAsyncTask<SecretKey, String, Boolean> {

    private DataManager dataManager;
    private DecryptionTaskCallback decryptionTaskCallback;

    public DecryptionTask(DataManager dataManager,
                          DecryptionTaskCallback decryptionTaskCallback) {
        super(decryptionTaskCallback);
        this.dataManager = dataManager;
        this.decryptionTaskCallback = decryptionTaskCallback;
    }

    public interface DecryptionTaskCallback extends AsyncCallback{
        void onDecrypted();
        void onError();
    }


    @Override
    protected Boolean doInBackground(SecretKey... params) {
        SecretKey secretKey = params[0];
        try {
            dataManager.createTemporaryImages(secretKey);
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
        super.onPostExecute(result);
    }

}