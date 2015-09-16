package com.miguelcatalan.model.rest;

import com.miguelcatalan.model.entities.BookDetail;
import com.miguelcatalan.model.entities.BooksWrapper;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Interface representing the BookDatabaseAPI endpoints
 * used by retrofit
 *
 * @author Miguel Catalan Bañuls
 */
public interface BookDatabaseAPI {

    @GET("/search/{query}")
    void getBooks(
            @Path("query") String query,
            Callback<BooksWrapper> callback
    );

    @GET("/book/{id}")
    void getBookDetail(
            @Path("id") String id,
            Callback<BookDetail> callback
    );

    @GET("/search/{query}/page/{number}")
    void getBooksByPage(
            @Path("query") String query,
            @Path("number") String page,
            Callback<BooksWrapper> callback
    );
}
