package com.miguelcatalan.domain;

import com.miguelcatalan.model.entities.BooksWrapper;

/**
 * @author Miguel Catalan Bañuls
 */
public interface GetBooksUsecase extends Usecase {

    void requestBooks(String query);

    void sendBooksToPresenter(BooksWrapper response);

    void unRegister();
}
