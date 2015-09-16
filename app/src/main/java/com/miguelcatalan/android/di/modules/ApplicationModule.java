package com.miguelcatalan.android.di.modules;

import android.content.Context;

import com.miguelcatalan.android.BooksApp;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author Miguel Catalan Bañuls
 */
@Module
public class ApplicationModule {

    private final BooksApp application;

    public ApplicationModule(BooksApp application) {

        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return application;
    }

    public static class BookUsecasesModule {
    }
}