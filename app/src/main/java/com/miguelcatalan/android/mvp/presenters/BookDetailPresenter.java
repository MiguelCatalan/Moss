package com.miguelcatalan.android.mvp.presenters;

import com.miguelcatalan.android.mvp.views.DetailView;
import com.miguelcatalan.domain.GetBookDetailUsecase;
import com.miguelcatalan.model.entities.BookDetail;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

/**
 * @author Miguel Catalan Bañuls
 */
public class BookDetailPresenter extends Presenter {

    private final Bus mBus;
    private DetailView mBookDetailView;
    private GetBookDetailUsecase mGetBookDetail;

    @Inject
    public BookDetailPresenter(GetBookDetailUsecase getBookDetailUsecase, Bus bus) {
        mGetBookDetail = getBookDetailUsecase;
        mBus = bus;
    }

    public void attachView(DetailView bookDetailView) {

        mBookDetailView = bookDetailView;
        mGetBookDetail.execute();
    }

    public void showTitle(String title) {

        mBookDetailView.setTitle(title);
    }

    public void showAuthors(String author) {
        mBookDetailView.setAuthor(author);
    }

    public void showDescription(String description) {

        mBookDetailView.setDescription(description);
    }

    public void showISBN(String isbn) {

        mBookDetailView.setISBN(isbn);
    }

    public void showRelease(String release) {

        mBookDetailView.setRelease(release);
    }

    public void showPublisher(String publisher) {

        mBookDetailView.setPublisher(publisher);
    }

    private void showBookImage(String url) {
        mBookDetailView.showBookImage(url);
        mBookDetailView.showBookCover(url);
    }

    @Subscribe
    public void onDetailInformationReceived(BookDetail response) {

        showDescription(response.getDescription());
        showTitle(response.getTitle());
        showAuthors(response.getAuthor());
        showISBN(response.getISBN());
        showRelease(response.getYear());
        showPublisher(response.getPublisher());
        showBookImage(response.getImage());
    }

    @Override
    public void start() {

        mBus.register(this);
    }

    @Override
    public void stop() {

        mBus.unregister(this);
    }
}
