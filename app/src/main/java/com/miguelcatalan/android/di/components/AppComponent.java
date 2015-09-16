package com.miguelcatalan.android.di.components;

import com.miguelcatalan.android.di.modules.ApplicationModule;
import com.miguelcatalan.android.di.modules.DomainModule;
import com.miguelcatalan.model.rest.RestBookSource;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        DomainModule.class,
})

public interface AppComponent {

    Bus bus();

    RestBookSource restBookSource();
}
