/*
 * Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://mindorks.com/license/apache-v2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.secure.calculatorp.data;


import android.net.Uri;


import com.secure.calculatorp.data.file.FileHelper;
import com.secure.calculatorp.data.model.FileModel;
import com.secure.calculatorp.data.prefs.PreferencesHelper;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;

import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.inject.Inject;
import javax.inject.Singleton;



@Singleton
public class AppDataManager implements DataManager {

    private static final String TAG = "AppDataManager";

    private final PreferencesHelper mPreferencesHelper;
    private final FileHelper mFileHelper;

    @Inject
    public AppDataManager(PreferencesHelper preferencesHelper, FileHelper mFileHelper) {
        this.mPreferencesHelper = preferencesHelper;
        this.mFileHelper = mFileHelper;
    }


    @Override
    public boolean hasPinCodeEnabled() {
        return mPreferencesHelper.hasPinCodeEnabled();
    }

    @Override
    public void setPinCode(boolean pinEnabled) {
        mPreferencesHelper.setPinCode(pinEnabled);
    }

    @Override
    public boolean getPinCode() {
        return mPreferencesHelper.getPinCode();
    }

    @Override
    public void setTempPin(String pin) {
        mPreferencesHelper.setTempPin(pin);
    }

    @Override
    public String getTempPin() {
        return mPreferencesHelper.getTempPin();
    }

    @Override
    public void removeTempPin() {
        mPreferencesHelper.removeTempPin();
    }

    @Override
    public void setOperator(String pin) {
        mPreferencesHelper.setOperator(pin);
    }

    @Override
    public String getOperator() {
        return mPreferencesHelper.getOperator();
    }

    @Override
    public void setSalt(String salt) {
        mPreferencesHelper.setSalt(salt);
    }

    @Override
    public String getSalt() {
        return mPreferencesHelper.getSalt();
    }

    @Override
    public boolean hasInitializationVector() {
        return mPreferencesHelper.hasInitializationVector();
    }

    @Override
    public void setInitializationVector(String initializationVector) {
        mPreferencesHelper.setInitializationVector(initializationVector);
    }

    @Override
    public String getInitializationVector() {
        return mPreferencesHelper.getInitializationVector();
    }

    @Override
    public HashSet<Uri> getTempImages() {
        return mFileHelper.getTempImages();
    }

    @Override
    public HashSet<Integer> getVideoList() {
        return null;
    }

    @Override
    public HashSet<Integer> getDocumentList() {
        return null;
    }

    @Override
    public boolean deleteImage(Uri uri) {
        return mFileHelper.deleteImage(uri);
    }

    @Override
    public boolean storeImage(FileModel src, SecretKey secretKey) throws IOException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException {
        return mFileHelper.storeImage(src,secretKey);
    }

    @Override
    public boolean storeImage(ArrayList<FileModel> uri, SecretKey secretKey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, IOException {
        return mFileHelper.storeImage(uri,secretKey);
    }

    @Override
    public void createTempImages(SecretKey secretKey) throws IOException,
            InvalidAlgorithmParameterException, NoSuchAlgorithmException,
            InvalidKeyException, NoSuchPaddingException {
        mFileHelper.createTempImages(secretKey);
    }

    @Override
    public void removeTempImages() {
        mFileHelper.removeTempImages();
    }

}
