package com.miguelcatalan.android.views.activities;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.miguelcatalan.android.BooksApp;
import com.miguelcatalan.android.R;
import com.miguelcatalan.android.di.components.DaggerBooksUsecasesComponent;
import com.miguelcatalan.android.di.modules.BooksUsecasesModule;
import com.miguelcatalan.android.mvp.presenters.BooksPresenter;
import com.miguelcatalan.android.mvp.views.BooksView;
import com.miguelcatalan.android.utils.GridSpacingDecoration;
import com.miguelcatalan.android.utils.RecyclerInsetsDecoration;
import com.miguelcatalan.android.utils.RecyclerViewClickListener;
import com.miguelcatalan.android.views.adapters.BooksAdapter;
import com.miguelcatalan.android.views.custom_views.MaterialSearchView;
import com.miguelcatalan.model.entities.Book;
import com.miguelcatalan.model.entities.BooksWrapper;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;

public class BooksActivity extends AppCompatActivity implements
        BooksView, RecyclerViewClickListener {


    private final static String BUNDLE_BOOKS_WRAPPER = "books_wrapper";
    private final static String BUNDLE_BACK_TRANSLATION = "background_translation";
    public final static String EXTRA_BOOK_ID = "book_id";
    public final static String EXTRA_BOOK_LOCATION = "view_location";
    public final static String EXTRA_BOOK_POSITION = "book_position";
    public final static String SHARED_ELEMENT_COVER = "cover";
    private BooksAdapter mBooksAdapter;

    public float mBackgroundTranslation;


    @Optional
    @InjectView(R.id.activity_books_background_view)
    View mTabletBackground;

    @InjectView(R.id.activity_books_toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.activity_books_progress)
    ProgressBar mProgressBar;
    @InjectView(R.id.activity_books_recycler)
    RecyclerView mRecycler;
    @InjectView(R.id.search_view)
    MaterialSearchView searchView;

    @Inject
    BooksPresenter mBooksPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);
        ButterKnife.inject(this);

        initializeDependencyInjector();
        initializeToolbar();
        initializeRecycler();

        mBooksPresenter.attachView(this);

        if (savedInstanceState != null) {
            initializeFromParams(savedInstanceState);
        }

    }

    @Override
    protected void onStart() {

        super.onStart();
        mBooksPresenter.start();
    }

    @Override
    protected void onStop() {

        super.onStop();
        mBooksPresenter.stop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);

        if (mBooksAdapter != null) {

            outState.putSerializable(BUNDLE_BOOKS_WRAPPER, new BooksWrapper(
                    mBooksAdapter.getBookList()));

            outState.putFloat(BUNDLE_BACK_TRANSLATION, mBackgroundTranslation);
        }
    }

    private void initializeFromParams(Bundle savedInstanceState) {

        BooksWrapper booksWrapper = (BooksWrapper) savedInstanceState
                .getSerializable(BUNDLE_BOOKS_WRAPPER);

        mBooksPresenter.onBooksReceived(booksWrapper);
    }

    private void initializeRecycler() {

        mRecycler.addItemDecoration(new RecyclerInsetsDecoration(this));
        mRecycler.setOnScrollListener(recyclerScrollListener);


        mBooksAdapter = new BooksAdapter();
        mBooksAdapter.setRecyclerListListener(this);
        GridLayoutManager layoutManager = new GridLayoutManager(this, getResources().getInteger(R.integer.number_items_grid));
        mRecycler.setLayoutManager(layoutManager);
        mRecycler.addItemDecoration(new GridSpacingDecoration(getResources().getInteger(R.integer.number_items_grid), (int) getResources().getDimension(R.dimen.activity_horizontal_margin_half), true));
        mRecycler.setAdapter(mBooksAdapter);
    }

    private void initializeToolbar() {

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");

        searchView.setSuggestions(getResources().getStringArray(R.array.query_suggestions));

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mBooksPresenter.searchBooks(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_books, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;
    }

    private void initializeDependencyInjector() {

        BooksApp app = (BooksApp) getApplication();

        DaggerBooksUsecasesComponent.builder()
                .appComponent(app.getAppComponent())
                .booksUsecasesModule(new BooksUsecasesModule())
                .build().inject(this);
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public Context getContext() {

        return this;
    }

    @Override
    public void showBooks(List<Book> bookList) {
        mBooksAdapter.setBookList(bookList);
    }

    @Override
    public void showLoading() {

        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {

        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showLoadingLabel() {

    }

    @Override
    public void hideActionLabel() {

    }

    @Override
    public boolean isTheListEmpty() {

        return (mBooksAdapter == null) || mBooksAdapter.getBookList().isEmpty();
    }

    @Override
    public void appendBooks(List<Book> bookList) {

        mBooksAdapter.appendBooks(bookList);
    }

    @Override
    public void clearBooks() {
        mBooksAdapter.clearBooks();
    }

    @Override
    public void onClick(View touchedView, int bookPosition, float touchedX, float touchedY) {

        Intent bookDetailActivityIntent = new Intent(
                BooksActivity.this, BookDetailActivity.class);

        String bookID = mBooksAdapter.getBookList().get(bookPosition).getID();
        bookDetailActivityIntent.putExtra(EXTRA_BOOK_ID, bookID);
        bookDetailActivityIntent.putExtra(EXTRA_BOOK_POSITION, bookPosition);

        ImageView mCoverImage = (ImageView) touchedView.findViewById(R.id.item_book_cover);
        BitmapDrawable bitmapDrawable = (BitmapDrawable) mCoverImage.getDrawable();

        if (mBooksAdapter.isBookReady(bookPosition) || bitmapDrawable != null) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                startDetailActivityBySharedElements(touchedView, bookPosition,
                        bookDetailActivityIntent);
            else
                startDetailActivityByAnimation(touchedView, (int) touchedX,
                        (int) touchedY, bookDetailActivityIntent);

        } else {

            //Toast.makeText(this, getString(R.string.activity_books_message_loading_film), Toast.LENGTH_SHORT).show();
        }
    }

    private void startDetailActivityByAnimation(View touchedView,
                                                int touchedX, int touchedY, Intent bookDetailActivityIntent) {

        int[] touchedLocation = {touchedX, touchedY};
        int[] locationAtScreen = new int[2];
        touchedView.getLocationOnScreen(locationAtScreen);

        int finalLocationX = locationAtScreen[0] + touchedLocation[0];
        int finalLocationY = locationAtScreen[1] + touchedLocation[1];

        int[] finalLocation = {finalLocationX, finalLocationY};
        bookDetailActivityIntent.putExtra(EXTRA_BOOK_LOCATION,
                finalLocation);

        startActivity(bookDetailActivityIntent);
    }

    @SuppressWarnings("unchecked")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void startDetailActivityBySharedElements(View touchedView,
                                                     int bookPosition, Intent bookDetailActivityIntent) {

         ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                this, new Pair<>(touchedView, SHARED_ELEMENT_COVER + bookPosition));

        startActivity(bookDetailActivityIntent, options.toBundle());
    }

    private RecyclerView.OnScrollListener recyclerScrollListener = new RecyclerView.OnScrollListener() {
        public boolean flag;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

            super.onScrolled(recyclerView, dx, dy);

            int visibleItemCount = mRecycler.getLayoutManager().getChildCount();
            int totalItemCount = mRecycler.getLayoutManager().getItemCount();
            int pastVisibleItems = ((GridLayoutManager) mRecycler.getLayoutManager())
                    .findFirstVisibleItemPosition();

            if ((visibleItemCount + pastVisibleItems) >= totalItemCount && !mBooksPresenter.isLoading()) {
                mBooksPresenter.onEndListReached();
            }

            if (mTabletBackground != null) {

                mBackgroundTranslation = mTabletBackground.getY() - (dy / 2);
                mTabletBackground.setTranslationY(mBackgroundTranslation);
            }

            // Is scrolling up
            if (dy > 10) {

                if (!flag) {

                    showToolbar();
                    flag = true;
                }

                // Is scrolling down
            } else if (dy < -10) {

                if (flag) {

                    hideToolbar();
                    flag = false;
                }
            }

        }
    };


    private void showToolbar() {

        mToolbar.startAnimation(AnimationUtils.loadAnimation(this,
                R.anim.translate_up_off));
    }

    private void hideToolbar() {

        mToolbar.startAnimation(AnimationUtils.loadAnimation(this,
                R.anim.translate_up_on));
    }
}
