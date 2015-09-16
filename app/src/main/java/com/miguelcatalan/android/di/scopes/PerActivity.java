package com.miguelcatalan.android.di.scopes;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Miguel Catalan Bañuls
 */
@Scope
@Retention(RUNTIME)
public @interface PerActivity {
}