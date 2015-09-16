package com.miguelcatalan.domain;

import com.miguelcatalan.model.entities.BookDetail;

/**
 * @author Miguel Catalan Bañuls
 */
public interface GetBookDetailUsecase extends Usecase {

    void requestBookDetail(String bookId);

    void onBookDetailResponse(BookDetail response);

    void sendDetailBookToPresenter(BookDetail response);
}