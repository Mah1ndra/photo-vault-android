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

package com.lionroarsrk.scapp.di.component;

import android.app.Application;
import android.content.Context;

import com.lionroarsrk.scapp.VaultApp;
import com.lionroarsrk.scapp.crypto.CryptoManager;
import com.lionroarsrk.scapp.crypto.key.KeyGen;
import com.lionroarsrk.scapp.data.DataManager;
import com.lionroarsrk.scapp.di.ApplicationContext;
import com.lionroarsrk.scapp.di.module.ApplicationModule;
import com.lionroarsrk.scapp.threading.ThreadExecutor;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(VaultApp app);

    @ApplicationContext
    Context context();

    Application application();

    DataManager getDataManager();

    CryptoManager getCryptoManager();

    KeyGen getKengen();

    ThreadExecutor getThreadExecutor();
}