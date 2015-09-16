package com.miguelcatalan.android.di.components;

import com.miguelcatalan.android.di.modules.BooksUsecasesModule;
import com.miguelcatalan.android.di.scopes.PerActivity;
import com.miguelcatalan.android.views.activities.BooksActivity;

import dagger.Component;

/**
 * @author Miguel Catalan Bañuls
 */
@PerActivity
@Component(dependencies = AppComponent.class, modules = BooksUsecasesModule.class)
public interface BooksUsecasesComponent {

    void inject (BooksActivity booksActivity);
}