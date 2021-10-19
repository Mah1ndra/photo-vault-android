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


import android.content.Context;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.lionroarsrk.scapp.crypto.operation.AppCryptoOperation;
import com.lionroarsrk.scapp.di.ActivityContext;
import com.lionroarsrk.scapp.ui.keypad.KeyPadPresenter;
import com.lionroarsrk.scapp.ui.keypad.KeyPadPresenterContract;
import com.lionroarsrk.scapp.ui.keypad.KeyPadView;
import com.lionroarsrk.scapp.ui.vault.VaultPresenter;
import com.lionroarsrk.scapp.ui.vault.VaultPresenterContract;
import com.lionroarsrk.scapp.ui.vault.VaultView;
import com.lionroarsrk.scapp.ui.vault.type.photo.PhotoAdapter;
import com.lionroarsrk.scapp.ui.vault.type.photo.PhotoPresenter;
import com.lionroarsrk.scapp.ui.vault.type.photo.PhotoPresenterContract;
import com.lionroarsrk.scapp.ui.vault.type.photo.PhotoView;
import com.lionroarsrk.scapp.ui.vault.type.photo.dialog.PhotoDialogPresenter;
import com.lionroarsrk.scapp.ui.vault.type.photo.dialog.PhotoDialogPresenterContract;
import com.lionroarsrk.scapp.ui.vault.type.photo.dialog.PhotoDialogView;


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
