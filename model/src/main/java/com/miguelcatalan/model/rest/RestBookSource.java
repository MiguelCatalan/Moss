package com.miguelcatalan.model.rest;

import com.miguelcatalan.common.utils.Constants;
import com.miguelcatalan.model.entities.BookDetail;
import com.miguelcatalan.model.entities.BooksWrapper;
import com.squareup.otto.Bus;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * @author Miguel Catalan Bañuls
 */
public class RestBookSource implements RestDataSource {

    private final BookDatabaseAPI booksDBApi;
    private final Bus bus;

    public RestBookSource(Bus bus) {

        RestAdapter bookAPIRest = new RestAdapter.Builder()
                .setEndpoint(Constants.BOOK_DB_HOST)
                .setLogLevel(RestAdapter.LogLevel.HEADERS)
                .build();

        booksDBApi = bookAPIRest.create(BookDatabaseAPI.class);
        this.bus = bus;
    }

    @Override
    public void getBooks(String query) {

        booksDBApi.getBooks(query, retrofitCallback);
    }

    @Override
    public void getDetailBook(String id) {

        booksDBApi.getBookDetail(id, retrofitCallback);
    }

    public Callback retrofitCallback = new Callback() {
        @Override
        public void success(Object o, Response response) {

            if (o instanceof BookDetail) {

                BookDetail detailResponse = (BookDetail) o;
                bus.post(detailResponse);

            } else if (o instanceof BooksWrapper) {
                BooksWrapper booksApiResponse = (BooksWrapper) o;
                bus.post(booksApiResponse);

            }
        }

        @Override
        public void failure(RetrofitError error) {

            System.out.println("[DEBUG] RestBookSource failure - " + error.getMessage());
        }
    };

    @Override
    public void getBooksByPage(String query, int page) {
        booksDBApi.getBooksByPage(
                query,
                page + "",
                retrofitCallback
        );
        System.out.println("lalalalal");
    }
}
