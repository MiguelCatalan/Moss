package com.miguelcatalan.android.di.modules;

import com.miguelcatalan.model.rest.RestBookSource;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author Miguel Catalan Bañuls
 */
@Module
public class DomainModule {

    @Provides
    @Singleton
    Bus provideBus() {
        return new Bus();
    }

    @Provides
    @Singleton
    RestBookSource provideDataSource(Bus bus) {
        return new RestBookSource(bus);
    }

}