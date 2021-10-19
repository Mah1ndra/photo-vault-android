package com.lionroarsrk.scapp;

import android.app.Application;

import com.lionroarsrk.scapp.data.DataManager;
import com.lionroarsrk.scapp.di.component.ApplicationComponent;
//import com.lionroarsrk.scapp.di.component.DaggerApplicationComponent;
import com.lionroarsrk.scapp.di.module.ApplicationModule;

import javax.inject.Inject;


public class VaultApp extends Application {


    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();

        mApplicationComponent.inject(this);


    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }


    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }
}
