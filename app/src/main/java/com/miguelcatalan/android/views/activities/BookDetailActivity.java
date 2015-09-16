package com.miguelcatalan.android.views.activities;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.transition.Transition;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.miguelcatalan.android.BooksApp;
import com.miguelcatalan.android.R;
import com.miguelcatalan.android.di.components.DaggerBookUsecasesComponent;
import com.miguelcatalan.android.di.modules.BookUsecasesModule;
import com.miguelcatalan.android.mvp.presenters.BookDetailPresenter;
import com.miguelcatalan.android.mvp.views.DetailView;
import com.miguelcatalan.android.utils.GUIUtils;
import com.miguelcatalan.android.utils.PaletteGeneratorTransformation;
import com.miguelcatalan.android.utils.TransitionUtils;
import com.miguelcatalan.android.views.custom_listeners.AnimatorAdapter;
import com.miguelcatalan.android.views.custom_listeners.TransitionAdapter;
import com.miguelcatalan.android.views.custom_views.ObservableScrollView;
import com.miguelcatalan.android.views.custom_views.ScrollViewListener;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;

public class BookDetailActivity extends AppCompatActivity implements DetailView, ScrollViewListener {

    // Boolean that indicates if the activity is shown in a tablet or not
    boolean mIsTablet;

    private Palette.Swatch mBrightSwatch;

    @InjectView(R.id.activity_detail_author)
    TextView mAuthor;

    @InjectView(R.id.activity_detail_header_author)
    TextView mAuthorHeader;

    @InjectView(R.id.activity_detail_isbn)
    TextView mISBN;

    @InjectView(R.id.activity_detail_header_isbn)
    TextView mISBNHeader;

    @InjectView(R.id.activity_detail_release)
    TextView mRelease;

    @InjectView(R.id.activity_detail_header_release)
    TextView mReleaseHeader;

    @InjectView(R.id.activity_detail_publisher)
    TextView mPubliser;

    @InjectView(R.id.activity_detail_header_publisher)
    TextView mPublisherHeader;

    @InjectView(R.id.activity_detail_description)
    TextView mDescription;

    @InjectView(R.id.activity_detail_header_description)
    TextView mDescriptionHeader;

    @InjectView(R.id.activity_detail_title)
    TextView mTitle;

    @InjectView(R.id.activity_detail_fab)
    ImageView mFabButton;

    @InjectView(R.id.activity_detail_container)
    View mInformationContainer;

    @InjectView(R.id.item_book_cover)
    ImageView mCoverImageView;

    @Optional
    @InjectView(R.id.activity_detail_image)
    ImageView mBookImageView;

    @InjectView(R.id.activity_detail_book_info)
    LinearLayout mBookDescriptionContainer;

    @InjectView(R.id.activity_detail_scroll)
    ObservableScrollView mObservableScrollView;
    private int[] mViewLastLocation;

