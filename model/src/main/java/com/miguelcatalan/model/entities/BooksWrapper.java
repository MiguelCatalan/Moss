package com.miguelcatalan.model.entities;

import java.io.Serializable;
import java.util.List;

/**
 * @author Miguel Catalan Bañuls
 */
public class BooksWrapper implements Serializable {

    private Number Page;
    private List<Book> Books;
    private String Total;

    public BooksWrapper(List<Book> Books) {

        this.Books = Books;
    }

    public Number getPage() {

        return this.Page;
    }

    public void setPage(Number Page) {

        this.Page = Page;
    }

    public List<Book> getBooks() {

        return Books;
    }

    public Number getTotalPages() {

        return (int) Math.ceil(Integer.parseInt(this.Total) / 10.0);
    }

    public String getTotal() {

        return this.Total;
    }

    public void setTotal(String Total) {

        this.Total = Total;
    }
}