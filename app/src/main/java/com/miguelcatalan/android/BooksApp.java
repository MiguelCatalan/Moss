package com.miguelcatalan.android;

import android.app.Application;

import com.miguelcatalan.android.di.components.AppComponent;
import com.miguelcatalan.android.di.components.DaggerAppComponent;
import com.miguelcatalan.android.di.modules.ApplicationModule;
import com.miguelcatalan.android.di.modules.DomainModule;

/**
 * @author Miguel Catalan Bañuls
 */
public class BooksApp extends Application {

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        this.initializeDependencyInjector();
    }

    private void initializeDependencyInjector() {

        mAppComponent =  DaggerAppComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .domainModule(new DomainModule())
                .build();
    }

    public AppComponent getAppComponent() {

        return mAppComponent;
    }
}