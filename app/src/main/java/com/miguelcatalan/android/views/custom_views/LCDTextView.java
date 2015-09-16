package com.miguelcatalan.android.views.custom_views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * @author Miguel Catalan Bañuls
 */
public class LCDTextView extends TextView {
    public LCDTextView(Context context) {

        super(context);

        if (!isInEditMode())
            init(context);
    }

    public LCDTextView(Context context, AttributeSet attrs) {

        super(context, attrs);

        if (!isInEditMode())
            init(context);
    }

    public LCDTextView(Context context, AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);

        if (!isInEditMode())
            init(context);

    }

    private void init(Context context) {

        Typeface t = Typeface.createFromAsset(context.getAssets(), "LCD-Regular.ttf");
        this.setTypeface(t);
    }
}