    @Inject
    BookDetailPresenter mDetailPresenter;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookdetail);
        ButterKnife.inject(this);

        mIsTablet = getContext().getResources().getBoolean(
                R.bool.is_tablet);

        initializeDependencyInjector();
        initializeStartAnimation();
    }

    @Override
    protected void onStart() {

        super.onStart();
        mDetailPresenter.attachView(this);
        mDetailPresenter.start();
    }

    private void initializeStartAnimation() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            if (!mIsTablet) {

                GUIUtils.makeTheStatusbarTranslucent(this);
                mObservableScrollView.setScrollViewListener(this);
            }

            configureEnterTransition ();

        } else {

            mViewLastLocation = getIntent().getIntArrayExtra(
                    BooksActivity.EXTRA_BOOK_LOCATION);

            configureEnterAnimation ();
        }
    }

    private void initializeDependencyInjector() {

        String bookId = getIntent().getStringExtra(BooksActivity.EXTRA_BOOK_ID);
        BooksApp app = (BooksApp) getApplication();

        DaggerBookUsecasesComponent.builder()
                .appComponent(app.getAppComponent())
                .bookUsecasesModule(new BookUsecasesModule(bookId))
                .build().inject(this);
    }

    private void configureEnterAnimation() {

        if (!mIsTablet) {

            GUIUtils.startScaleAnimationFromPivotY(
                    mViewLastLocation[0], mViewLastLocation[1],
                    mObservableScrollView, new AnimatorAdapter() {

                        @Override
                        public void onAnimationEnd(Animator animation) {

                            super.onAnimationEnd(animation);
                            GUIUtils.showViewByScale(mFabButton);
                        }
                    }
            );

            animateElementsByScale();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void configureEnterTransition() {

        getWindow().setSharedElementEnterTransition(
                TransitionUtils.makeSharedElementEnterTransition(this));

        postponeEnterTransition();

        int bookPosition = getIntent().getIntExtra(
                BooksActivity.EXTRA_BOOK_POSITION, 0);

        mCoverImageView.setTransitionName(BooksActivity.SHARED_ELEMENT_COVER + bookPosition);
        mObservableScrollView.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {

                    @Override
                    public boolean onPreDraw() {

                        mObservableScrollView.getViewTreeObserver()
                                .removeOnPreDrawListener(this);

                        startPostponedEnterTransition();
                        return true;
                    }
                }
        );

        getWindow().getSharedElementEnterTransition().addListener(
                new TransitionAdapter() {

                    @Override
                    public void onTransitionEnd(Transition transition) {

                        super.onTransitionEnd(transition);
                        animateElementsByScale();
                    }
                }
        );
    }

    private void animateElementsByScale() {

        GUIUtils.showViewByScale(mFabButton);
        GUIUtils.showViewByScaleY(mTitle, new AnimatorAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {

                super.onAnimationEnd(animation);
                GUIUtils.showViewByScale(mBookDescriptionContainer);
            }
        });
    }

    @Override
    public void showBookImage(String url) {
        if (mIsTablet && mBookImageView != null) {
            mBookImageView.setVisibility(View.VISIBLE);
            Picasso.with(this)
                    .load(url)
                    .fit()
                    .centerCrop()
                    .into(mBookImageView);
        }
    }

    @Override
    public void showBookCover(String url) {
        Picasso.with(this)
                .load(url)
                .fit()
                .centerCrop()
                .transform(new PaletteGeneratorTransformation(4))
                .into(mCoverImageView, new PaletteGeneratorTransformation.Callback(mCoverImageView) {
                    @Override
                    public void onPalette(final Palette palette) {
                        //themeWithPalette(palette);
                    }
                });
    }

    @Override
    public void setTitle(String title) {

        mTitle.setText(title);
    }

    @Override
    public void setDescription(String description) {

        if (!description.isEmpty() && !description.equals("") && description.length() > 10) {
            mDescription.setVisibility(View.VISIBLE);
            mDescriptionHeader.setVisibility(View.VISIBLE);
            mDescription.setText(description);
        }
    }

    @Override
    public void setRelease(String release) {

        if (!release.isEmpty() && !release.equals("") && release.length() > 3) {
            mRelease.setVisibility(View.VISIBLE);
            mReleaseHeader.setVisibility(View.VISIBLE);
            mRelease.setText(release);
        }
    }

    @Override
    public void setPublisher(String publisher) {

        if (!publisher.isEmpty() && !publisher.equals("") && publisher.length() > 10) {
            mPubliser.setVisibility(View.VISIBLE);
            mPublisherHeader.setVisibility(View.VISIBLE);
            mPubliser.setText(publisher);
        }
    }

    @Override
    public void setISBN(String isbn) {

        if (!isbn.isEmpty() && !isbn.equals("") && isbn.length() > 10) {
            mISBN.setVisibility(View.VISIBLE);
            mISBNHeader.setVisibility(View.VISIBLE);
            mISBN.setText(isbn);
        }
    }

    @Override
    public void setAuthor(String author) {
        mAuthor.setVisibility(View.VISIBLE);
        mAuthor.setText(author);
    }


    @Override
    public Context getContext() {

        return this;
    }

    public void setBackgroundAndFabContentColors(Palette.Swatch swatch) {

        if (swatch != null) {

            mInformationContainer.setBackgroundColor(swatch.getRgb());

            mTitle.setTextColor(swatch.getRgb());
            mAuthor.setTextColor(swatch.getTitleTextColor());
            mDescription.setTextColor(swatch.getTitleTextColor());
        }
    }

    public void setHeadersTitleColors(Palette.Swatch swatch) {

        if (swatch != null) {

            mBrightSwatch = swatch;

            mTitle.setBackgroundColor(
                    mBrightSwatch.getRgb());
            mDescriptionHeader.setTextColor(swatch.getRgb());
        }  // else use colors of the layout
    }

    private void themeWithPalette(Palette palette) {

        if (palette != null) {

            final Palette.Swatch darkVibrantSwatch = palette.getDarkVibrantSwatch();
            final Palette.Swatch darkMutedSwatch = palette.getDarkMutedSwatch();
            final Palette.Swatch lightVibrantSwatch = palette.getLightVibrantSwatch();
            final Palette.Swatch lightMutedSwatch = palette.getLightMutedSwatch();
            final Palette.Swatch vibrantSwatch = palette.getVibrantSwatch();

            final Palette.Swatch backgroundAndContentColors = (darkVibrantSwatch != null)
                    ? darkVibrantSwatch : darkMutedSwatch;

            final Palette.Swatch titleAndFabColors = (darkVibrantSwatch != null)
                    ? lightVibrantSwatch : lightMutedSwatch;

            setBackgroundAndFabContentColors(backgroundAndContentColors);

            setHeadersTitleColors(titleAndFabColors);

            setVibrantElements(vibrantSwatch);
        }
    }

    private void setVibrantElements(Palette.Swatch vibrantSwatch) {

        if (vibrantSwatch != null) {

            mFabButton.getBackground().setColorFilter(vibrantSwatch.getRgb(), PorterDuff.Mode.MULTIPLY);
        }
    }

    boolean isTranslucent = false;

    @Override
    public void onScrollChanged(ScrollView scrollView, int x, int y, int oldx, int oldy) {

        if (y > mCoverImageView.getHeight()) {

            mTitle.setTranslationY(
                    y - mCoverImageView.getHeight());

            if (!isTranslucent) {

                isTranslucent = true;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                    GUIUtils.setTheStatusbarNotTranslucent(this);
                    if (mBrightSwatch != null) {
                        getWindow().setStatusBarColor(mBrightSwatch.getRgb());
                    }
                }
            }
        }

        if (y < mCoverImageView.getHeight() && isTranslucent) {

            mTitle.setTranslationY(0);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                GUIUtils.makeTheStatusbarTranslucent(this);
                isTranslucent = false;
            }
        }
    }

    @Override
    public void finish(String cause) {

        Toast.makeText(this, cause, Toast.LENGTH_SHORT).show();
        this.finish();
    }

    @Override
    protected void onStop() {

        super.onStop();
        mDetailPresenter.stop();
    }
}