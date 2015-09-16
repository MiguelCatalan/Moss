package com.miguelcatalan.android.mvp.views;

import android.graphics.Bitmap;

/**
 * @author Miguel Catalan Bañuls
 */
public interface DetailView extends MVPView {

    void showBookCover(String url);

    void setTitle(String title);

    void setDescription(String description);

    void setAuthor(String homepage);

    void setISBN(String isbn);

    void setRelease(String release);

    void setPublisher(String publisher);

    void finish(String cause);

    void showBookImage(String url);
}