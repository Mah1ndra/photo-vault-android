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


import android.content.Context;

import com.lionroarsrk.scapp.di.ActivityContext;
import com.lionroarsrk.scapp.di.PerActivity;
import com.lionroarsrk.scapp.di.module.ActivityModule;
import com.lionroarsrk.scapp.ui.keypad.KeyPadActivity;
import com.lionroarsrk.scapp.ui.vault.VaultActivity;
import com.lionroarsrk.scapp.ui.vault.type.photo.PhotoFragment;
import com.lionroarsrk.scapp.ui.vault.type.photo.dialog.PhotoDialogFragment;

import dagger.Component;


@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    @ActivityContext
    Context context();

    void inject(VaultActivity activity);

    void inject(KeyPadActivity activity);

    void inject(PhotoFragment fragment);

    void inject(PhotoDialogFragment fragment);

}
