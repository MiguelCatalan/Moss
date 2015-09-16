package com.miguelcatalan.domain;

import com.miguelcatalan.model.MediaDataSource;
import com.miguelcatalan.model.entities.BookDetail;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

/**
 * @author Miguel Catalan Bañuls
 */
public class GetBookDetailUsecaseController implements GetBookDetailUsecase {

    private final MediaDataSource mMovieDataSource;
    private final String mBookId;
    private final Bus mUiBus;

    public GetBookDetailUsecaseController(String bookId, Bus uiBus, MediaDataSource dataSource) {

        mBookId = bookId;
        mUiBus = uiBus;
        mMovieDataSource = dataSource;
        mUiBus.register(this);
    }

    @Override
    public void requestBookDetail(String movieId) {

        mMovieDataSource.getDetailBook(movieId);
    }

    @Subscribe
    @Override
    public void onBookDetailResponse(BookDetail bookDetail) {

        sendDetailBookToPresenter(bookDetail);
        mUiBus.unregister(this);
    }

    @Override
    public void sendDetailBookToPresenter(BookDetail response) {

        mUiBus.post(response);
    }

    @Override
    public void execute() {

        requestBookDetail(mBookId);
    }
}