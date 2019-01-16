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

package com.secure.calculatorp.di.module;


import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.secure.calculatorp.crypto.operation.AppCryptoOperation;
import com.secure.calculatorp.di.ActivityContext;
import com.secure.calculatorp.ui.keypad.KeyPadPresenter;
import com.secure.calculatorp.ui.keypad.KeyPadPresenterContract;
import com.secure.calculatorp.ui.keypad.KeyPadView;
import com.secure.calculatorp.ui.vault.VaultPresenter;
import com.secure.calculatorp.ui.vault.VaultPresenterContract;
import com.secure.calculatorp.ui.vault.VaultView;
import com.secure.calculatorp.ui.vault.type.photo.PhotoAdapter;
import com.secure.calculatorp.ui.vault.type.photo.PhotoPresenter;
import com.secure.calculatorp.ui.vault.type.photo.PhotoPresenterContract;
import com.secure.calculatorp.ui.vault.type.photo.PhotoView;
import com.secure.calculatorp.ui.vault.type.photo.dialog.PhotoDialogPresenter;
import com.secure.calculatorp.ui.vault.type.photo.dialog.PhotoDialogPresenterContract;
import com.secure.calculatorp.ui.vault.type.photo.dialog.PhotoDialogView;


import java.util.ArrayList;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class ActivityModule {

    private AppCompatActivity mActivity;

    public ActivityModule(AppCompatActivity activity) {
        this.mActivity = activity;
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(AppCompatActivity activity) {
        return new LinearLayoutManager(activity);
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }


    @Provides
    KeyPadPresenter<KeyPadView> provideKeyPadMvpPresenter(
            KeyPadPresenterContract<KeyPadView> presenter) {
        return presenter;
    }

    @Provides
    PhotoPresenter<PhotoView> providePhotoMvpPresenter(
            PhotoPresenterContract<PhotoView> presenter) {
        return presenter;
    }

    @Provides
    VaultPresenter<VaultView> provideVaultPresenter(
            VaultPresenterContract<VaultView> presenter) {
        return presenter;
    }

    @Provides
    PhotoDialogPresenter<PhotoDialogView> providePhotoDialogPresenter(
            PhotoDialogPresenterContract<PhotoDialogView> presenter) {
        return presenter;
    }

    @Provides
    PhotoAdapter providePhotoAdapter() {
        return new PhotoAdapter(new ArrayList<>());
    }

    @Provides
    FragmentManager provideFragmentManager() {
        return mActivity.getSupportFragmentManager();
    }

}
