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

package com.secure.calculatorp.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;


import com.secure.calculatorp.di.ApplicationContext;
import com.secure.calculatorp.di.PreferenceInfo;
import com.secure.calculatorp.util.StringUtil;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class AppPreferencesHelper implements PreferencesHelper {

    private static final String PIN = "com.secure.calculatorp.data.pin_enabled";
    private static final String TEMP_PIN_CODE = "com.secure.calculatorp.data.temp_pin_code";
    private static final String OPERATOR = "com.secure.calculatorp.data.operator";
    private static final String SALT = "com.secure.calculatorp.data.salt";
    private static final String INITIALIZATION_VECTOR = "com.secure.calculatorp.data.initialization_vector";

    private final SharedPreferences mPrefs;

    @Inject
    public AppPreferencesHelper(@ApplicationContext Context context,
                                @PreferenceInfo String prefFileName) {
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }


    @Override
    public boolean hasPinCodeEnabled() {
        return getPinCode();
    }

    @Override
    public void setPinCode(boolean pinEnabled) {
        mPrefs.edit().putBoolean(PIN, pinEnabled).apply();
    }

    @Override
    public boolean getPinCode() {
        return mPrefs.getBoolean(PIN, false);
    }

    @Override
    public void setTempPin(String pin) {
        mPrefs.edit().putString(TEMP_PIN_CODE, pin).apply();
    }

    @Override
    public String getTempPin() {
        return mPrefs.getString(TEMP_PIN_CODE, "");
    }

    @Override
    public void removeTempPin() {
        mPrefs.edit().putString(TEMP_PIN_CODE, "").apply();
    }

    @Override
    public void setOperator(String pin) {
        mPrefs.edit().putString(OPERATOR, pin).apply();
    }

    @Override
    public String getOperator() {
        return mPrefs.getString(OPERATOR, "");
    }

    @Override
    public void setSalt(String salt) {
        mPrefs.edit().putString(SALT, salt).apply();
    }

    @Override
    public String getSalt() {
        return mPrefs.getString(SALT, "");
    }

    @Override
    public boolean hasInitializationVector() {
        return !StringUtil.isNullOrEmpty(getInitializationVector());
    }

    @Override
    public void setInitializationVector(String initializationVector) {
        mPrefs.edit().putString(INITIALIZATION_VECTOR, initializationVector).apply();
    }

    @Override
    public String getInitializationVector() {
        return mPrefs.getString(INITIALIZATION_VECTOR, "");
    }
}
