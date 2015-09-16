package com.miguelcatalan.android.views.custom_views;

import android.widget.ScrollView;

/**
 * @author Miguel Catalan Bañuls
 */
public interface ScrollViewListener {

    void onScrollChanged(ScrollView scrollView, int x, int y, int oldx, int oldy);

}