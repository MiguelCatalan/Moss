package com.miguelcatalan.android.di.components;

import com.miguelcatalan.android.di.modules.BookUsecasesModule;
import com.miguelcatalan.android.di.scopes.PerActivity;
import com.miguelcatalan.android.views.activities.BookDetailActivity;

import dagger.Component;

/**
 * @author Miguel Catalan Bañuls
 */
@PerActivity
@Component(dependencies = AppComponent.class, modules = BookUsecasesModule.class)
public interface BookUsecasesComponent {

    void inject(BookDetailActivity bookDetailActivity);
}