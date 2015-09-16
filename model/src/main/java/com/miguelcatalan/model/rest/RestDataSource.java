package com.miguelcatalan.model.rest;

import com.miguelcatalan.model.MediaDataSource;

/**
 * @author Miguel Catalan Bañuls
 */
public interface RestDataSource extends MediaDataSource {

    void getBooksByPage(String query, int page);

}
