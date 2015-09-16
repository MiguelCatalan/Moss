package com.miguelcatalan.android.di.modules;

import com.miguelcatalan.domain.GetBooksUsecase;
import com.miguelcatalan.domain.GetBooksUsecaseController;
import com.miguelcatalan.model.rest.RestBookSource;
import com.squareup.otto.Bus;

import dagger.Module;
import dagger.Provides;

/**
 * @author Miguel Catalan Bañuls
 */
@Module
public class BooksUsecasesModule {

    @Provides
    GetBooksUsecase provideMoviesUsecase(Bus bus, RestBookSource movieSource) {
        return new GetBooksUsecaseController(movieSource, bus);
    }
}