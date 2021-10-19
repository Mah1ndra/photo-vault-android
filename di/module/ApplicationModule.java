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

package com.lionroarsrk.scapp.di.module;

import android.app.Application;
import android.content.Context;


import com.lionroarsrk.scapp.crypto.AppCryptoManager;
import com.lionroarsrk.scapp.crypto.CryptoManager;
import com.lionroarsrk.scapp.crypto.cipher.AESGCMCipherAlgorithm;
import com.lionroarsrk.scapp.crypto.cipher.CipherAlgorithm;
import com.lionroarsrk.scapp.crypto.key.CryptoKeyManager;
import com.lionroarsrk.scapp.crypto.key.KeyGen;
import com.lionroarsrk.scapp.crypto.key.KeyManager;
import com.lionroarsrk.scapp.crypto.key.PBKDF2KeyGenerator;
import com.lionroarsrk.scapp.crypto.operation.AppCryptoOperation;
import com.lionroarsrk.scapp.crypto.operation.CryptoOperation;
import com.lionroarsrk.scapp.data.AppDataManager;
import com.lionroarsrk.scapp.data.DataManager;
import com.lionroarsrk.scapp.data.file.AppCryptoFileHelper;
import com.lionroarsrk.scapp.data.file.FileHelper;
import com.lionroarsrk.scapp.data.prefs.AppPreferencesHelper;
import com.lionroarsrk.scapp.data.prefs.PreferencesHelper;
import com.lionroarsrk.scapp.di.ApplicationContext;
import com.lionroarsrk.scapp.di.PreferenceInfo;
import com.lionroarsrk.scapp.ui.keypad.Calculator;
import com.lionroarsrk.scapp.ui.keypad.PinValidator;
import com.lionroarsrk.scapp.util.AppConstants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;



@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }


    @Provides
    @Singleton
    DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }


    @Provides
    @PreferenceInfo
    String providePreferenceName() {
        return AppConstants.PREF_NAME;
    }

    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper(AppPreferencesHelper appPreferencesHelper) {
        return appPreferencesHelper;
    }

    @Provides
    @Singleton
    FileHelper provideFileHelper(AppCryptoFileHelper appPreferencesHelper) {
        return appPreferencesHelper;
    }

    @Provides
    @Singleton
    CryptoManager provideCryptoManager(AppCryptoManager appCryptoManager){
        return appCryptoManager;
    }

    @Provides
    @Singleton
    CryptoOperation provideCryptoCipherManager(AppCryptoOperation appCryptoOperation){
        return appCryptoOperation;
    }

    @Provides
    @Singleton
    KeyGen provideKeyGenerator() {
        return new PBKDF2KeyGenerator();
    }

    @Provides
    @Singleton
    KeyManager provideCryptoKeyManager(CryptoKeyManager cryptoKeyManager) {
        return cryptoKeyManager;
    }

    @Provides
    CipherAlgorithm provideCipherAlgorithm(AESGCMCipherAlgorithm aesgcmCipherAlgorithm) {
        return aesgcmCipherAlgorithm;
    }

    @Provides
    Calculator provideCalculator(Calculator calculator) {
        return calculator;
    }


    @Provides
    @Singleton
    PinValidator getPinCodeValidator(PinValidator pinValidator) {
        return pinValidator;
    }
}
