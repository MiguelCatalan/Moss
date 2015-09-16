package com.miguelcatalan.android.mvp.presenters;

import com.miguelcatalan.android.mvp.views.BooksView;
import com.miguelcatalan.domain.GetBooksUsecase;
import com.miguelcatalan.model.entities.BooksWrapper;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

/**
 * @author Miguel Catalan Bañuls
 */
public class BooksPresenter extends Presenter {

    private final Bus mBus;
    private GetBooksUsecase mGetBooks;
    private BooksView mBooksView;

    private boolean isLoading = false;
    private boolean mRegistered;

    @Inject
    public BooksPresenter(GetBooksUsecase getBooksUsecase, Bus bus) {
        mGetBooks = getBooksUsecase;
        mBus = bus;
    }

    public void attachView(BooksView booksView) {

        mBooksView = booksView;
    }

    @Subscribe
    public void onBooksReceived(BooksWrapper moviesWrapper) {

        if (mBooksView.isTheListEmpty()) {

            mBooksView.hideLoading();
            mBooksView.showBooks(moviesWrapper.getBooks());

        } else {

            mBooksView.hideActionLabel();
            mBooksView.appendBooks(moviesWrapper.getBooks());
        }

        isLoading = false;
    }

    public void searchBooks(String query){
        mBooksView.clearBooks();
        mBooksView.showLoading();
        mGetBooks.requestBooks(query);
    }

    public void onEndListReached() {

        mGetBooks.execute();
        mBooksView.showLoadingLabel();
        isLoading = true;
    }

    @Override
    public void start() {

        if (mBooksView.isTheListEmpty()) {

            mBus.register(this);
            mRegistered = true;

            mBooksView.showLoading();
            mGetBooks.requestBooks("android");
        }
    }

    @Override
    public void stop() {

    }

    public boolean isLoading() {

        return isLoading;
    }

    public void setLoading(boolean isLoading) {

        this.isLoading = isLoading;
    }
}