package com.miguelcatalan.android.views.custom_views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.miguelcatalan.android.R;

/**
 * @author Miguel Catalan Bañuls
 */
public class AutoResizeImageView extends ImageView {

    private int resizeBy;
    private float ratio;

    public AutoResizeImageView(final Context context) {
        super(context);
    }

    public AutoResizeImageView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.AutoResizeImageView, 0, 0);
        try {
            resizeBy = attributes.getInteger(R.styleable.AutoResizeImageView_resize, 0);
            ratio = attributes.getFloat(R.styleable.AutoResizeImageView_ratio, 1);
        } finally {
            attributes.recycle();
        }
    }

    public AutoResizeImageView(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
        TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.AutoResizeImageView, 0, 0);
        try {
            resizeBy = attributes.getInteger(R.styleable.AutoResizeImageView_resize, 0);
            ratio = attributes.getFloat(R.styleable.AutoResizeImageView_ratio, 1);
        } finally {
            attributes.recycle();
        }
    }


    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        if (resizeBy == 0) {
            int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
            setMeasuredDimension(width, (int) (width * ratio));
        } else {
            int height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
            setMeasuredDimension((int) (height * ratio), height);
        }
    }

    @Override
    protected void onSizeChanged(final int w, final int h, final int oldw, final int oldh) {
        super.onSizeChanged(w, w, oldw, oldh);
    }
}