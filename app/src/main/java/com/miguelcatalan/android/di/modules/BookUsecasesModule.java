package com.miguelcatalan.android.di.modules;

import com.miguelcatalan.domain.GetBookDetailUsecase;
import com.miguelcatalan.domain.GetBookDetailUsecaseController;
import com.miguelcatalan.model.rest.RestBookSource;
import com.squareup.otto.Bus;

import dagger.Module;
import dagger.Provides;

/**
 * @author Miguel Catalan Bañuls
 */
@Module
public class BookUsecasesModule {

    private final String bookId;

    public BookUsecasesModule(String bookId) {

        this.bookId = bookId;
    }

    @Provides
    GetBookDetailUsecase provideGetBookDetailUsecase(Bus bus, RestBookSource movieSource) {
        return new GetBookDetailUsecaseController(bookId, bus, movieSource);
    }
}