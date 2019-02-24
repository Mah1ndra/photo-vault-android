package com.secure.calculatorp;

import android.app.Application;

import com.secure.calculatorp.data.DataManager;
import com.secure.calculatorp.di.component.ApplicationComponent;
import com.secure.calculatorp.di.component.DaggerApplicationComponent;
import com.secure.calculatorp.di.module.ApplicationModule;

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
