package com.miguelcatalan.domain;

import com.miguelcatalan.model.entities.BooksWrapper;
import com.miguelcatalan.model.rest.RestDataSource;
import com.squareup.otto.Bus;

import javax.inject.Inject;

/**
 * @author Miguel Catalan Bañuls
 */
public class GetBooksUsecaseController implements GetBooksUsecase {

    private final RestDataSource mDataSource;
    private final Bus mUiBus;
    private int mCurrentPage = 1;
    private String mQuery;

    @Inject
    public GetBooksUsecaseController(RestDataSource dataSource, Bus uiBus) {

        mDataSource = dataSource;
        mUiBus = uiBus;
        mUiBus.register(this);
    }

    @Override
    public void requestBooks(String query) {
        mQuery = query;
        mDataSource.getBooksByPage(query, 1);
    }

    @Override
    public void sendBooksToPresenter(BooksWrapper response) {

        mUiBus.post(response);
    }

    @Override
    public void unRegister() {

        mUiBus.unregister(this);
    }

    @Override
    public void execute() {
        mCurrentPage++;
        mDataSource.getBooksByPage(mQuery, mCurrentPage);
    }
}