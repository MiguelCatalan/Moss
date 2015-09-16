package com.miguelcatalan.android.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.transition.ChangeBounds;
import android.transition.Explode;
import android.transition.Transition;

import com.miguelcatalan.android.R;

/**
 * @author Miguel Catalan Bañuls
 */
public class TransitionUtils {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static Transition makeEnterTransition() {

        Explode explodeTransition = new Explode();
        return explodeTransition;
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static Transition makeSharedElementEnterTransition(Context context) {

        Transition changeBounds = new ChangeBounds();
        changeBounds.addTarget(R.id.item_book_cover);
        return changeBounds;
    }
}
