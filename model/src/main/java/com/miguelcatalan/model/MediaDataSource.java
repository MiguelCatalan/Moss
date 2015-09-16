package com.miguelcatalan.model;

/**
 * @author Miguel Catalan Bañuls
 */
public interface MediaDataSource {
    void getBooks(String query);

    void getDetailBook(String id);
}
