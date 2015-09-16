package com.miguelcatalan.android.mvp.views;

import com.miguelcatalan.model.entities.Book;

import java.util.List;

/**
 * @author Miguel Catalan Bañuls
 */
public interface BooksView extends MVPView {

    void showBooks(List<Book> bookList);

    void showLoading();

    void hideLoading();

    void showLoadingLabel();

    void hideActionLabel();

    boolean isTheListEmpty();

    void appendBooks(List<Book> movieList);

    void clearBooks();
}